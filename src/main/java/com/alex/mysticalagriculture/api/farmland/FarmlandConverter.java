package com.alex.mysticalagriculture.api.farmland;

import com.alex.mysticalagriculture.api.crop.CropTierProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public final class FarmlandConverter {
    /**
     * Call this using {@link Item#useOnBlock(ItemUsageContext)} to allow default farmland conversion mechanics
     */
    public static ActionResult convert(IFarmlandConverter converter, ItemUsageContext context) {
        var pos = context.getBlockPos();
        var world = context.getWorld();
        var stack = context.getStack();
        var state = world.getBlockState(pos);
        var block = state.getBlock();

        if (block == Blocks.FARMLAND) {
            var newState = converter.getConvertedFarmland().getDefaultState().with(FarmlandBlock.MOISTURE, state.get(FarmlandBlock.MOISTURE));

            world.setBlockState(pos, newState);
            stack.decrement(1);

            return ActionResult.SUCCESS;
        } else if (block instanceof EssenceFarmLand farmland) {
            var item = stack.getItem();

            if (item instanceof CropTierProvider provider) {
                var tier = provider.getTier();

                if (tier != farmland.getTier()) {
                    var newState = converter.getConvertedFarmland().getDefaultState().with(FarmlandBlock.MOISTURE, state.get(FarmlandBlock.MOISTURE));

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
