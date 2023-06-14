package com.alex.mysticalagriculture.data.generator;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.registry.CropRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.concurrent.CompletableFuture;

public class BlockTagsJsonGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagsJsonGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            var id = BuiltInRegistries.BLOCK.getKey(crop.getCropBlock());
            this.getOrCreateTagBuilder(MysticalAgricultureAPI.CROPS_TAG).add(id);
        }
    }

    @Override
    public String getName() {
        return MysticalAgriculture.NAME +  " block tags generator";
    }
}
