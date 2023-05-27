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
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

import java.util.Iterator;

public class ShapelessTagRecipe extends ShapelessRecipe {
    private final OutputResolver outputResolver;
    private ItemStack output;

    public ShapelessTagRecipe(Identifier id, String group, CraftingRecipeCategory category, DefaultedList<Ingredient> inputs, OutputResolver.Item outputResolver) {
        super(id, group, category, ItemStack.EMPTY, inputs);
        this.outputResolver = outputResolver;
    }

    public ShapelessTagRecipe(Identifier id, String group, CraftingRecipeCategory category, DefaultedList<Ingredient> inputs, String tag, int count) {
        super(id, group, category, ItemStack.EMPTY, inputs);
        this.outputResolver = new OutputResolver.Tag(tag, count);
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
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
            String group = JsonHelper.getString(json, "group", "");
            CraftingRecipeCategory category = (CraftingRecipeCategory)CraftingRecipeCategory.CODEC.byId(JsonHelper.getString(json, "category", (String)null), CraftingRecipeCategory.MISC);
            DefaultedList<Ingredient> ingredients = readIngredients(JsonHelper.getArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (ingredients.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe the max is 9");
            } else {
                JsonObject result = JsonHelper.getObject(json, "result");
                String tag = JsonHelper.getString(result, "tag");
                int count = JsonHelper.getInt(result, "count", 1);
                return new ShapelessTagRecipe(recipeId, group, category, ingredients, tag, count);
            }
        }

        @Override
        public ShapelessTagRecipe read(Identifier recipeId, PacketByteBuf buffer) {
            String group = buffer.readString(32767);
            int size = buffer.readVarInt();
            CraftingRecipeCategory category = (CraftingRecipeCategory)buffer.readEnumConstant(CraftingRecipeCategory.class);
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(size, Ingredient.EMPTY);

            for(int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.fromPacket(buffer));
            }

            OutputResolver.Item output = OutputResolver.create(buffer);
            return new ShapelessTagRecipe(recipeId, group, category, ingredients, output);
        }

        @Override
        public void write(PacketByteBuf buffer, ShapelessTagRecipe recipe) {
            buffer.writeString(recipe.getGroup());
            buffer.writeVarInt(recipe.getIngredients().size());
            buffer.writeEnumConstant(recipe.getCategory());
            Iterator var3 = recipe.getIngredients().iterator();

            while(var3.hasNext()) {
                Ingredient ingredient = (Ingredient)var3.next();
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.outputResolver.resolve());
        }

        private static DefaultedList<Ingredient> readIngredients(JsonArray ingredientArray) {
            DefaultedList<Ingredient> ingredients = DefaultedList.of();

            for(int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }

            return ingredients;
        }
    }
}
