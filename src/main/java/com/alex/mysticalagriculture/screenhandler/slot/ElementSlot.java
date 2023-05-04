package com.alex.mysticalagriculture.screenhandler.slot;

import com.alex.mysticalagriculture.api.tinkering.ElementalItem;
import com.alex.mysticalagriculture.zucchini.iface.ToggleableSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ElementSlot extends Slot implements ToggleableSlot {
    private final ScreenHandler screenHandler;

    public ElementSlot(ScreenHandler screenHandler, Inventory inventory, int index, int x, int y) {
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
        var stack = this.inventory.getStack(0);
        var item = stack.getItem();

        return item instanceof ElementalItem;
    }
}
