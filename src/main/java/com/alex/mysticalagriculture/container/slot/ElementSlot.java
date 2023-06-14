package com.alex.mysticalagriculture.container.slot;

import com.alex.cucumber.forge.items.SlotItemHandler;
import com.alex.cucumber.iface.ToggleableSlot;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.mysticalagriculture.api.tinkering.IElementalItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ElementSlot extends SlotItemHandler implements ToggleableSlot {
    private final AbstractContainerMenu container;

    public ElementSlot(AbstractContainerMenu container, BaseItemStackHandler inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
        this.container = container;
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
    public boolean isActive() {
        var stack = this.getItemHandler().getItem(0);
        var item = stack.getItem();

        return item instanceof IElementalItem;
    }
}
