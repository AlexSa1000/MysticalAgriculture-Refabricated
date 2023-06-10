package com.alex.mysticalagriculture.container;

import com.alex.mysticalagriculture.blockentities.HarvesterBlockEntity;
import com.alex.mysticalagriculture.container.inventory.UpgradeItemStackHandler;
import com.alex.cucumber.container.BaseContainerMenu;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.cucumber.inventory.slot.BaseItemStackHandlerSlot;
import com.alex.mysticalagriculture.init.ScreenHandlerTypes;
import com.alex.mysticalagriculture.items.MachineUpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class HarvesterContainer extends BaseContainerMenu {
    private HarvesterContainer(MenuType<?> type, int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(type, id, playerInventory, HarvesterBlockEntity.createInventoryHandler(), new UpgradeItemStackHandler(), buffer.readBlockPos());
    }

    private HarvesterContainer(MenuType<?> type, int id, Inventory playerInventory, BaseItemStackHandler inventory, UpgradeItemStackHandler upgradeInventory, BlockPos pos) {
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
    public ItemStack quickMoveStack(Player player, int index) {
        var itemstack = ItemStack.EMPTY;
        var slot = this.slots.get(index);

        if (slot.hasItem()) {
            var itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index > 1) {
                if (itemstack1.getItem() instanceof MachineUpgradeItem) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (HarvesterBlockEntity.getFuelTime(itemstack1) > 0) {
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 44) {
                    if (!this.moveItemStackTo(itemstack1, 44, 53, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 53 && !this.moveItemStackTo(itemstack1, 17, 44, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 17, 53, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    public static HarvesterContainer create(int windowId, Inventory playerInventory, FriendlyByteBuf packetByteBuf) {
        return new HarvesterContainer(ScreenHandlerTypes.HARVESTER, windowId, playerInventory, packetByteBuf);
    }

    public static HarvesterContainer create(int windowId, Inventory playerInventory, BaseItemStackHandler inventory, UpgradeItemStackHandler upgradeInventory, BlockPos pos) {
        return new HarvesterContainer(ScreenHandlerTypes.HARVESTER, windowId, playerInventory, inventory, upgradeInventory, pos);
    }
}
