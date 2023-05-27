package com.alex.mysticalagriculture.cucumber.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class NBTHelper {

    public static void setString(ItemStack stack, String key, String value) {
        getTagCompound(stack).putString(key, value);
    }

    public static void setBoolean(ItemStack stack, String key, boolean value) {
        getTagCompound(stack).putBoolean(key, value);
    }

    public static String getString(ItemStack stack, String key) {
        return stack.hasNbt() ? getTagCompound(stack).getString(key) : "";
    }

    public static boolean getBoolean(ItemStack stack, String key) {
        return stack.hasNbt() && getTagCompound(stack).getBoolean(key);
    }

    public static boolean hasKey(ItemStack stack, String key) {
        return stack.hasNbt() && getTagCompound(stack).contains(key);
    }

    public static void flipBoolean(ItemStack stack, String key) {
        setBoolean(stack, key, !getBoolean(stack, key));
    }

    public static void validateCompound(ItemStack stack) {
        if (!stack.hasNbt()) {
            NbtCompound tag = new NbtCompound();
            stack.setNbt(tag);
        }
    }

    public static NbtCompound getTagCompound(ItemStack stack) {
        validateCompound(stack);
        return stack.getNbt();
    }
}
