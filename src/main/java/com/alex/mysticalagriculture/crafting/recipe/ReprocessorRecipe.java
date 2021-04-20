package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.crafting.SpecialRecipe;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class ReprocessorRecipe implements SpecialRecipe, com.alex.mysticalagriculture.crafting.ReprocessorRecipe {
    private final Identifier recipeId;
    private final DefaultedList<Ingredient> inputs;
    private final ItemStack output;

    public ReprocessorRecipe(Identifier recipeId, Ingredient input, ItemStack output) {
        this.recipeId = recipeId;
        this.inputs = DefaultedList.copyOf(Ingredient.EMPTY, input);
        this.output = output;
    }

    @Override
    public ItemStack craft(Inventory inv) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
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
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.REPROCESSOR;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.REPROCESSOR;
    }

    @Override
    public boolean matches(Inventory inventory, int startIndex, int endIndex) {
        ItemStack stack = inventory.getStack(0);
        return this.inputs.get(0).test(stack);
    }

    public static class Serializer implements RecipeSerializer<ReprocessorRecipe> {

        @Override
        public ReprocessorRecipe read(Identifier id, JsonObject json) {
            JsonObject ingredient = json.getAsJsonObject("input");
            Ingredient input = Ingredient.fromJson(ingredient);
            ItemStack output = ShapedRecipe.getItemStack(json.getAsJsonObject("result"));

            return new ReprocessorRecipe(id, input, output);
        }

        @Override
        public ReprocessorRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();

            return new ReprocessorRecipe(id, input, output);
        }

        @Override
        public void write(PacketByteBuf buf, ReprocessorRecipe recipe) {
            recipe.inputs.get(0).write(buf);
            buf.writeItemStack(recipe.output);
        }
    }
}
