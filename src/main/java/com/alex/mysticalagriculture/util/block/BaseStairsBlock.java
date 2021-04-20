package com.alex.mysticalagriculture.util.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;

public class BaseStairsBlock extends StairsBlock {

    public BaseStairsBlock(BlockState state, Settings settings) {
        super(state, settings);
    }

    public BaseStairsBlock(BlockState state, Material material, BlockSoundGroup sound, float hardness, float resistance, Tag<Item> tool) {
        this(state, FabricBlockSettings.of(material).sounds(sound).strength(hardness, resistance).breakByTool(tool));
    }
}
