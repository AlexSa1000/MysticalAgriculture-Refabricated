package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.util.block.BaseBlock;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class WitherproofBlock extends BaseBlock {

    public WitherproofBlock() {
        super(Material.STONE, p -> p
                .strength(20.0F, 2000.0F)
                .sounds(BlockSoundGroup.STONE)
                .breakByTool(FabricToolTags.PICKAXES, 1)
        );
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) { }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }
}
