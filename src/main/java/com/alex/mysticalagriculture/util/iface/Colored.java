package com.alex.mysticalagriculture.util.iface;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

public interface Colored {
    default int getColor(int index) {
        return -1;
    }

    default int getColor(int index, ItemStack stack) {
        return this.getColor(index);
    }

    class BlockColors implements BlockColorProvider {

        @Override
        public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
            return ((Colored) state.getBlock()).getColor(tintIndex);
        }
    }

    class ItemColors implements ItemColorProvider {
        @Override
        public int getColor(ItemStack stack, int index) {
            return ((Colored) stack.getItem()).getColor(index, stack);
        }
    }

    class ItemBlockColors implements ItemColorProvider {
        @Override
        public int getColor(ItemStack stack, int index) {
            return ((Colored) Block.getBlockFromItem(stack.getItem())).getColor(index, stack);
        }
    }
}
