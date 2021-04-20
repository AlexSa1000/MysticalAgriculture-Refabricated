package com.alex.mysticalagriculture.container.slot;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentProvider;
import com.alex.mysticalagriculture.api.tinkerer.Tinkerable;
import com.alex.mysticalagriculture.util.iface.ToggleableSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
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
    public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
        ItemStack take = super.onTakeItem(player, stack);
        this.screenHandler.onContentChanged(null);
        return take;
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        this.screenHandler.onContentChanged(null);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (!super.canInsert(stack))
            return false;

        ItemStack stackInSlot = this.inventory.getStack(0);
        Item tinkerableItem = stackInSlot.getItem();
        Item augmentItem = stack.getItem();
        if (tinkerableItem instanceof Tinkerable && augmentItem instanceof AugmentProvider) {
            Tinkerable tinkerable = (Tinkerable) tinkerableItem;
            Augment augment = ((AugmentProvider) augmentItem).getAugment();

            return tinkerable.canApplyAugment(augment);
        }

        return false;
    }

    @Override
    public boolean doDrawHoveringEffect() {
        ItemStack stack = this.inventory.getStack(0);
        Item item = stack.getItem();
        if (item instanceof Tinkerable) {
            Tinkerable tinkerable = (Tinkerable) item;
            return this.augmentSlot < tinkerable.getAugmentSlots();
        }

        return false;
    }
}
