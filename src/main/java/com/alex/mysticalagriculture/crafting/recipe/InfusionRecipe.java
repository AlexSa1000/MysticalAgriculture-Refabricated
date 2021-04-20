package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.crafting.SpecialRecipe;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

import java.util.Arrays;

public class InfusionRecipe implements SpecialRecipe {
    private final Identifier recipeId;
    private final DefaultedList<Ingredient> inputs;
    private final ItemStack output;

    public InfusionRecipe(Identifier recipeId, DefaultedList<Ingredient> inputs, ItemStack output) {
        this.recipeId = recipeId;
        this.inputs = inputs;
        this.output = output;
    }



    @Override
    public boolean fits(int var1, int var2) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public DefaultedList<Ingredient> getPreviewInputs() {
        return this.inputs;
    }

    @Override
    public Identifier getId() {
        return this.recipeId;
    }

    @Override
    public ItemStack craft(Inventory inv) {
        return this.output.copy();
    }

    @Override
    public boolean matches(Inventory inventory) {
        ItemStack altarStack = inventory.getStack(0);
        return !this.inputs.isEmpty() && this.inputs.get(0).test(altarStack) && SpecialRecipe.super.matches(inventory);
    }

    @Override
    public RecipeSerializer<InfusionRecipe> getSerializer() {
        return RecipeSerializers.INFUSION;
    }
    @Override
    public RecipeType<? extends InfusionRecipe> getType() {
        return RecipeTypes.INFUSION;
    }

    public static Ingredient getIngredient(Crop crop, ComponentType type) {
        Ingredient ingredient;
        switch (type) {
            case ESSENCE:
                ingredient = Ingredient.ofItems(crop.getTier().getEssence());
                break;
            case SEED:
                ingredient = Ingredient.ofItems(crop.getType().getCraftingSeed());
                break;
            case MATERIAL:
                ingredient = crop.getCraftingMaterial();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return ingredient;
    }



    public static class InfusionRecipeSerializer implements RecipeSerializer<InfusionRecipe> {
        @Override
        public InfusionRecipe read(Identifier id, JsonObject json) {
            DefaultedList<Ingredient> inputs = DefaultedList.of();

            JsonObject input = JsonHelper.getObject(json, "input");
            if (!input.has("item") && !input.has("tag")) {
                String cropId = JsonHelper.getString(input, "crop");
                String typeName = JsonHelper.getString(input, "component");

                Crop crop = ModCrops.getCropById(cropId);
                ComponentType type = ComponentType.fromName(typeName);

                inputs.add(getIngredient(crop, type));
            } else {
                inputs.add(Ingredient.fromJson(input));
            }

            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            for (JsonElement i : ingredients) {
                if (i.isJsonObject()) {
                    JsonObject object = i.getAsJsonObject();
                    if (!object.has("item") && !object.has("tag")) {
                        String cropId = JsonHelper.getString(object, "crop");
                        String typeName = JsonHelper.getString(object, "component");

                        Crop crop = ModCrops.getCropById(cropId);
                        ComponentType type = ComponentType.fromName(typeName);

                        inputs.add(getIngredient(crop, type));
                    } else {
                        inputs.add(Ingredient.fromJson(i));
                    }
                }
            }

            ItemStack output = ShapedRecipe.getItemStack(json.getAsJsonObject("result"));

            return new InfusionRecipe(id, inputs, output);
        }

        @Override
        public InfusionRecipe read(Identifier id, PacketByteBuf buf) {
            int size = buf.readVarInt();

            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(size, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();

            return new InfusionRecipe(id, inputs, output);
        }

        @Override
        public void write(PacketByteBuf buf, InfusionRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());

            for (Ingredient ingredient : recipe.inputs) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.output);
        }
    }

    public enum ComponentType {
        ESSENCE("essence"),
        SEED("seed"),
        MATERIAL("material");

        public final String name;

        ComponentType(String name) {
            this.name = name;
        }

        public static ComponentType fromName(String name) {
            return Arrays.stream(values()).filter(t -> t.name.equals(name)).findFirst().orElse(null);
        }
    }

}
