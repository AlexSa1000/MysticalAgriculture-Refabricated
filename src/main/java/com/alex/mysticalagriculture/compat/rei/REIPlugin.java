package com.alex.mysticalagriculture.compat.rei;


import com.alex.mysticalagriculture.crafting.recipe.InfusionRecipe;
import com.alex.mysticalagriculture.crafting.recipe.ReprocessorRecipe;
import com.alex.mysticalagriculture.init.Blocks;
import me.shedaniel.rei.api.EntryRegistry;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import me.shedaniel.rei.plugin.DefaultPlugin;
import net.minecraft.util.Identifier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class REIPlugin implements REIPluginV0 {
    public static Identifier INFUSION = new Identifier(MOD_ID, "plugins/infusion");
    public static InfusionRecipeCategory INFUSION_CATEGORY = new InfusionRecipeCategory();

    public static Identifier REPROCESSOR = new Identifier(MOD_ID, "plugins/reprocessor");
    public static ReprocessorRecipeCategory REPROCESSOR_CATEGORY = new ReprocessorRecipeCategory();

    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier(MOD_ID, "plugin");
    }

    @Override
    public void registerEntries(EntryRegistry entryRegistry) {
        entryRegistry.registerEntries();
    }

    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
        recipeHelper.registerCategory(INFUSION_CATEGORY);
        recipeHelper.registerCategory(REPROCESSOR_CATEGORY);
    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        recipeHelper.registerRecipes(INFUSION, InfusionRecipe.class, InfusionRecipeDisplay::new);
        recipeHelper.registerRecipes(REPROCESSOR, ReprocessorRecipe.class, ReprocessorRecipeDisplay::new);

    }

    @Override
    public void registerOthers(RecipeHelper recipeHelper) {
        recipeHelper.removeAutoCraftButton(INFUSION);
        recipeHelper.registerWorkingStations(INFUSION, EntryStack.create(Blocks.INFUSION_ALTAR));
        recipeHelper.registerWorkingStations(INFUSION, EntryStack.create(Blocks.INFUSION_PEDESTAL));

        recipeHelper.removeAutoCraftButton(REPROCESSOR);
        recipeHelper.registerWorkingStations(REPROCESSOR, EntryStack.create(Blocks.BASIC_REPROCESSOR));
        recipeHelper.registerWorkingStations(REPROCESSOR, EntryStack.create(Blocks.INFERIUM_REPROCESSOR));
        recipeHelper.registerWorkingStations(REPROCESSOR, EntryStack.create(Blocks.PRUDENTIUM_REPROCESSOR));
        recipeHelper.registerWorkingStations(REPROCESSOR, EntryStack.create(Blocks.TERTIUM_REPROCESSOR));
        recipeHelper.registerWorkingStations(REPROCESSOR, EntryStack.create(Blocks.IMPERIUM_REPROCESSOR));
        recipeHelper.registerWorkingStations(REPROCESSOR, EntryStack.create(Blocks.SUPREMIUM_REPROCESSOR));

        recipeHelper.registerWorkingStations(DefaultPlugin.SMELTING, EntryStack.create(Blocks.INFERIUM_FURNACE));
        recipeHelper.registerWorkingStations(DefaultPlugin.SMELTING, EntryStack.create(Blocks.PRUDENTIUM_FURNACE));
        recipeHelper.registerWorkingStations(DefaultPlugin.SMELTING, EntryStack.create(Blocks.TERTIUM_FURNACE));
        recipeHelper.registerWorkingStations(DefaultPlugin.SMELTING, EntryStack.create(Blocks.IMPERIUM_FURNACE));
        recipeHelper.registerWorkingStations(DefaultPlugin.SMELTING, EntryStack.create(Blocks.SUPREMIUM_FURNACE));
    }

}
