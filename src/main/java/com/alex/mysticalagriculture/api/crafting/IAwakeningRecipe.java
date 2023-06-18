package com.alex.mysticalagriculture.api.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

public interface IAwakeningRecipe extends Recipe<Container> {
    EssenceVesselRequirements getEssenceRequirements();

    record EssenceVesselRequirements(int air, int earth, int water, int fire) { }
}
