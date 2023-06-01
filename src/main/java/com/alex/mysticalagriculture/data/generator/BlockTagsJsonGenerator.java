package com.alex.mysticalagriculture.data.generator;

import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.registry.CropRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.util.registry.Registry;

public class BlockTagsJsonGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagsJsonGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            var id = Registry.BLOCK.getId(crop.getCropBlock());
            this.getOrCreateTagBuilder(MysticalAgricultureAPI.CROPS_TAG).add(id);
        }
    }
}
