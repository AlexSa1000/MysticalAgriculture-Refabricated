package com.alex.mysticalagriculture.zucchini.iface;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface CustomBow {
    default float getDrawSpeedMulti(ItemStack stack) {
        return 1.0F;
    }

    boolean hasFOVChange();

    static ItemStack findAmmo(PlayerEntity player) {
        if (isArrow(player.getStackInHand(Hand.OFF_HAND))) {
            return player.getStackInHand(Hand.OFF_HAND);
        } else if (isArrow(player.getStackInHand(Hand.MAIN_HAND))) {
            return player.getStackInHand(Hand.MAIN_HAND);
        } else {
            for (int i = 0; i < player.getInventory().size(); i++) {
                var stack = player.getInventory().getStack(i);
                if (isArrow(stack)) {
                    return stack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    static boolean isArrow(ItemStack stack) {
        return stack.getItem() instanceof ArrowItem;
    }
}
