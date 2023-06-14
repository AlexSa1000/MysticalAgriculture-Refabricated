package com.alex.mysticalagriculture.data.generator;

import com.alex.mysticalagriculture.data.recipe.InfusionRecipeBuilder;
import com.alex.mysticalagriculture.data.recipe.ReprocessorRecipeBuilder;
import com.alex.mysticalagriculture.registry.CropRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class RecipeJsonGenerator extends FabricRecipeProvider {
    public RecipeJsonGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            //var craftingId = "seed/crafting/" + crop.getName();
            //CraftingRecipeBuilder.newSeedRecipe(crop).build(consumer, new Identifier(crop.getModId(), craftingId));

            var infusionId = "seed/infusion/" + crop.getName();
            InfusionRecipeBuilder.newSeedRecipe(crop).build(exporter, new ResourceLocation(crop.getModId(), infusionId));

            var reprocessorId = "seed/reprocessor/" + crop.getName();
            ReprocessorRecipeBuilder.newSeedReprocessingRecipe(crop).build(exporter, new ResourceLocation(crop.getModId(), reprocessorId));
        }
    }
}
