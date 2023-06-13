package com.alex.mysticalagriculture.api.crafting;

import com.alex.mysticalagriculture.api.soul.MobSoulType;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

public interface ISoulExtractionRecipe extends Recipe<Container> {
    MobSoulType getMobSoulType();
    double getSouls();
}
