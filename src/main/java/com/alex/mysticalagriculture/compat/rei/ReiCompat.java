package com.alex.mysticalagriculture.compat.rei;

import com.alex.mysticalagriculture.crafting.recipe.AwakeningRecipe;
import com.alex.mysticalagriculture.crafting.recipe.InfusionRecipe;
import com.alex.mysticalagriculture.crafting.recipe.ReprocessorRecipe;
import com.alex.mysticalagriculture.crafting.recipe.SoulExtractionRecipe;
import com.alex.mysticalagriculture.init.Blocks;
import com.alex.mysticalagriculture.init.RecipeTypes;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class ReiCompat implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        var infusionCategory = new InfusionCategory();
        registry.add(infusionCategory);
        registry.addWorkstations(infusionCategory.getCategoryIdentifier(), EntryStacks.of(Blocks.INFUSION_ALTAR), EntryStacks.of(Blocks.INFUSION_PEDESTAL));
        var awakeningCategory = new AwakeningCategory();
        registry.add(awakeningCategory);
        registry.addWorkstations(awakeningCategory.getCategoryIdentifier(), EntryStacks.of(Blocks.AWAKENING_ALTAR), EntryStacks.of(Blocks.AWAKENING_PEDESTAL), EntryStacks.of(Blocks.ESSENCE_VESSEL));

        var reprocessorCategory = new ReprocessorCategory();
        registry.add(reprocessorCategory);
        registry.addWorkstations(reprocessorCategory.getCategoryIdentifier(), EntryStacks.of(Blocks.BASIC_REPROCESSOR), EntryStacks.of(Blocks.INFERIUM_REPROCESSOR), EntryStacks.of(Blocks.PRUDENTIUM_REPROCESSOR), EntryStacks.of(Blocks.TERTIUM_REPROCESSOR), EntryStacks.of(Blocks.IMPERIUM_REPROCESSOR), EntryStacks.of(Blocks.SUPREMIUM_REPROCESSOR));
        var soulExtractorCategory = new SoulExtractorCategory();
        registry.add(soulExtractorCategory);
        registry.addWorkstations(soulExtractorCategory.getCategoryIdentifier(), EntryStacks.of(Blocks.SOUL_EXTRACTOR));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(InfusionRecipe.class, RecipeTypes.INFUSION, InfusionCategory.RecipeDisplay::new);
        registry.registerRecipeFiller(AwakeningRecipe.class, RecipeTypes.AWAKENING, AwakeningCategory.RecipeDisplay::new);
        registry.registerRecipeFiller(ReprocessorRecipe.class, RecipeTypes.REPROCESSOR, ReprocessorCategory.RecipeDisplay::new);
        registry.registerRecipeFiller(SoulExtractionRecipe.class, RecipeTypes.SOUL_EXTRACTION, SoulExtractorCategory.RecipeDisplay::new);
    }
}
