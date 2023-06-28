package com.alex.mysticalagriculture.container;

import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.cucumber.container.BaseContainerMenu;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.cucumber.inventory.slot.BaseItemStackHandlerSlot;
import com.alex.mysticalagriculture.container.inventory.UpgradeItemStackHandler;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import com.alex.mysticalagriculture.init.ModContainerTypes;
import com.alex.mysticalagriculture.items.MachineUpgradeItem;
import com.alex.mysticalagriculture.util.RecipeIngredientCache;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class ReprocessorContainer extends BaseContainerMenu {
    private ReprocessorContainer(MenuType<?> type, int id, Inventory playerInventory, BlockPos pos) {
        this(type, id, playerInventory, ReprocessorBlockEntity.createInventoryHandler(), new UpgradeItemStackHandler(), pos);
    }

    private ReprocessorContainer(MenuType<?> type, int id, Inventory playerInventory, BaseItemStackHandler inventory, UpgradeItemStackHandler upgradeInventory, BlockPos pos) {
        super(type, id, pos);

        this.addSlot(new BaseItemStackHandlerSlot(upgradeInventory, 0, 152, 9));

        this.addSlot(new BaseItemStackHandlerSlot(inventory, 0, 74, 52));
        this.addSlot(new BaseItemStackHandlerSlot(inventory, 1, 30, 56));
        this.addSlot(new BaseItemStackHandlerSlot(inventory, 2, 134, 52));

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

            if (index == 3) {
                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 2 && index != 1) {
                if (itemstack1.getItem() instanceof MachineUpgradeItem) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (RecipeIngredientCache.INSTANCE.isValidInput(itemstack1, ModRecipeTypes.REPROCESSOR)) {
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 31) {
                    if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 40 && !this.moveItemStackTo(itemstack1, 4, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
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

    public boolean isFuel(ItemStack itemStack) {
        return AbstractFurnaceBlockEntity.isFuel(itemStack);
    }

    public static ReprocessorContainer create(int windowId, Inventory playerInventory, FriendlyByteBuf packetByteBuf) {
        return new ReprocessorContainer(ModContainerTypes.REPROCESSOR, windowId, playerInventory, packetByteBuf.readBlockPos());
    }

    public static ReprocessorContainer create(int windowId, Inventory playerInventory, BaseItemStackHandler inventory, UpgradeItemStackHandler upgradeInventory, BlockPos pos) {
        return new ReprocessorContainer(ModContainerTypes.REPROCESSOR, windowId, playerInventory, inventory, upgradeInventory, pos);
    }
}
