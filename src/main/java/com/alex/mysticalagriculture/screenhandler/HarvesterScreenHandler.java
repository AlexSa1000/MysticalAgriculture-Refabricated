package com.alex.mysticalagriculture.screenhandler;

import com.alex.mysticalagriculture.blockentities.HarvesterBlockEntity;
import com.alex.mysticalagriculture.blockentities.SoulExtractorBlockEntity;
import com.alex.mysticalagriculture.init.ScreenHandlerTypes;
import com.alex.mysticalagriculture.items.MachineUpgradeItem;
import com.alex.mysticalagriculture.screenhandler.inventory.UpgradeItemStackHandler;
import com.alex.mysticalagriculture.zucchini.screenhandler.BaseScreenHandler;
import com.alex.mysticalagriculture.zzz.BaseItemStackHandler;
import com.alex.mysticalagriculture.zzz.BaseItemStackHandlerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class HarvesterScreenHandler extends BaseScreenHandler {
    private HarvesterScreenHandler(ScreenHandlerType<?> type, int id, Inventory playerInventory, PacketByteBuf buffer) {
        this(type, id, playerInventory, HarvesterBlockEntity.createInventoryHandler(), new UpgradeItemStackHandler(), buffer.readBlockPos());
    }

    private HarvesterScreenHandler(ScreenHandlerType<?> type, int id, Inventory playerInventory, BaseItemStackHandler inventory, UpgradeItemStackHandler upgradeInventory, BlockPos pos) {
        super(type, id, pos);

        this.addSlot(new BaseItemStackHandlerSlot(upgradeInventory, 0, 152, 9));
        this.addSlot(new BaseItemStackHandlerSlot(inventory, 0, 30, 56));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                this.addSlot(new BaseItemStackHandlerSlot(inventory, 1 + j + i * 5, 80 + j * 18, 42 + i * 18));
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 112 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 170));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        var itemstack = ItemStack.EMPTY;
        var slot = this.slots.get(index);

        if (slot.hasStack()) {
            var itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index > 1) {
                if (itemstack1.getItem() instanceof MachineUpgradeItem) {
                    if (!this.insertItem(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (HarvesterBlockEntity.getFuelTime(itemstack1) > 0) {
                    if (!this.insertItem(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 44) {
                    if (!this.insertItem(itemstack1, 44, 53, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 53 && !this.insertItem(itemstack1, 17, 44, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemstack1, 17, 53, false)) {
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

    public static HarvesterScreenHandler create(int windowId, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        return new HarvesterScreenHandler(ScreenHandlerTypes.HARVESTER, windowId, playerInventory, packetByteBuf);
    }

    public static HarvesterScreenHandler create(int windowId, PlayerInventory playerInventory, BaseItemStackHandler inventory, UpgradeItemStackHandler upgradeInventory, BlockPos pos) {
        return new HarvesterScreenHandler(ScreenHandlerTypes.HARVESTER, windowId, playerInventory, inventory, upgradeInventory, pos);
    }
}
