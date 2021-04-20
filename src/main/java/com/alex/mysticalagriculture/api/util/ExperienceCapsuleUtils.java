package com.alex.mysticalagriculture.api.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class ExperienceCapsuleUtils {

    public static final int MAX_XP_POINTS = 1200;


    public static CompoundTag makeTag(int xp) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Experience", Math.min(MAX_XP_POINTS, xp));

        return nbt;
    }

    public static int getExperience(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("Experience"))
            return nbt.getInt("Experience");

        return 0;
    }

    public static int addExperienceToCapsule(ItemStack stack, int amount) {
        int xp = getExperience(stack);
        if (xp >= MAX_XP_POINTS) {
            return amount;
        } else {
            int newAmount = Math.min(MAX_XP_POINTS, xp + amount);
            CompoundTag nbt = stack.getTag();
            if (nbt == null) {
                CompoundTag tag = makeTag(newAmount);
                stack.setTag(tag);
            } else {
                nbt.putInt("Experience", newAmount);
            }

            return Math.max(0, amount - (newAmount - xp));
        }
    }
}
