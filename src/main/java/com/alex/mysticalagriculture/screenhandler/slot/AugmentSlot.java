package com.alex.mysticalagriculture.screenhandler.slot;

import com.alex.mysticalagriculture.api.tinkering.AugmentProvider;
import com.alex.mysticalagriculture.api.tinkering.Tinkerable;
import com.alex.mysticalagriculture.zucchini.iface.ToggleableSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AugmentSlot extends Slot implements ToggleableSlot {
    private final ScreenHandler screenHandler;
    private final int augmentSlot;

    public AugmentSlot(ScreenHandler screenHandler, Inventory inventory, int index, int x, int y, int augmentSlot) {
        super(inventory, index, x, y);
        this.screenHandler = screenHandler;
        this.augmentSlot = augmentSlot;
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        super.onTakeItem(player, stack);
        this.screenHandler.onContentChanged(null);
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        this.screenHandler.onContentChanged(null);
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (!super.canInsert(stack))
            return false;

        var stackInSlot = this.inventory.getStack(0);
        var tinkerableItem = stackInSlot.getItem();
        var augmentItem = stack.getItem();

        if (tinkerableItem instanceof Tinkerable tinkerable && augmentItem instanceof AugmentProvider augmentProvider) {
            var augment = augmentProvider.getAugment();

            return tinkerable.canApplyAugment(augment);
        }

        return false;
    }

    @Override
    public boolean isEnabled() {
        var stack = this.inventory.getStack(0);
        var item = stack.getItem();

        if (item instanceof Tinkerable tinkerable) {
            return this.augmentSlot < tinkerable.getAugmentSlots();
        }

        return false;
    }
}
