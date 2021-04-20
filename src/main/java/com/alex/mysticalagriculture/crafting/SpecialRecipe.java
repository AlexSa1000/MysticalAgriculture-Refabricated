package com.alex.mysticalagriculture.crafting;

import com.alex.mysticalagriculture.util.inventory.RecipeMatcher;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public interface SpecialRecipe extends Recipe<Inventory> {

    @Override
    default boolean matches(Inventory inv, World world) {
        return this.matches(inv);
    }

    default boolean matches(Inventory inventory) {
        return this.matches(inventory, 0, inventory.size());
    }

    default boolean matches(Inventory inventory, int startIndex, int endIndex) {
        DefaultedList<ItemStack> inputs = DefaultedList.of();
        for (int i = startIndex; i < endIndex; i++) {
            inputs.add(inventory.getStack(i));
        }

        return RecipeMatcher.findMatches(inputs, this.getPreviewInputs()) != null;
    }
}
