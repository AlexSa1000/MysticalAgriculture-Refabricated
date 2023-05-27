package com.alex.mysticalagriculture.forge.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

public abstract class ItemStackHandler implements SidedInventory {

    protected DefaultedList<ItemStack> stacks;
    protected boolean simulate;

    public ItemStackHandler()
    {
        this(1);
    }

    public ItemStackHandler(int size)
    {
        stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
        this.simulate = false;
    }

    public ItemStackHandler(DefaultedList<ItemStack> stacks)
    {
        this.stacks = stacks;
    }

    public void setSize(int size)
    {
        stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
    }

    public void setStackInSlot(int slot, @NotNull ItemStack stack)
    {
        validateSlotIndex(slot);
        this.stacks.set(slot, stack);
        markDirty();
    }

    public void setSimulate(boolean simulate) {
        this.simulate = simulate;
    }

    @Override
    public int size() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.stacks) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        validateSlotIndex(slot);
        return this.stacks.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (amount == 0)
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxCount());

        if (existing.getCount() <= toExtract)
        {
            if (!this.simulate)
            {
                this.stacks.set(slot, ItemStack.EMPTY);
                markDirty();
                return existing;
            }
            else
            {
                this.simulate = false;
                return existing.copy();
            }
        }
        else
        {
            if (!this.simulate) {
                this.stacks.set(slot, existing.copyWithCount(existing.getCount() - toExtract));
                markDirty();
            }
            this.simulate = false;
            return existing.copyWithCount(toExtract);
        }
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        validateSlotIndex(slot);
        this.stacks.set(slot, stack);
        markDirty();
    }

    @NotNull
    public ItemStack insertItem(int slot, @NotNull ItemStack stack) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isValid(slot, stack))
            return stack;

        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty())
        {
            if (!ItemStack.canCombine(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!this.simulate) {
            if (existing.isEmpty())
            {
                this.setStack(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
            }
            else
            {
                existing.increment(reachedLimit ? limit : stack.getCount());
            }
            markDirty();
        }

        this.simulate = false;
        return reachedLimit ? stack.copyWithCount(stack.getCount()- limit) : ItemStack.EMPTY;
    }

    public int getSlotLimit(int slot)
    {
        return 64;
    }

    protected int getStackLimit(int slot, @NotNull ItemStack stack)
    {
        return Math.min(getSlotLimit(slot), stack.getMaxCount());
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }

    public NbtCompound serializeNBT() {
        NbtList nbtTagList = new NbtList();
        for (int i = 0; i < stacks.size(); i++)
        {
            if (!stacks.get(i).isEmpty())
            {
                NbtCompound itemTag = new NbtCompound();
                itemTag.putInt("Slot", i);
                stacks.get(i).writeNbt(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        NbtCompound nbt = new NbtCompound();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", stacks.size());
        return nbt;
    }

    public void deserializeNBT(NbtCompound nbt)
    {
        setSize(nbt.contains("Size", NbtElement.INT_TYPE) ? nbt.getInt("Size") : stacks.size());
        NbtList tagList = nbt.getList("Items", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < tagList.size(); i++)
        {
            NbtCompound itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < stacks.size())
            {
                stacks.set(slot, ItemStack.fromNbt(itemTags));
            }
        }
    }

    protected void validateSlotIndex(int slot)
    {
        if (slot < 0 || slot >= stacks.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
    }
}
