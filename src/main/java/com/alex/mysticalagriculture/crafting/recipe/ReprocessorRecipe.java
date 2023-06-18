package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.cucumber.crafting.SpecialRecipe;
import com.alex.mysticalagriculture.api.crafting.IReprocessorRecipe;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ReprocessorRecipe implements SpecialRecipe, IReprocessorRecipe {
    private final ResourceLocation recipeId;
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;

    public ReprocessorRecipe(ResourceLocation recipeId, Ingredient input, ItemStack output) {
        this.recipeId = recipeId;
        this.inputs = NonNullList.of(Ingredient.EMPTY, input);
        this.output = output;
    }

    @Override
    public ItemStack assemble(Container inventory) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.REPROCESSOR;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.REPROCESSOR;
    }

    @Override
    public boolean matches(Container inventory) {
        ItemStack stack = inventory.getItem(0);
        return this.inputs.get(0).test(stack);
    }

    public static class Serializer implements RecipeSerializer<ReprocessorRecipe> {

        @Override
        public ReprocessorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var ingredient = json.getAsJsonObject("input");
            var input = Ingredient.fromJson(ingredient);
            var output = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result"));

            return new ReprocessorRecipe(recipeId, input, output);
        }

        @Override
        public ReprocessorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            var input = Ingredient.fromNetwork(buffer);
            var output = buffer.readItem();

            return new ReprocessorRecipe(recipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ReprocessorRecipe recipe) {
            recipe.inputs.get(0).toNetwork(buffer);
            buffer.writeItem(recipe.output);
        }
    }
}
