package com.alex.mysticalagriculture.zucchini.helper;

import net.minecraft.item.ItemStack;

public class StackHelper {
    public static ItemStack withSize(ItemStack stack, int size, boolean container) {
        if (size <= 0) {
            if (container && stack.getItem().hasRecipeRemainder()) {
                return new ItemStack(stack.getItem().getRecipeRemainder());
            } else {
                return ItemStack.EMPTY;
            }
        }

        stack = stack.copy();
        stack.setCount(size);

        return stack;
    }

    public static ItemStack grow(ItemStack stack, int amount) {
        return withSize(stack, stack.getCount() + amount, false);
    }

    public static ItemStack shrink(ItemStack stack, int amount, boolean container) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        return withSize(stack, stack.getCount() - amount, container);
    }

    public static boolean canCombineStacks(ItemStack stack1, ItemStack stack2) {
        if (!stack1.isEmpty() && stack2.isEmpty()) return true;
        return areStacksEqual(stack1, stack2) && (stack1.getCount() + stack2.getCount()) <= stack1.getMaxCount();
    }

    public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
        return !stack1.isEmpty() && !stack2.isEmpty() && stack1.isItemEqual(stack2);
    }
    public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return areItemsEqual(stack1, stack2) && ItemStack.areNbtEqual(stack1, stack2);
    }

    public static ItemStack combineStacks(ItemStack stack1, ItemStack stack2) {
        if (stack1.isEmpty())
            return stack2.copy();

        return grow(stack1, stack2.getCount());
    }
}
