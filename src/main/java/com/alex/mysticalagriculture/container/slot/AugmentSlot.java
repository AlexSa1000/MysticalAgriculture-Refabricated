package com.alex.mysticalagriculture.container.slot;

import com.alex.cucumber.forge.items.SlotItemHandler;
import com.alex.cucumber.iface.ToggleableSlot;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.mysticalagriculture.api.tinkering.AugmentProvider;
import com.alex.mysticalagriculture.api.tinkering.Tinkerable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class AugmentSlot extends SlotItemHandler implements ToggleableSlot {
    private final AbstractContainerMenu container;
    private final int augmentSlot;

    public AugmentSlot(AbstractContainerMenu container, BaseItemStackHandler inventory, int index, int x, int y, int augmentSlot) {
        super(inventory, index, x, y);
        this.container = container;
        this.augmentSlot = augmentSlot;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        super.onTake(player, stack);
        this.container.slotsChanged(null);
    }

    @Override
    public void set(ItemStack stack) {
        super.set(stack);
        this.container.slotsChanged(null);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (!super.mayPlace(stack))
            return false;

        var stackInSlot = this.getItemHandler().getItem(0);
        var tinkerableItem = stackInSlot.getItem();
        var augmentItem = stack.getItem();

        if (tinkerableItem instanceof Tinkerable tinkerable && augmentItem instanceof AugmentProvider augmentProvider) {
            var augment = augmentProvider.getAugment();

            return tinkerable.canApplyAugment(augment);
        }

        return false;
    }

    @Override
    public boolean isActive() {
        var stack = this.getItemHandler().getItem(0);
        var item = stack.getItem();

        if (item instanceof Tinkerable tinkerable) {
            return this.augmentSlot < tinkerable.getAugmentSlots();
        }

        return false;
    }
}
