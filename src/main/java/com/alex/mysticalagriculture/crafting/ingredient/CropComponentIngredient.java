package com.alex.mysticalagriculture.crafting.ingredient;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class CropComponentIngredient implements CustomIngredient {
    private final Ingredient base;
    private final Crop crop;
    private final ComponentType type;

    public CropComponentIngredient(Crop crop, ComponentType type) {
        this.base = new Ingredient(Stream.empty());
        this.crop = crop;
        this.type = type;
    }

    public CropComponentIngredient(Crop crop, ComponentType type, Stream<Ingredient.Entry> itemList) {
        this.base = new Ingredient(itemList);
        this.crop = crop;
        this.type = type;
    }

    @Override
    public boolean test(ItemStack input) {
        if (input == null)
            return false;

        if (!base.test(input))
            return false;

        return Arrays.stream(base.getMatchingStacks())
                .anyMatch(s -> s.getDamage() == input.getDamage() && (!s.hasNbt() || new NbtPredicate(s.getNbt()).test(input)));
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return Arrays.asList(base.getMatchingStacks());
    }

    @Override
    public boolean requiresTesting() {
        return base.requiresTesting();
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return RecipeSerializers.CROP_COMPONENT_INGREDIENT;
    }

    public Ingredient getBase() {
        return base;
    }

    public static class Serializer implements CustomIngredientSerializer<CropComponentIngredient> {

        @Override
        public Identifier getIdentifier() {
            return new Identifier(MOD_ID, "crop_component");
        }

        @Override
        public CropComponentIngredient read(JsonObject json) {
            var cropId = JsonHelper.getString(json, "crop");
            var typeName = JsonHelper.getString(json, "component");
            var crop = CropRegistry.getInstance().getCropById(new Identifier(cropId));
            var type = ComponentType.fromName(typeName);
            var itemList = switch (type) {
                case ESSENCE -> new Ingredient.StackEntry(new ItemStack(crop.getTier().getEssence()));
                case SEED -> new Ingredient.StackEntry(new ItemStack(crop.getType().getCraftingSeed()));
                case MATERIAL -> crop.getLazyIngredient().createValue();
            };

            if (itemList == null) {
                return new CropComponentIngredient(crop, type);
            }

            return new CropComponentIngredient(crop, type, Stream.of(itemList));
        }

        @Override
        public void write(JsonObject json, CropComponentIngredient ingredient) {
            //json.addProperty("fabric:type", "mysticalagriculture:crop_component");
            json.addProperty("component", ingredient.type.name);
            json.addProperty("crop", ingredient.crop.getId().toString());
        }

        @Override
        public CropComponentIngredient read(PacketByteBuf buffer) {
            var crop = CropRegistry.getInstance().getCropById(new Identifier(buffer.readString()));
            var type = ComponentType.fromName(buffer.readString());

            Stream<Ingredient.Entry> itemList = Stream.generate(buffer::readItemStack)
                    .limit(buffer.readVarInt())
                    .map(Ingredient.StackEntry::new);

            return new CropComponentIngredient(crop, type, itemList);
        }

        @Override
        public void write(PacketByteBuf buffer, CropComponentIngredient ingredient) {
            buffer.writeString(ingredient.crop.getId().toString());
            buffer.writeString(ingredient.type.name);

            var items = ingredient.getMatchingStacks();

            buffer.writeVarInt(items.size());

            for (var item : items) {
                buffer.writeItemStack(item);
            }
        }
    }

    public enum ComponentType {
        ESSENCE("essence"),
        SEED("seed"),
        MATERIAL("material");

        private static final Map<String, ComponentType> LOOKUP = new HashMap<>();
        public final String name;

        static {
            for (var value : values()) {
                LOOKUP.put(value.name, value);
            }
        }

        ComponentType(String name) {
            this.name = name;
        }

        public static ComponentType fromName(String name) {
            return LOOKUP.get(name);
        }
    }
}
