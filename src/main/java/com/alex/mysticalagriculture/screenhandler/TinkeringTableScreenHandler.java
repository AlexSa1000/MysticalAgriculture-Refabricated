package com.alex.mysticalagriculture.screenhandler;

import com.alex.mysticalagriculture.api.tinkering.AugmentProvider;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.blockentities.TinkeringTableBlockEntity;
import com.alex.mysticalagriculture.cucumber.inventory.BaseItemStackHandler;
import com.alex.mysticalagriculture.screenhandler.slot.AugmentSlot;
import com.alex.mysticalagriculture.screenhandler.slot.ElementSlot;
import com.alex.mysticalagriculture.screenhandler.slot.TinkerableSlot;
import com.alex.mysticalagriculture.init.ScreenHandlerTypes;
import com.alex.mysticalagriculture.cucumber.screenhandler.BaseScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class TinkeringTableScreenHandler extends BaseScreenHandler {
    private final BaseItemStackHandler inventory;

    private TinkeringTableScreenHandler(ScreenHandlerType<?> type, int id, PlayerInventory playerInventory, PacketByteBuf buffer) {
        this(type, id, playerInventory, TinkeringTableBlockEntity.createInventoryHandler(), buffer.readBlockPos());
    }

    private TinkeringTableScreenHandler(ScreenHandlerType<?> type, int id, PlayerInventory playerInventory, BaseItemStackHandler inventory, BlockPos pos) {
        super(type, id, pos);
        this.inventory = inventory;

        this.addSlot(new TinkerableSlot(this, inventory, 0, 80, 49));
        this.addSlot(new AugmentSlot(this, inventory, 1, 140, 36, 0));
        this.addSlot(new AugmentSlot(this, inventory, 2, 140, 62, 1));

        this.addSlot(new ElementSlot(this, inventory, 3, 20, 18));
        this.addSlot(new ElementSlot(this, inventory, 4, 20, 39));
        this.addSlot(new ElementSlot(this, inventory, 5, 20, 60));
        this.addSlot(new ElementSlot(this, inventory, 6, 20, 81));

        for (int k = 0; k < 3; k++) {
            for (int i1 = 0; i1 < 9; i1++) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 115 + k * 18));
            }
        }

        for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 173));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        var tinkerable = this.inventory.getStack(0);

        if (!tinkerable.isEmpty()) {
            for (int i = 0; i < 2; i++) {
                var stack = this.inventory.getStack(i + 1);
                var item = stack.getItem();
                var augmentInSlot = AugmentUtils.getAugment(tinkerable, i);

                if (!stack.isEmpty() && item instanceof AugmentProvider) {
                    var augment = ((AugmentProvider) item).getAugment();
                    if (augment != augmentInSlot)
                        AugmentUtils.addAugment(tinkerable, augment, i);
                } else if (augmentInSlot != null) {
                    AugmentUtils.removeAugment(tinkerable, i);
                }
            }
        }

        super.onContentChanged(inventory);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int slotNumber) {
        var itemstack = ItemStack.EMPTY;
        var slot = this.slots.get(slotNumber);

        if (slot.hasStack()) {
            var itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotNumber == 0) {
                if (!this.insertItem(itemstack1, 7, 43, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemstack1, itemstack);
            } else if (slotNumber >= 7 && slotNumber < 43) {
                if (!this.insertItem(itemstack1, 0, 7, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemstack1, 7, 43, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemstack1);
        }

        return itemstack;
    }

    public static TinkeringTableScreenHandler create(int windowId, PlayerInventory playerInventory, PacketByteBuf buffer) {
        return new TinkeringTableScreenHandler(ScreenHandlerTypes.TINKERING_TABLE, windowId, playerInventory, buffer);
    }

    public static TinkeringTableScreenHandler create(int windowId, PlayerInventory playerInventory, BaseItemStackHandler inventory, BlockPos pos) {
        return new TinkeringTableScreenHandler(ScreenHandlerTypes.TINKERING_TABLE, windowId, playerInventory, inventory, pos);
    }
}
