package com.alex.mysticalagriculture.cucumber.helper;

import com.google.common.collect.ImmutableMap;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class RecipeHelper {
    public static RecipeManager recipeManager;

    public RecipeHelper() {
    }

    private static RecipeManager getRecipeManager() {
        if (recipeManager.recipes instanceof ImmutableMap) {
            recipeManager.recipes = new HashMap(recipeManager.recipes);
            recipeManager.recipes.replaceAll((t, v) -> {
                return new HashMap((Map)recipeManager.recipes.get(t));
            });
        }

        if (recipeManager.recipesById instanceof ImmutableMap) {
            recipeManager.recipesById = new HashMap(recipeManager.recipesById);
        }

        return recipeManager;
    }

    public static Map<RecipeType<?>, Map<Identifier, Recipe<?>>> getRecipes() {
        return getRecipeManager().recipes;
    }

    public static <C extends Inventory, T extends Recipe<C>> Map<Identifier, T> getRecipes(RecipeType<T> type) {
        return getRecipeManager().getAllOfType(type);
    }

    public static void addRecipe(Recipe<?> recipe) {
        ((Map)getRecipeManager().recipes.computeIfAbsent(recipe.getType(), (t) -> {
            return new HashMap();
        })).put(recipe.getId(), recipe);
        getRecipeManager().recipesById.put(recipe.getId(), recipe);
    }
}
