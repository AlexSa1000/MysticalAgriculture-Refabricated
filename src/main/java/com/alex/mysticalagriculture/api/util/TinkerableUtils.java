package com.alex.mysticalagriculture.api.util;

import com.alex.mysticalagriculture.api.tinkering.Tinkerable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TinkerableUtils {

    public static Tinkerable getTinkerable(ItemStack stack) {
        var item = stack.getItem();
        return item instanceof Tinkerable tinkerable ? tinkerable : null;
    }

    public static int getArmorSetMinimumTier(PlayerEntity player) {
        int tier = -1;

        for (int i = 0; i < 4; i++) {
            var stack = player.getEquippedStack(EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, i));
            var tinkerable = getTinkerable(stack);

            if (tinkerable == null)
                return -1;
        }

        return tier;
    }

    public static boolean hasArmorSetMinimumTier(PlayerEntity player, int tier) {
        return getArmorSetMinimumTier(player) == tier;
    }
}
