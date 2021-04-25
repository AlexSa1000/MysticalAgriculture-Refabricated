package com.alex.mysticalagriculture.api.farmland;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface FarmlandConverter {

    FarmlandBlock getConvertedFarmland();

    default ActionResult convert(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == Blocks.FARMLAND) {
            BlockState newState = this.getConvertedFarmland().getDefaultState().with(FarmlandBlock.MOISTURE, state.get(FarmlandBlock.MOISTURE));
            world.setBlockState(pos, newState);
            context.getStack().decrement(1);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
