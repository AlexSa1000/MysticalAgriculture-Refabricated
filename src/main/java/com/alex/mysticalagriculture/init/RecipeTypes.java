package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.crafting.recipe.InfusionCrystalRecipe;
import com.alex.mysticalagriculture.crafting.recipe.InfusionRecipe;
import com.alex.mysticalagriculture.crafting.recipe.ReprocessorRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipeTypes {
    public static final RecipeType<InfusionRecipe> INFUSION = new RecipeType<InfusionRecipe>() {};
    public static final RecipeType<ReprocessorRecipe> REPROCESSOR = new RecipeType<ReprocessorRecipe>() {};

    public static final RecipeType<InfusionCrystalRecipe> INFUSION_CRYSTAL = new RecipeType<InfusionCrystalRecipe>() {};

    public static void registerRecipeTypes() {
        Registry.register(Registry.RECIPE_TYPE, new Identifier("mysticalagriculture:infusion"), INFUSION);
        Registry.register(Registry.RECIPE_TYPE, new Identifier("mysticalagriculture:reprocessor"), REPROCESSOR);

        Registry.register(Registry.RECIPE_TYPE, new Identifier("mysticalagriculture:infusion_crystal"), INFUSION_CRYSTAL);
    }
}
