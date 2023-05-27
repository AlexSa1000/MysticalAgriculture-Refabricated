package com.alex.mysticalagriculture.cucumber.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.StairsBlock;
import net.minecraft.sound.BlockSoundGroup;

public class BaseStairsBlock extends StairsBlock {

    public BaseStairsBlock(BlockState state, Settings settings) {
        super(state, settings);
    }

    public BaseStairsBlock(BlockState state, Material material, BlockSoundGroup sound, float hardness, float resistance, boolean tool) {
        this(state, FabricBlockSettings.of(material).sounds(sound).strength(hardness, resistance).requiresTool());
    }
}
