package com.alex.mysticalagriculture.forge.common.extensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public interface ForgeItem {
    private Item self()
    {
        return (Item) this;
    }

    default boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player)
    {
        return false;
    }

    default boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
    {
        return false;
    }
}
