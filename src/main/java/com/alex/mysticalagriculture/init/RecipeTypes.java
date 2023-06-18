package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.api.crafting.IAwakeningRecipe;
import com.alex.mysticalagriculture.api.crafting.IInfusionRecipe;
import com.alex.mysticalagriculture.api.crafting.IReprocessorRecipe;
import com.alex.mysticalagriculture.api.crafting.ISoulExtractionRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypes {
    public static final RecipeType<IInfusionRecipe> INFUSION = new RecipeType<IInfusionRecipe>() {};
    public static final RecipeType<IAwakeningRecipe> AWAKENING = new RecipeType<IAwakeningRecipe>() {};
    public static final RecipeType<IReprocessorRecipe> REPROCESSOR = new RecipeType<IReprocessorRecipe>() {};
    public static final RecipeType<ISoulExtractionRecipe> SOUL_EXTRACTION = new RecipeType<ISoulExtractionRecipe>() {};

    //public static final RecipeType<InfusionCrystalRecipe> INFUSION_CRYSTAL = new RecipeType<InfusionCrystalRecipe>() {};

    public static void registerRecipeTypes() {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:infusion"), INFUSION);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:awakening"), AWAKENING);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:reprocessor"), REPROCESSOR);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:soul_extraction"), SOUL_EXTRACTION);
        //Registry.register(Registry.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:infusion_crystal"), INFUSION_CRYSTAL);
    }
}
