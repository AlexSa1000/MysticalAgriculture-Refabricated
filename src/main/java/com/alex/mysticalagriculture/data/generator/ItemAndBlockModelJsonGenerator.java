package com.alex.mysticalagriculture.data.generator;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Optional;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ItemAndBlockModelJsonGenerator extends FabricModelProvider {
    public ItemAndBlockModelJsonGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            var block = crop.getCropBlock();

            if (crop.shouldRegisterCropBlock()) {
                var stemModel = crop.getType().getStemModel();
                int[] ageTextureIndices = {0, 1, 2, 3, 4, 5, 6, 7};

                BlockStateVariantMap blockStateVariantMap = BlockStateVariantMap.create(Properties.AGE_7).register(integer -> {
                    int i = ageTextureIndices[integer];
                    if (i == 7)
                        return BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(MOD_ID, "block/" + crop.getNameWithSuffix("crop")));
                    return BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(stemModel.getNamespace(), stemModel.getPath() + "_" + i));
                });
                blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(blockStateVariantMap));

                var mysticalCrop7Model = new Model(Optional.of(new Identifier(stemModel.getNamespace(), stemModel.getPath() + "_7")), Optional.empty());
                mysticalCrop7Model.upload(new Identifier(MOD_ID, "block/" + crop.getNameWithSuffix("crop")), new TextureMap().register(TextureKey.of("flower"), crop.getTextures().getFlowerTexture()), blockStateModelGenerator.modelCollector);
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (crop.shouldRegisterEssenceItem()) {
                Models.GENERATED.upload(new Identifier(MOD_ID, "item/" + crop.getNameWithSuffix("essence")), TextureMap.layer0(crop.getTextures().getEssenceTexture()), itemModelGenerator.writer);

            }

            if (crop.shouldRegisterSeedsItem()) {
                Models.GENERATED.upload(new Identifier(MOD_ID, "item/" + crop.getNameWithSuffix("seeds")), TextureMap.layer0(crop.getTextures().getSeedTexture()), itemModelGenerator.writer);
            }
        }

        var augmentModel = new Model(Optional.of(new Identifier(MOD_ID, "item/augment")), Optional.empty());

        for (var augment : AugmentRegistry.getInstance().getAugments()) {
            itemModelGenerator.register(augment.getItem(), augmentModel);
        }
    }
}
