package com.alex.mysticalagriculture.util.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Function;

public class BaseGlassBlock extends GlassBlock {
    public BaseGlassBlock(Material material, Function<FabricBlockSettings, FabricBlockSettings> settings) {
        super(settings.apply(FabricBlockSettings.of(material)).nonOpaque());
    }

    public BaseGlassBlock(Material material, BlockSoundGroup sound, float hardness, float resistance) {
        super(FabricBlockSettings.of(material).sounds(sound).strength(hardness, resistance).nonOpaque());
    }
}
