package com.alex.mysticalagriculture.cucumber.crafting.recipe;

import com.alex.mysticalagriculture.cucumber.crafting.OutputResolver;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class ShapelessTagRecipe extends ShapelessRecipe {
    private final OutputResolver outputResolver;
    private ItemStack output;

    public ShapelessTagRecipe(Identifier id, String group, DefaultedList<Ingredient> inputs, OutputResolver.Item outputResolver) {
        super(id, group, ItemStack.EMPTY, inputs);
        this.outputResolver = outputResolver;
    }

    public ShapelessTagRecipe(Identifier id, String group, DefaultedList<Ingredient> inputs, String tag, int count) {
        super(id, group, ItemStack.EMPTY, inputs);
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
        return RecipeSerializers.CRAFTING_SHAPELESS_TAG;
    }

    public static class Serializer implements RecipeSerializer<ShapelessTagRecipe> {
        @Override
        public ShapelessTagRecipe read(Identifier recipeId, JsonObject json) {
            var group = JsonHelper.getString(json, "group", "");
            var ingredients = readIngredients(JsonHelper.getArray(json, "ingredients"));

            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (ingredients.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe the max is 9");
            }

            var result = JsonHelper.getObject(json, "result");
            var tag = JsonHelper.getString(result, "tag");
            var count = JsonHelper.getInt(result, "count", 1);

            return new ShapelessTagRecipe(recipeId, group, ingredients, tag, count);
        }

        @Override
        public ShapelessTagRecipe read(Identifier recipeId, PacketByteBuf buffer) {
            var group = buffer.readString(32767);
            var size = buffer.readVarInt();
            var ingredients = DefaultedList.ofSize(size, Ingredient.EMPTY);

            for(int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.fromPacket(buffer));
            }

            var output = OutputResolver.create(buffer);

            return new ShapelessTagRecipe(recipeId, group, ingredients, output);
        }

        @Override
        public void write(PacketByteBuf buffer, ShapelessTagRecipe recipe) {
            buffer.writeString(recipe.getGroup());
            buffer.writeVarInt(recipe.getIngredients().size());

            for (var ingredient : recipe.getIngredients()) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.outputResolver.resolve());
        }

        private static DefaultedList<Ingredient> readIngredients(JsonArray ingredientArray) {
            DefaultedList<Ingredient> ingredients = DefaultedList.of();

            for(int i = 0; i < ingredientArray.size(); ++i) {
                var ingredient = Ingredient.fromJson(ingredientArray.get(i));

                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }

            return ingredients;
        }
    }
}
