package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.cucumber.util.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class FarmlandTillRecipe extends ShapelessRecipe {
    private final ItemStack result;

    public FarmlandTillRecipe(Identifier id, String group, ItemStack result, DefaultedList<Ingredient> inputs) {
        super(id, group, CraftingRecipeCategory.MISC, result, inputs);
        this.result = result;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inv) {
        var remaining = super.getRemainder(inv);

        for (int i = 0; i < inv.size(); i++) {
            var stack = inv.getStack(i);

            if (stack.getItem() instanceof HoeItem) {
                var hoe = stack.copy();

                if (!hoe.damage(1, Utils.RANDOM, null)) {
                    remaining.set(i, hoe);
                }
            }
        }
        return remaining;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.CRAFTING_FARMLAND_TILL;
    }

    public static class Serializer  implements RecipeSerializer<FarmlandTillRecipe> {
        @Override
        public FarmlandTillRecipe read(Identifier recipeId, JsonObject json) {
            var group = JsonHelper.getString(json, "group", "");
            var ingredients = readIngredients(JsonHelper.getArray(json, "ingredients"));

            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else {
                ItemStack itemstack = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
                return new FarmlandTillRecipe(recipeId, group, itemstack, ingredients);
            }
        }

        private static DefaultedList<Ingredient> readIngredients(JsonArray array) {
            DefaultedList<Ingredient> ingredients = DefaultedList.of();

            for (int i = 0; i < array.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(array.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }

            return ingredients;
        }

        @Override
        public FarmlandTillRecipe read(Identifier recipeId, PacketByteBuf buffer) {
            var group = buffer.readString(32767);
            int size = buffer.readVarInt();
            var inputs = DefaultedList.ofSize(size, Ingredient.EMPTY);

            for (int j = 0; j < inputs.size(); ++j) {
                inputs.set(j, Ingredient.fromPacket(buffer));
            }

            ItemStack itemstack = buffer.readItemStack();
            return new FarmlandTillRecipe(recipeId, group, itemstack, inputs);
        }

        @Override
        public void write(PacketByteBuf buffer, FarmlandTillRecipe recipe) {
            buffer.writeString(recipe.getGroup());
            buffer.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.result);
        }
    }
}
