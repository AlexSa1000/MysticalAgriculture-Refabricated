package com.alex.mysticalagriculture.forge.common.extensions;

import com.alex.mysticalagriculture.forge.common.ForgeHooks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public interface ForgeBlock {
    private Block self()
    {
        return (Block) this;
    }

    static boolean canHarvestBlock(BlockState state, BlockView world, BlockPos pos, PlayerEntity player)
    {
        return ForgeHooks.isCorrectToolForDrops(state, player);
    }

    static boolean onDestroyedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid)
    {
        state.getBlock().onBreak(world, pos, state, player);
        return world.setBlockState(pos, fluid.getBlockState(), world.isClient ? 11 : 3);
    }
}
