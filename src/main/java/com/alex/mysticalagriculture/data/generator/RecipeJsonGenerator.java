package com.alex.mysticalagriculture.data.generator;

import com.alex.mysticalagriculture.data.recipe.InfusionRecipeBuilder;
import com.alex.mysticalagriculture.data.recipe.ReprocessorRecipeBuilder;
import com.alex.mysticalagriculture.registry.CropRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class RecipeJsonGenerator extends FabricRecipeProvider {
    public RecipeJsonGenerator(FabricDataGenerator output) {
        super(output);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            //var craftingId = "seed/crafting/" + crop.getName();
            //CraftingRecipeBuilder.newSeedRecipe(crop).build(consumer, new Identifier(crop.getModId(), craftingId));

            var infusionId = "seed/infusion/" + crop.getName();
            InfusionRecipeBuilder.newSeedRecipe(crop).build(exporter, new Identifier(crop.getModId(), infusionId));

            var reprocessorId = "seed/reprocessor/" + crop.getName();
            ReprocessorRecipeBuilder.newSeedReprocessingRecipe(crop).build(exporter, new Identifier(crop.getModId(), reprocessorId));
        }
    }
}
