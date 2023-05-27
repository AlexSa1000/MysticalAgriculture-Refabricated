package com.alex.mysticalagriculture.forge.items;

import com.alex.mysticalagriculture.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class SlotItemHandler extends Slot {

    private static Inventory emptyInventory = new SimpleInventory(0);
    private final BaseItemStackHandler itemHandler;
    private final int index;

    public SlotItemHandler(BaseItemStackHandler itemHandler, int index, int x, int y) {
        super(emptyInventory, index, x, y);
        this.itemHandler = itemHandler;
        this.index = index;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack.isEmpty())
            return false;
        return itemHandler.isValid(index, stack);
    }

    @Override
    public ItemStack getStack() {
        return this.getItemHandler().getStack(index);
    }

    @Override
    public void setStackNoCallbacks(ItemStack stack) {
        this.getItemHandler().setStackInSlot(index, stack);
        this.markDirty();
    }

    @Override
    public void onQuickTransfer(ItemStack newItem, ItemStack original) {

    }

    @Override
    public int getMaxItemCount() {
        return this.itemHandler.getSlotLimit(this.index);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        ItemStack maxAdd = stack.copy();
        int maxInput = stack.getMaxCount();
        maxAdd.setCount(maxInput);

        BaseItemStackHandler handler = this.getItemHandler();
        ItemStack currentStack = handler.getStack(index);
        handler.setStackInSlot(index, ItemStack.EMPTY);

        handler.setSimulate(true);
        ItemStack remainder = handler.insertItem(index, maxAdd);

        handler.setStackInSlot(index, currentStack);

        return maxInput - remainder.getCount();
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        this.getItemHandler().setSimulate(true);
        return !this.getItemHandler().extractItem(index, 1).isEmpty();
    }

    @Override
    public ItemStack takeStack(int amount) {
        return this.getItemHandler().extractItem(index, amount);
    }

    public BaseItemStackHandler getItemHandler() {
        return itemHandler;
    }
}
