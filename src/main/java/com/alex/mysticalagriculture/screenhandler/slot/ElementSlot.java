package com.alex.mysticalagriculture.screenhandler.slot;

import com.alex.mysticalagriculture.api.tinkering.IElementalItem;
import com.alex.mysticalagriculture.cucumber.iface.ToggleableSlot;
import com.alex.mysticalagriculture.cucumber.inventory.BaseItemStackHandler;
import com.alex.mysticalagriculture.forge.items.SlotItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ElementSlot extends SlotItemHandler implements ToggleableSlot {
    private final ScreenHandler screenHandler;

    public ElementSlot(ScreenHandler screenHandler, BaseItemStackHandler inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.screenHandler = screenHandler;
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
    public boolean isEnabled() {
        var stack = this.getItemHandler().getStack(0);
        var item = stack.getItem();

        return item instanceof IElementalItem;
    }
}
