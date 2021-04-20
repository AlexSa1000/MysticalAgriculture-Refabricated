package com.alex.mysticalagriculture.container;

import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.mysticalagriculture.crafting.recipe.ReprocessorRecipe;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.init.ScreenHandlerTypes;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import java.util.function.Function;

public class ReprocessorContainer extends ScreenHandler {
    private final Function<PlayerEntity, Boolean> isUsableByPlayer;
    private final PropertyDelegate data;
    protected final World world;
    private final RecipeType<? extends ReprocessorRecipe> recipeType;


    private ReprocessorContainer(ScreenHandlerType<?> type, RecipeType<? extends ReprocessorRecipe> recipeType, int id, PlayerInventory playerInventory) {
        this(type, recipeType, id, playerInventory, p -> false, (new ReprocessorBlockEntity.Basic()).toInventory(), new ArrayPropertyDelegate(6));
    }

    private ReprocessorContainer(ScreenHandlerType<?> type, RecipeType<? extends ReprocessorRecipe> recipeType, int id, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> isUsableByPlayer, Inventory inventory, PropertyDelegate data) {
        super(type, id);
        this.recipeType = recipeType;
        this.isUsableByPlayer = isUsableByPlayer;
        this.data = data;
        this.world = playerInventory.player.world;

        this.addSlot(new Slot(inventory, 0, 74, 42));
        this.addSlot(new Slot(inventory, 1, 36, 50));
        this.addSlot(new Slot(inventory, 2, 134, 42) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 101 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 159));
        }

        this.addProperties(data);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.isUsableByPlayer.apply(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2) {
                if (!this.insertItem(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onStackChanged(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                if (this.isSmeltable(itemstack1)) {
                    if (!this.insertItem(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    if (!this.insertItem(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 30) {
                    if (!this.insertItem(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 39 && !this.insertItem(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemstack1, 3, 39, false)) {
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

    public boolean isSmeltable(ItemStack itemStack) {
        return this.world.getRecipeManager().getFirstMatch(this.recipeType, new SimpleInventory(itemStack), this.world).isPresent();
    }

    public boolean isFuel(ItemStack itemStack) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(itemStack);
    }

    public static ReprocessorContainer create(int windowId, PlayerInventory playerInventory) {
        return new ReprocessorContainer(ScreenHandlerTypes.REPROCESSOR, RecipeTypes.REPROCESSOR, windowId, playerInventory);
    }

    public static ReprocessorContainer create(int windowId, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> isUsableByPlayer, Inventory inventory, PropertyDelegate data) {
        return new ReprocessorContainer(ScreenHandlerTypes.REPROCESSOR, RecipeTypes.REPROCESSOR, windowId, playerInventory, isUsableByPlayer, inventory, data);
    }

    public int getCookProgressScaled(int pixels) {
        int i = this.getProgress();
        int j = this.getOperationTime();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    public int getFuelBarScaled(int pixels) {
        int i = this.getFuel();
        int j = this.getFuelCapacity();
        return (int) (j != 0 && i != 0 ? (long) i * pixels / j : 0);
    }

    public int getBurnLeftScaled(int pixels) {
        int i = this.getFuelLeft();
        int j = this.getFuelItemValue();
        return (int) (j != 0 && i != 0 ? (long) i * pixels / j : 0);
    }

    public boolean isFuelItemValuable() {
        return this.data.get(3) > 0;
    }

    public boolean isProgressing() {
        return this.data.get(0) > 0;
    }

    public boolean hasFuel() {
        return this.data.get(2) > 0;
    }

    public int getProgress() {
        return this.data.get(0);
    }

    public int getFuel() {
        return this.data.get(1);
    }

    public int getFuelLeft() {
        return this.data.get(2);
    }

    public int getFuelItemValue() {
        return this.data.get(3);
    }

    public int getOperationTime() {
        return this.data.get(4);
    }

    public int getFuelCapacity() {
        return this.data.get(5);
    }

}
