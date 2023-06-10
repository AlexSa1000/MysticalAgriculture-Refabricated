package com.alex.mysticalagriculture.blocks;

import com.alex.cucumber.block.BaseBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class WitherproofBlock extends BaseBlock {
    public WitherproofBlock() {
        super(Material.STONE, p -> p
                .strength(20.0F, 2000.0F)
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops()
        );
    }

    @Override
    public void wasExploded(Level world, BlockPos pos, Explosion explosion) { }

    @Override
    public boolean dropFromExplosion(Explosion explosion) {
        return false;
    }
}
