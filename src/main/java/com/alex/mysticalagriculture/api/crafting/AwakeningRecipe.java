package com.alex.mysticalagriculture.api.crafting;

import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;

public interface AwakeningRecipe extends Recipe<Inventory> {
    EssenceVesselRequirements getEssenceRequirements();

    record EssenceVesselRequirements(int air, int earth, int water, int fire) { }
}
