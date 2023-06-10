package com.alex.mysticalagriculture.container;

import com.alex.mysticalagriculture.blockentities.SoulExtractorBlockEntity;
import com.alex.mysticalagriculture.container.inventory.UpgradeItemStackHandler;
import com.alex.cucumber.container.BaseContainerMenu;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.cucumber.inventory.slot.BaseItemStackHandlerSlot;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.init.ScreenHandlerTypes;
import com.alex.mysticalagriculture.items.MachineUpgradeItem;
import com.alex.mysticalagriculture.util.RecipeIngredientCache;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SoulExtractorContainer extends BaseContainerMenu {
    private SoulExtractorContainer(MenuType<?> type, int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(type, id, playerInventory, SoulExtractorBlockEntity.createInventoryHandler(), new UpgradeItemStackHandler(), buffer.readBlockPos());
    }

    private SoulExtractorContainer(MenuType<?> type, int id, Inventory playerInventory, BaseItemStackHandler inventory, UpgradeItemStackHandler upgradeInventory, BlockPos pos) {
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

            if (index > 3) {
                if (itemstack1.getItem() instanceof MachineUpgradeItem) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack1.getItem() == Items.SOUL_JAR) {
                    if (!this.moveItemStackTo(itemstack1, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (SoulExtractorBlockEntity.getFuelTime(itemstack1) > 0) {
                    if (!this.moveItemStackTo(itemstack1, 2, 3, false)) {
                        if (RecipeIngredientCache.INSTANCE.isValidInput(itemstack1, RecipeTypes.SOUL_EXTRACTION)) {
                            if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else {
                            return ItemStack.EMPTY;
                        }
                    }
                } else if (RecipeIngredientCache.INSTANCE.isValidInput(itemstack1, RecipeTypes.SOUL_EXTRACTION)) {
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 31) {
                    if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 39 && !this.moveItemStackTo(itemstack1, 4, 31, false)) {
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

    public static SoulExtractorContainer create(int windowId, Inventory playerInventory, FriendlyByteBuf packetByteBuf) {
        return new SoulExtractorContainer(ScreenHandlerTypes.SOUL_EXTRACTOR, windowId, playerInventory, packetByteBuf);
    }

    public static SoulExtractorContainer create(int windowId, Inventory playerInventory, BaseItemStackHandler inventory, UpgradeItemStackHandler upgradeInventory, BlockPos pos) {
        return new SoulExtractorContainer(ScreenHandlerTypes.SOUL_EXTRACTOR, windowId, playerInventory, inventory, upgradeInventory, pos);
    }
}
