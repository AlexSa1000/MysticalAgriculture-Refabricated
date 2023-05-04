package com.alex.mysticalagriculture.zucchini.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Function;

public class BaseGlassBlock extends GlassBlock {
    public BaseGlassBlock(Material material, Function<Settings, Settings> settings) {
        super(settings.apply(Settings.of(material))
                .nonOpaque()
                .allowsSpawning(BaseGlassBlock::never)
                .solidBlock(BaseGlassBlock::never)
                .suffocates(BaseGlassBlock::never)
                .blockVision(BaseGlassBlock::never)
        );
    }

    public BaseGlassBlock(Material material, BlockSoundGroup sound, float hardness, float resistance) {
        super(Settings.of(material)
                .sounds(sound)
                .strength(hardness, resistance)
                .nonOpaque()
                .allowsSpawning(BaseGlassBlock::never)
                .solidBlock(BaseGlassBlock::never)
                .suffocates(BaseGlassBlock::never)
                .blockVision(BaseGlassBlock::never)
        );
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}
