package com.alex.mysticalagriculture.data;

import com.alex.mysticalagriculture.data.generator.BlockTagsJsonGenerator;
import com.alex.mysticalagriculture.data.generator.ItemAndBlockModelJsonGenerator;
import com.alex.mysticalagriculture.data.generator.ItemTagsJsonGenerator;
import com.alex.mysticalagriculture.data.generator.RecipeJsonGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

        fabricDataGenerator.addProvider(RecipeJsonGenerator::new);
        fabricDataGenerator.addProvider(ItemAndBlockModelJsonGenerator::new);
        fabricDataGenerator.addProvider(BlockTagsJsonGenerator::new);
        fabricDataGenerator.addProvider(ItemTagsJsonGenerator::new);
    }
}
