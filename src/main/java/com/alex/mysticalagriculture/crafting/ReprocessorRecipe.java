package com.alex.mysticalagriculture.crafting;

import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;

/**
 * Used to represent a Reprocessor recipe for the recipe type
 */
@SuppressWarnings("unchecked")
public interface ReprocessorRecipe extends Recipe<Inventory> {
    default <T extends ReprocessorRecipe> T cast() {
        return (T) this;
    }
}
