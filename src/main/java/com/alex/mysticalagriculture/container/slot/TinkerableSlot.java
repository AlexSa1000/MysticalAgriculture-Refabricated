package com.alex.mysticalagriculture.container.slot;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.Tinkerable;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class TinkerableSlot extends Slot {
    private final ScreenHandler screenHandler;

    public TinkerableSlot(ScreenHandler screenHandler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.screenHandler = screenHandler;
    }

    @Override
    public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
        for (int i = 0; i < 2; i++) {
            this.inventory.removeStack(i + 1, 1);
        }
        return super.onTakeItem(player, stack);
    }

    @Override
    public void setStack(ItemStack stack) {
        for (int i = 0; i < 2; i++) {
            ItemStack augmentStack = this.inventory.getStack(i + 1);
            if (!augmentStack.isEmpty()) {
                this.inventory.removeStack(i + 1, augmentStack.getMaxCount());
            }

            Augment augment = AugmentUtils.getAugment(stack, i);
            if (augment != null) {
                this.inventory.setStack(i + 1, new ItemStack(augment.getItem()));
            }
        }

        super.setStack(stack);
        this.screenHandler.onContentChanged(null);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() instanceof Tinkerable;
    }
}
