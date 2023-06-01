package com.alex.mysticalagriculture.cucumber.crafting.recipe;

import com.alex.mysticalagriculture.cucumber.crafting.OutputResolver;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class ShapedTagRecipe extends ShapedNoMirrorRecipe {
    private final OutputResolver outputResolver;
    private ItemStack output;

    public ShapedTagRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> inputs, OutputResolver.Item outputResolver) {
        super(id, group, width, height, inputs, ItemStack.EMPTY);
        this.outputResolver = outputResolver;
    }

    public ShapedTagRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> inputs, String tag, int count) {
        super(id, group, width, height, inputs, ItemStack.EMPTY);
        this.outputResolver = new OutputResolver.Tag(tag, count);
    }

    @Override
    public ItemStack getOutput() {
        if (this.output == null) {
            this.output = this.outputResolver.resolve();
        }

        return this.output;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        if (this.output == null) {
            this.output = this.outputResolver.resolve();
        }

        return this.output.isEmpty();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.CRAFTING_SHAPED_TAG;
    }

    public static class Serializer implements RecipeSerializer<ShapedTagRecipe> {
        @Override
        public ShapedTagRecipe read(Identifier recipeId, JsonObject json) {
            var group = JsonHelper.getString(json, "group", "");
            var key = ShapedRecipe.readSymbols(JsonHelper.getObject(json, "key"));
            var pattern = ShapedRecipe.getPattern(JsonHelper.getArray(json, "pattern"));
            var width = pattern[0].length();
            var height = pattern.length;
            var ingredients = ShapedRecipe.createPatternMatrix(pattern, key, width, height);
            var result = JsonHelper.getObject(json, "result");
            var tag = JsonHelper.getString(result, "tag");
            var count = JsonHelper.getInt(result, "count", 1);

            return new ShapedTagRecipe(recipeId, group, width, height, ingredients, tag, count);
        }

        @Override
        public ShapedTagRecipe read(Identifier recipeId, PacketByteBuf buffer) {
            var group = buffer.readString(32767);
            var width = buffer.readVarInt();
            var height = buffer.readVarInt();
            var ingredients = DefaultedList.ofSize(width * height, Ingredient.EMPTY);

            for (int k = 0; k < ingredients.size(); ++k) {
                ingredients.set(k, Ingredient.fromPacket(buffer));
            }

            var output = OutputResolver.create(buffer);

            return new ShapedTagRecipe(recipeId, group, width, height, ingredients, output);
        }

        @Override
        public void write(PacketByteBuf buffer, ShapedTagRecipe recipe) {
            buffer.writeString(recipe.getGroup());
            buffer.writeVarInt(recipe.getWidth());
            buffer.writeVarInt(recipe.getHeight());

            for (var ingredient : recipe.getIngredients()) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.outputResolver.resolve());
        }
    }
}
