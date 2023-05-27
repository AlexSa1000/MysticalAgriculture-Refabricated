package com.alex.mysticalagriculture.cucumber.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.SlabBlock;
import net.minecraft.sound.BlockSoundGroup;

public class BaseSlabBlock extends SlabBlock {

    public BaseSlabBlock(Settings settings) {
        super(settings);
    }


    public BaseSlabBlock(Material material, BlockSoundGroup sound, float hardness, float resistance, boolean tool) {
        this(FabricBlockSettings.of(material).sounds(sound).strength(hardness, resistance).requiresTool());
    }
}
