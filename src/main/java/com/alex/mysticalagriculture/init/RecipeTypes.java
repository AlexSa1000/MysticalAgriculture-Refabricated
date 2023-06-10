package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.api.crafting.SoulExtractionRecipe;
import com.alex.mysticalagriculture.crafting.recipe.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypes {
    public static final RecipeType<com.alex.mysticalagriculture.api.crafting.InfusionRecipe> INFUSION = new RecipeType<com.alex.mysticalagriculture.api.crafting.InfusionRecipe>() {};
    public static final RecipeType<com.alex.mysticalagriculture.api.crafting.AwakeningRecipe> AWAKENING = new RecipeType<com.alex.mysticalagriculture.api.crafting.AwakeningRecipe>() {};
    public static final RecipeType<com.alex.mysticalagriculture.api.crafting.ReprocessorRecipe> REPROCESSOR = new RecipeType<com.alex.mysticalagriculture.api.crafting.ReprocessorRecipe>() {};
    public static final RecipeType<com.alex.mysticalagriculture.api.crafting.SoulExtractionRecipe> SOUL_EXTRACTION = new RecipeType<SoulExtractionRecipe>() {};

    //public static final RecipeType<InfusionCrystalRecipe> INFUSION_CRYSTAL = new RecipeType<InfusionCrystalRecipe>() {};

    public static void registerRecipeTypes() {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:infusion"), INFUSION);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:awakening"), AWAKENING);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:reprocessor"), REPROCESSOR);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:soul_extraction"), SOUL_EXTRACTION);
        //Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:infusion_crystal"), INFUSION_CRYSTAL);
    }
}
