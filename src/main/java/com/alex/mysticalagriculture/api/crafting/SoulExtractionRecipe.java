package com.alex.mysticalagriculture.api.crafting;

import com.alex.mysticalagriculture.api.soul.MobSoulType;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

public interface SoulExtractionRecipe extends Recipe<Container> {
    MobSoulType getMobSoulType();
    double getSouls();
}
