package com.alex.mysticalagriculture.zzz;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class BaseItemStackHandlerSlot extends Slot {
    private final BaseItemStackHandler inventory;
    private final int index;

    public BaseItemStackHandlerSlot(BaseItemStackHandler inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.inventory = inventory;
        this.index = index;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack.isEmpty())
            return false;
        return inventory.isValid(index, stack);
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        this.inventory.setSimulate(true);
        return !this.inventory.extractItem(this.index, 1, true).isEmpty();
    }

    @Override
    public ItemStack takeStack(int amount) {
        this.inventory.setSimulate(false);
        return this.inventory.extractItem(this.index, amount, true);
    }


}
