package com.alex.mysticalagriculture.container.slot;

import com.alex.mysticalagriculture.api.tinkerer.ElementalItem;
import com.alex.mysticalagriculture.util.iface.ToggleableSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
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
    public boolean doDrawHoveringEffect() {
        ItemStack stack = this.inventory.getStack(0);
        Item item = stack.getItem();
        return item instanceof ElementalItem;
    }
}
