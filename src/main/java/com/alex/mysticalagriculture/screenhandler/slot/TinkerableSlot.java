package com.alex.mysticalagriculture.screenhandler.slot;

import com.alex.mysticalagriculture.api.tinkering.Tinkerable;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.cucumber.inventory.BaseItemStackHandler;
import com.alex.mysticalagriculture.forge.items.SlotItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class TinkerableSlot extends SlotItemHandler {
    private final ScreenHandler screenHandler;

    public TinkerableSlot(ScreenHandler screenHandler, BaseItemStackHandler inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.screenHandler = screenHandler;
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        for (int i = 0; i < 2; i++) {
            this.getItemHandler().extractItem(i + 1, 1);
        }
    }

    @Override
    public void setStack(ItemStack stack) {
        for (int i = 0; i < 2; i++) {
            var augmentStack = this.getItemHandler().getStack(i + 1);
            if (!augmentStack.isEmpty()) {
                this.getItemHandler().extractItem(i + 1, augmentStack.getMaxCount());
            }

            var augment = AugmentUtils.getAugment(stack, i);
            if (augment != null) {
                this.getItemHandler().insertItem(i + 1, new ItemStack(augment.getItem()));
            }
        }

        super.setStack(stack);
        this.screenHandler.onContentChanged(null);
    }

    /*@Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() instanceof Tinkerable;
    }*/
}
