package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.crafting.recipe.*;
import net.minecraft.core.Registry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypes {
    public static final RecipeType<InfusionRecipe> INFUSION = new RecipeType<InfusionRecipe>() {};
    public static final RecipeType<AwakeningRecipe> AWAKENING = new RecipeType<AwakeningRecipe>() {};
    public static final RecipeType<ReprocessorRecipe> REPROCESSOR = new RecipeType<ReprocessorRecipe>() {};
    public static final RecipeType<SoulExtractionRecipe> SOUL_EXTRACTION = new RecipeType<SoulExtractionRecipe>() {};

    public static final RecipeType<InfusionCrystalRecipe> INFUSION_CRYSTAL = new RecipeType<InfusionCrystalRecipe>() {};

    public static void registerRecipeTypes() {
        Registry.register(Registries.RECIPE_TYPE, new Identifier("mysticalagriculture:infusion"), INFUSION);
        Registry.register(Registries.RECIPE_TYPE, new Identifier("mysticalagriculture:awakening"), AWAKENING);
        Registry.register(Registries.RECIPE_TYPE, new Identifier("mysticalagriculture:reprocessor"), REPROCESSOR);
        Registry.register(Registries.RECIPE_TYPE, new Identifier("mysticalagriculture:soul_extraction"), SOUL_EXTRACTION);
        Registry.register(Registries.RECIPE_TYPE, new Identifier("mysticalagriculture:infusion_crystal"), INFUSION_CRYSTAL);
    }
}
