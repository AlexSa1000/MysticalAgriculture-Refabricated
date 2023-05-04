package com.alex.mysticalagriculture.api.crafting;

import com.alex.mysticalagriculture.api.soul.MobSoulType;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;

public interface SoulExtractionRecipe extends Recipe<Inventory> {
    MobSoulType getMobSoulType();
    double getSouls();
}
