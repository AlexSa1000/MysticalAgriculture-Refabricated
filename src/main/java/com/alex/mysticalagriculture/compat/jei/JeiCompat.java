package com.alex.mysticalagriculture.compat.jei;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.client.screen.ReprocessorScreen;
import com.alex.mysticalagriculture.client.screen.SoulExtractorScreen;
import com.alex.mysticalagriculture.compat.jei.category.AwakeningCategory;
import com.alex.mysticalagriculture.compat.jei.category.InfusionCategory;
import com.alex.mysticalagriculture.compat.jei.category.ReprocessorCategory;
import com.alex.mysticalagriculture.compat.jei.category.SoulExtractorCategory;
import com.alex.mysticalagriculture.init.Blocks;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.init.RecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public final class JeiCompat implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(MysticalAgriculture.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                new InfusionCategory(guiHelper),
                new AwakeningCategory(guiHelper),
                new ReprocessorCategory(guiHelper),
                new SoulExtractorCategory(guiHelper)
                //new CruxCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Blocks.INFUSION_ALTAR), InfusionCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.INFUSION_PEDESTAL), InfusionCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.AWAKENING_ALTAR), AwakeningCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.AWAKENING_PEDESTAL), AwakeningCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.ESSENCE_VESSEL), AwakeningCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.BASIC_REPROCESSOR), ReprocessorCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.INFERIUM_REPROCESSOR), ReprocessorCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.PRUDENTIUM_REPROCESSOR), ReprocessorCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.TERTIUM_REPROCESSOR), ReprocessorCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.IMPERIUM_REPROCESSOR), ReprocessorCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.SUPREMIUM_REPROCESSOR), ReprocessorCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.SOUL_EXTRACTOR), SoulExtractorCategory.RECIPE_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var level = Minecraft.getInstance().level;
        if (level != null) {
            var manager = level.getRecipeManager();

            registration.addRecipes(InfusionCategory.RECIPE_TYPE, manager.getAllRecipesFor(RecipeTypes.INFUSION));
            registration.addRecipes(AwakeningCategory.RECIPE_TYPE, manager.getAllRecipesFor(RecipeTypes.AWAKENING));
            registration.addRecipes(ReprocessorCategory.RECIPE_TYPE, manager.getAllRecipesFor(RecipeTypes.REPROCESSOR));
            registration.addRecipes(SoulExtractorCategory.RECIPE_TYPE, manager.getAllRecipesFor(RecipeTypes.SOUL_EXTRACTION));
            //registration.addRecipes(CruxCategory.RECIPE_TYPE, CruxRecipe.getGeneratedRecipes());
        }

        registration.addIngredientInfo(
                new ItemStack(Items.COGNIZANT_DUST),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.desc.mysticalagriculture.cognizant_dust")
        );
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ReprocessorScreen.class, 99, 52, 22, 15, ReprocessorCategory.RECIPE_TYPE);
        registration.addRecipeClickArea(SoulExtractorScreen.class, 99, 52, 22, 15, SoulExtractorCategory.RECIPE_TYPE);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, Items.SOUL_JAR, (stack, context) -> {
            var type = MobSoulUtils.getType(stack);
            return type != null ? type.getEntityIds().toString() : "";
        });
    }
}
