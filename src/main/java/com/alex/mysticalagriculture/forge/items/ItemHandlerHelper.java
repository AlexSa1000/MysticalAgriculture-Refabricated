package com.alex.mysticalagriculture.forge.items;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemHandlerHelper {
    @NotNull
    public static ItemStack copyStackWithSize(@NotNull ItemStack itemStack, int size)
    {
        if (size == 0)
            return ItemStack.EMPTY;
        ItemStack copy = itemStack.copy();
        copy.setCount(size);
        return copy;
    }
}
