package com.alex.mysticalagriculture.api.farmland;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.crop.CropTierProvider;
import com.alex.mysticalagriculture.blocks.InfusedFarmlandBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface FarmlandConverter {

    FarmlandBlock getConvertedFarmland();

    default ActionResult convert(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        ItemStack stack = context.getStack();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block == Blocks.FARMLAND) {
            BlockState newState = this.getConvertedFarmland().getDefaultState().with(FarmlandBlock.MOISTURE, state.get(FarmlandBlock.MOISTURE));

            world.setBlockState(pos, newState);
            stack.decrement(1);
            return ActionResult.SUCCESS;

        } else if (block instanceof InfusedFarmlandBlock) {
            InfusedFarmlandBlock farmland = (InfusedFarmlandBlock) block;
            Item item = stack.getItem();

            if (item instanceof CropTierProvider) {
                CropTier tier = ((CropTierProvider) item).getTier();

                if (tier != farmland.getTier()) {
                    BlockState newState = this.getConvertedFarmland().getDefaultState().with(FarmlandBlock.MOISTURE, state.get(FarmlandBlock.MOISTURE));

                    world.setBlockState(pos, newState);
                    stack.decrement(1);

                    if (Math.random() < 0.25) {
                        Block.dropStack(world, pos.up(), new ItemStack(farmland.getTier().getEssence()));
                    }

                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }
}
