package com.alex.mysticalagriculture.util.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.WallBlock;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;

public class BaseWallBlock extends WallBlock {

    public BaseWallBlock(Settings settings) {
        super(settings);
    }

    public BaseWallBlock(Material material, BlockSoundGroup sound, float hardness, float resistance, Tag<Item> tool) {
        this(FabricBlockSettings.of(material).sounds(sound).strength(hardness, resistance).breakByTool(tool));
    }
}
