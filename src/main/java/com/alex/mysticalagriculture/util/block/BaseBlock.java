package com.alex.mysticalagriculture.util.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;

import java.util.function.Function;

public class BaseBlock extends Block {
    public BaseBlock(Material material, Function<FabricBlockSettings, FabricBlockSettings> settings) {
        super(settings.apply(FabricBlockSettings.of(material)));
    }

    public BaseBlock(Material material, BlockSoundGroup sound, float hardness, float resistance) {
        super(FabricBlockSettings.of(material).sounds(sound).strength(hardness, resistance));
    }

    public BaseBlock(Material material, BlockSoundGroup sound, float hardness, float resistance, Tag<Item> tool) {
        super(FabricBlockSettings.of(material).sounds(sound).strength(hardness, resistance).breakByTool(tool).requiresTool());
    }
}
