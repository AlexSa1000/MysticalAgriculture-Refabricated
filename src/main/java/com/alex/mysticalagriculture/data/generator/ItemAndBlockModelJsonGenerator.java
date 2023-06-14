package com.alex.mysticalagriculture.data.generator;

import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ItemAndBlockModelJsonGenerator extends FabricModelProvider {
    public ItemAndBlockModelJsonGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            var block = crop.getCropBlock();

            if (crop.shouldRegisterCropBlock()) {
                var stemModel = crop.getType().getStemModel();
                int[] ageTextureIndices = {0, 1, 2, 3, 4, 5, 6, 7};

                var propertyDispatch = PropertyDispatch.property(BlockStateProperties.AGE_7).generate(integer -> {
                    int i = ageTextureIndices[integer];
                    if (i == 7)
                        return Variant.variant().with(VariantProperties.MODEL, new ResourceLocation(MOD_ID, "block/" + crop.getNameWithSuffix("crop")));
                    return Variant.variant().with(VariantProperties.MODEL, new ResourceLocation(stemModel.getNamespace(), stemModel.getPath() + "_" + i));
                });
                blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(propertyDispatch));

                var mysticalCrop7Model = new ModelTemplate(Optional.of(new ResourceLocation(stemModel.getNamespace(), stemModel.getPath() + "_7")), Optional.empty());
                mysticalCrop7Model.create(new ResourceLocation(MOD_ID, "block/" + crop.getNameWithSuffix("crop")), new TextureMapping().put(TextureSlot.create("flower"), crop.getTextures().getFlowerTexture()), blockStateModelGenerator.modelOutput);
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (crop.shouldRegisterEssenceItem()) {
                ModelTemplates.FLAT_ITEM.create(new ResourceLocation(MOD_ID, "item/" + crop.getNameWithSuffix("essence")), TextureMapping.layer0(crop.getTextures().getEssenceTexture()), itemModelGenerator.output);

            }

            if (crop.shouldRegisterSeedsItem()) {
                ModelTemplates.FLAT_ITEM.create(new ResourceLocation(MOD_ID, "item/" + crop.getNameWithSuffix("seeds")), TextureMapping.layer0(crop.getTextures().getSeedTexture()), itemModelGenerator.output);
            }
        }

        var augmentModel = new ModelTemplate(Optional.of(new ResourceLocation(MOD_ID, "item/augment")), Optional.empty());

        for (var augment : AugmentRegistry.getInstance().getAugments()) {
            itemModelGenerator.generateFlatItem(augment.getItem(), augmentModel);
        }
    }
}