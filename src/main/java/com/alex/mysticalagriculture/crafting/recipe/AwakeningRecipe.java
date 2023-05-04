package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.zucchini.crafting.SpecialRecipe;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class AwakeningRecipe implements SpecialRecipe, com.alex.mysticalagriculture.api.crafting.AwakeningRecipe {
    public static final int RECIPE_SIZE = 9;
    private final Identifier recipeId;
    private final DefaultedList<Ingredient> inputs;
    private final AwakeningRecipe.EssenceVesselRequirements essences;
    private final ItemStack output;

    public AwakeningRecipe(Identifier recipeId, DefaultedList<Ingredient> inputs, AwakeningRecipe.EssenceVesselRequirements essences, ItemStack output) {
        this.recipeId = recipeId;
        this.inputs = DefaultedList.ofSize(9, Ingredient.EMPTY);
        this.essences = essences;
        this.output = output;

        this.inputs.set(0, inputs.get(0));
        this.inputs.set(1, createEssenceIngredient(ModCrops.AIR));
        this.inputs.set(2, inputs.get(1));
        this.inputs.set(3, createEssenceIngredient(ModCrops.EARTH));
        this.inputs.set(4, inputs.get(2));
        this.inputs.set(5, createEssenceIngredient(ModCrops.WATER));
        this.inputs.set(6, inputs.get(3));
        this.inputs.set(7, createEssenceIngredient(ModCrops.FIRE));
        this.inputs.set(8, inputs.get(4));
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public Identifier getId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.AWAKENING;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.AWAKENING;
    }

    @Override
    public EssenceVesselRequirements getEssenceRequirements() {
        return this.essences;
    }

    private static Ingredient createEssenceIngredient(Crop crop) {
        var item = crop.getEssenceItem();
        return item == null ? Ingredient.EMPTY : Ingredient.ofItems(item);
    }

    public static class Serializer implements RecipeSerializer<AwakeningRecipe> {

        @Override
        public AwakeningRecipe read(Identifier recipeId, JsonObject json) {
            var inputs = DefaultedList.ofSize(RECIPE_SIZE, Ingredient.EMPTY);
            var input = JsonHelper.getObject(json, "input");

            inputs.set(0, Ingredient.fromJson(input));

            var ingredients = JsonHelper.getArray(json, "ingredients");

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i + 1, Ingredient.fromJson(ingredients.get(i)));
            }

            var essences = JsonHelper.getObject(json, "essences");

            var essenceRequirements = new AwakeningRecipe.EssenceVesselRequirements(
                    JsonHelper.getInt(essences, "air"),
                    JsonHelper.getInt(essences, "earth"),
                    JsonHelper.getInt(essences, "water"),
                    JsonHelper.getInt(essences, "fire")
            );

            var result = ShapedRecipe.outputFromJson(json.getAsJsonObject("result"));

            return new AwakeningRecipe(recipeId, inputs, essenceRequirements, result);
        }

        @Override
        public AwakeningRecipe read(Identifier recipeId, PacketByteBuf buffer) {
            int size = buffer.readVarInt();
            var inputs = DefaultedList.ofSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromPacket(buffer));
            }

            var essences = new AwakeningRecipe.EssenceVesselRequirements(
                    buffer.readVarInt(),
                    buffer.readVarInt(),
                    buffer.readVarInt(),
                    buffer.readVarInt()
            );

            ItemStack output = buffer.readItemStack();

            return new AwakeningRecipe(recipeId, inputs, essences, output);
        }

        @Override
        public void write(PacketByteBuf buffer, AwakeningRecipe recipe) {
            buffer.writeVarInt(5);

            // only send the non-vessel ingredients
            for (int i = 0; i <= 8; i += 2) {
                recipe.inputs.get(i).write(buffer);
            }

            buffer.writeVarInt(recipe.essences.air());
            buffer.writeVarInt(recipe.essences.earth());
            buffer.writeVarInt(recipe.essences.water());
            buffer.writeVarInt(recipe.essences.fire());

            buffer.writeItemStack(recipe.output);
        }
    }
}
