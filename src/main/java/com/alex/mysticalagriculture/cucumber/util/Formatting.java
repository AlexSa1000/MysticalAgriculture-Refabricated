package com.alex.mysticalagriculture.cucumber.util;

import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.text.NumberFormat;
import java.util.Locale;

public final class Formatting {
    public Formatting() {
    }

    public static MutableText number(Object value) {
        return Text.literal(NumberFormat.getInstance(new Locale("en", "US")).format(value));
    }

    public static MutableText percent(Object value) {
        return number(value).append("%");
    }

    public static MutableText energy(Object value) {
        return number(value).append(" FE");
    }

    public static MutableText perTick(Object value) {
        return number(value).append(" /t");
    }

    public static MutableText energyPerTick(Object value) {
        return number(value).append(" FE/t");
    }

    public static MutableText itemWithCount(ItemStack stack) {
        return itemWithCount(stack, stack.getCount());
    }

    public static MutableText itemWithCount(ItemStack stack, int count) {
        return Text.literal("" + count + "x " + stack.getName());
    }
}
