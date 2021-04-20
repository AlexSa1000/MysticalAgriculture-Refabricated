package com.alex.mysticalagriculture.util.blockentity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class BaseInventoryBlockEntity extends BlockEntity implements SidedInventory, BlockEntityClientSerializable {
    private final int size;
    private final DefaultedList<ItemStack> stacks;


    public BaseInventoryBlockEntity(BlockEntityType<?> type, int size) {
        super(type);
        this.size = size;
        this.stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[stacks.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if(!world.isClient) {
            sync();
        }
    }

    @Override
    public boolean isEmpty() {
        Iterator<ItemStack> var1 = this.stacks.iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = var1.next();
        } while(itemStack.isEmpty());

        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot >= 0 && slot < this.stacks.size() ? this.stacks.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(stacks, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(stacks, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }

        this.markDirty();
    }


    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.stacks.clear();
        this.markDirty();
    }

    public DefaultedList<ItemStack> getStacks() {
        return stacks;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, stacks);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, stacks);

        return tag;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        stacks.clear();
        Inventories.fromTag(tag, stacks);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        Inventories.toTag(tag, stacks);
        return super.toTag(tag);
    }

    public Inventory toInventory() {
        return new SimpleInventory(this.stacks.toArray(new ItemStack[0]));
    }

    public boolean isUsableByPlayer(PlayerEntity player) {
        BlockPos pos = this.getPos();
        return player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

}
