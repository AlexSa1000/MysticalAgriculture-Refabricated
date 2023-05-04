package com.alex.mysticalagriculture.screenhandler;

import com.alex.mysticalagriculture.blockentities.SoulExtractorBlockEntity;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.init.ScreenHandlerTypes;
import com.alex.mysticalagriculture.items.MachineUpgradeItem;
import com.alex.mysticalagriculture.screenhandler.inventory.UpgradeItemStackHandler;
import com.alex.mysticalagriculture.util.RecipeIngredientCache;
import com.alex.mysticalagriculture.zucchini.screenhandler.BaseScreenHandler;
import com.alex.mysticalagriculture.zzz.BaseItemStackHandler;
import com.alex.mysticalagriculture.zzz.BaseItemStackHandlerSlot;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class SoulExtractorScreenHandler extends BaseScreenHandler {
    private SoulExtractorScreenHandler(ScreenHandlerType<?> type, int id, Inventory playerInventory, PacketByteBuf buffer) {
        this(type, id, playerInventory, SoulExtractorBlockEntity.createInventoryHandler(), new UpgradeItemStackHandler(), buffer.readBlockPos());
    }

    private SoulExtractorScreenHandler(ScreenHandlerType<?> type, int id, Inventory playerInventory, BaseItemStackHandler inventory, UpgradeItemStackHandler upgradeInventory, BlockPos pos) {
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
    public ItemStack quickMove(PlayerEntity player, int index) {
        var itemstack = ItemStack.EMPTY;
        var slot = this.slots.get(index);

        if (slot.hasStack()) {
            var itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index > 3) {
                if (itemstack1.getItem() instanceof MachineUpgradeItem) {
                    if (!this.insertItem(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack1.getItem() == Items.SOUL_JAR) {
                    if (!this.insertItem(itemstack1, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (SoulExtractorBlockEntity.getFuelTime(itemstack1) > 0) {
                    if (!this.insertItem(itemstack1, 2, 3, false)) {
                        if (RecipeIngredientCache.INSTANCE.isValidInput(itemstack1, RecipeTypes.SOUL_EXTRACTION)) {
                            if (!this.insertItem(itemstack1, 1, 2, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else {
                            return ItemStack.EMPTY;
                        }
                    }
                } else if (RecipeIngredientCache.INSTANCE.isValidInput(itemstack1, RecipeTypes.SOUL_EXTRACTION)) {
                    if (!this.insertItem(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 31) {
                    if (!this.insertItem(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 39 && !this.insertItem(itemstack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemstack1, 4, 40, false)) {
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

    public static SoulExtractorScreenHandler create(int windowId, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        return new SoulExtractorScreenHandler(ScreenHandlerTypes.SOUL_EXTRACTOR, windowId, playerInventory, packetByteBuf);
    }

    public static SoulExtractorScreenHandler create(int windowId, PlayerInventory playerInventory, BaseItemStackHandler inventory, UpgradeItemStackHandler upgradeInventory, BlockPos pos) {
        return new SoulExtractorScreenHandler(ScreenHandlerTypes.SOUL_EXTRACTOR, windowId, playerInventory, inventory, upgradeInventory, pos);
    }
}
