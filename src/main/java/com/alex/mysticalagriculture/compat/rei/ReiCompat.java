package com.alex.mysticalagriculture.compat.rei;

import com.alex.mysticalagriculture.crafting.recipe.AwakeningRecipe;
import com.alex.mysticalagriculture.crafting.recipe.InfusionRecipe;
import com.alex.mysticalagriculture.crafting.recipe.ReprocessorRecipe;
import com.alex.mysticalagriculture.crafting.recipe.SoulExtractionRecipe;
import com.alex.mysticalagriculture.init.ModBlocks;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class ReiCompat implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        var infusionCategory = new InfusionCategory();
        registry.add(infusionCategory);
        registry.addWorkstations(infusionCategory.getCategoryIdentifier(), EntryStacks.of(ModBlocks.INFUSION_ALTAR), EntryStacks.of(ModBlocks.INFUSION_PEDESTAL));
        var awakeningCategory = new AwakeningCategory();
        registry.add(awakeningCategory);
        registry.addWorkstations(awakeningCategory.getCategoryIdentifier(), EntryStacks.of(ModBlocks.AWAKENING_ALTAR), EntryStacks.of(ModBlocks.AWAKENING_PEDESTAL), EntryStacks.of(ModBlocks.ESSENCE_VESSEL));

        var reprocessorCategory = new ReprocessorCategory();
        registry.add(reprocessorCategory);
        registry.addWorkstations(reprocessorCategory.getCategoryIdentifier(), EntryStacks.of(ModBlocks.REPROCESSOR));
        var soulExtractorCategory = new SoulExtractorCategory();
        registry.add(soulExtractorCategory);
        registry.addWorkstations(soulExtractorCategory.getCategoryIdentifier(), EntryStacks.of(ModBlocks.SOUL_EXTRACTOR));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(InfusionRecipe.class, ModRecipeTypes.INFUSION, InfusionCategory.RecipeDisplay::new);
        registry.registerRecipeFiller(AwakeningRecipe.class, ModRecipeTypes.AWAKENING, AwakeningCategory.RecipeDisplay::new);
        registry.registerRecipeFiller(ReprocessorRecipe.class, ModRecipeTypes.REPROCESSOR, ReprocessorCategory.RecipeDisplay::new);
        registry.registerRecipeFiller(SoulExtractionRecipe.class, ModRecipeTypes.SOUL_EXTRACTION, SoulExtractorCategory.RecipeDisplay::new);
    }
}
