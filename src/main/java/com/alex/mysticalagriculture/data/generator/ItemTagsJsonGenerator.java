package com.alex.mysticalagriculture.data.generator;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.registry.CropRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagsJsonGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagsJsonGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            var essenceId = Registries.ITEM.getId(crop.getEssenceItem());
            this.getOrCreateTagBuilder(MysticalAgricultureAPI.ESSENCES_TAG).add(essenceId);
            var seedsId = Registries.ITEM.getId(crop.getSeedsItem().asItem());
            this.getOrCreateTagBuilder(MysticalAgricultureAPI.SEEDS_TAG).add(seedsId);
        }
    }

    @Override
    public String getName() {
        return MysticalAgriculture.NAME +  " item tags generator";
    }
}
