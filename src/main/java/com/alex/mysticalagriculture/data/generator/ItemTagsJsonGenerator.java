package com.alex.mysticalagriculture.data.generator;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.registry.CropRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.concurrent.CompletableFuture;

public class ItemTagsJsonGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagsJsonGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            var essenceId = BuiltInRegistries.ITEM.getKey(crop.getEssenceItem());
            this.getOrCreateTagBuilder(MysticalAgricultureAPI.ESSENCES_TAG).add(essenceId);
            var seedsId = BuiltInRegistries.ITEM.getKey(crop.getSeedsItem().asItem());
            this.getOrCreateTagBuilder(MysticalAgricultureAPI.SEEDS_TAG).add(seedsId);
        }
    }
}
