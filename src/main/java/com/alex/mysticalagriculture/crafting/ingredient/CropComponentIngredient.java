package com.alex.mysticalagriculture.crafting.ingredient;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.init.ModRecipeSerializers;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

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

    public CropComponentIngredient(Crop crop, ComponentType type, Stream<Ingredient.Value> itemList) {
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

        return Arrays.stream(base.getItems())
                .anyMatch(s -> s.getDamageValue() == input.getDamageValue() && (!s.hasTag() || new NbtPredicate(s.getTag()).matches(input)));
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return Arrays.asList(base.getItems());
    }

    @Override
    public boolean requiresTesting() {
        return base.requiresTesting();
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return ModRecipeSerializers.CROP_COMPONENT_INGREDIENT;
    }

    public Ingredient getBase() {
        return base;
    }

    public static class Serializer implements CustomIngredientSerializer<CropComponentIngredient> {

        @Override
        public ResourceLocation getIdentifier() {
            return new ResourceLocation(MOD_ID, "crop_component");
        }

        @Override
        public CropComponentIngredient read(JsonObject json) {
            var cropId = GsonHelper.getAsString(json, "crop");
            var typeName = GsonHelper.getAsString(json, "component");
            var crop = CropRegistry.getInstance().getCropById(new ResourceLocation(cropId));
            var type = ComponentType.fromName(typeName);
            var itemList = switch (type) {
                case ESSENCE -> new Ingredient.ItemValue(new ItemStack(crop.getTier().getEssence()));
                case SEED -> new Ingredient.ItemValue(new ItemStack(crop.getType().getCraftingSeed()));
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
        public CropComponentIngredient read(FriendlyByteBuf buffer) {
            var crop = CropRegistry.getInstance().getCropById(new ResourceLocation(buffer.readUtf()));
            var type = ComponentType.fromName(buffer.readUtf());

            Stream<Ingredient.Value> itemList = Stream.generate(buffer::readItem)
                    .limit(buffer.readVarInt())
                    .map(Ingredient.ItemValue::new);

            return new CropComponentIngredient(crop, type, itemList);
        }

        @Override
        public void write(FriendlyByteBuf buffer, CropComponentIngredient ingredient) {
            buffer.writeUtf(ingredient.crop.getId().toString());
            buffer.writeUtf(ingredient.type.name);

            var items = ingredient.getBase().getItems();

            buffer.writeVarInt(items.length);

            for (var item : items) {
                buffer.writeItem(item);
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