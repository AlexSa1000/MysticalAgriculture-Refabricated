package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.crafting.recipe.AwakeningRecipe;
import com.alex.mysticalagriculture.crafting.recipe.InfusionRecipe;
import com.alex.mysticalagriculture.crafting.recipe.ReprocessorRecipe;
import com.alex.mysticalagriculture.crafting.recipe.SoulExtractionRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final RecipeType<InfusionRecipe> INFUSION = new RecipeType<InfusionRecipe>() {};
    public static final RecipeType<AwakeningRecipe> AWAKENING = new RecipeType<AwakeningRecipe>() {};
    public static final RecipeType<ReprocessorRecipe> REPROCESSOR = new RecipeType<ReprocessorRecipe>() {};
    public static final RecipeType<SoulExtractionRecipe> SOUL_EXTRACTION = new RecipeType<SoulExtractionRecipe>() {};

    public static void registerRecipeTypes() {
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:infusion"), INFUSION);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:awakening"), AWAKENING);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:reprocessor"), REPROCESSOR);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation("mysticalagriculture:soul_extraction"), SOUL_EXTRACTION);
    }
}
