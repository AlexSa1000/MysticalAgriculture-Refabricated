package com.alex.mysticalagriculture.util.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class NBTHelper {

    public static void setString(ItemStack stack, String key, String value) {
        getTagCompound(stack).putString(key, value);
    }

    public static void setBoolean(ItemStack stack, String key, boolean value) {
        getTagCompound(stack).putBoolean(key, value);
    }

    public static String getString(ItemStack stack, String key) {
        return stack.hasTag() ? getTagCompound(stack).getString(key) : "";
    }

    public static boolean getBoolean(ItemStack stack, String key) {
        return stack.hasTag() && getTagCompound(stack).getBoolean(key);
    }

    public static boolean hasKey(ItemStack stack, String key) {
        return stack.hasTag() && getTagCompound(stack).contains(key);
    }

    public static void flipBoolean(ItemStack stack, String key) {
        setBoolean(stack, key, !getBoolean(stack, key));
    }

    public static void validateCompound(ItemStack stack) {
        if (!stack.hasTag()) {
            CompoundTag tag = new CompoundTag();
            stack.setTag(tag);
        }
    }

    public static CompoundTag getTagCompound(ItemStack stack) {
        validateCompound(stack);
        return stack.getTag();
    }
}
