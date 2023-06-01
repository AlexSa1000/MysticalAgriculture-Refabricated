package com.alex.mysticalagriculture.data.generator;

import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.registry.CropRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.util.registry.Registry;

public class ItemTagsJsonGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagsJsonGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            var essenceId = Registry.ITEM.getId(crop.getEssenceItem());
            this.getOrCreateTagBuilder(MysticalAgricultureAPI.ESSENCES_TAG).add(essenceId);
            var seedsId = Registry.ITEM.getId(crop.getSeedsItem().asItem());
            this.getOrCreateTagBuilder(MysticalAgricultureAPI.SEEDS_TAG).add(seedsId);
        }
    }
}
