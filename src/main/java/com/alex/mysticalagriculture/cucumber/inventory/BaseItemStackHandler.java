package com.alex.mysticalagriculture.cucumber.inventory;

import com.alex.mysticalagriculture.forge.items.ItemStackHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaseItemStackHandler extends ItemStackHandler {
    private final Runnable onContentsChanged;
    @Nullable
    private List<InventoryChangedListener> listeners;
    private final Map<Integer, Integer> slotSizeMap;
    private BiFunction<Integer, ItemStack, Boolean> canInsert = null;
    private Function<Integer, Boolean> canExtract = null;
    private int maxStackSize = 64;
    protected int[] outputSlots = null;
    private final int[] availableSlots;

    protected BaseItemStackHandler(int size, Runnable onContentsChanged) {
        super(size);
        this.onContentsChanged = onContentsChanged;
        this.slotSizeMap = new HashMap();

        availableSlots = new int[size()];
        for (int i = 0; i < size(); i++) {
            availableSlots[i] = i;
        }
    }

    public ItemStack insertItem(int slot, ItemStack stack) {
        return this.insertItem(slot, stack, false);
    }

    public ItemStack insertItem(int slot, ItemStack stack, boolean container) {
        return !container && this.outputSlots != null && ArrayUtils.contains(this.outputSlots, slot) ? stack : super.insertItem(slot, stack);
    }

    public ItemStack extractItem(int slot, int amount) {
        return this.extractItem(slot, amount, false);
    }

    public ItemStack extractItem(int slot, int amount, boolean container) {
        if (!container) {
            if (this.canExtract != null && !(Boolean)this.canExtract.apply(slot)) {
                return ItemStack.EMPTY;
            }

            if (this.outputSlots != null && !ArrayUtils.contains(this.outputSlots, slot)) {
                return ItemStack.EMPTY;
            }
        }

        return super.removeStack(slot, amount);
    }

    public int getSlotLimit(int slot) {
        return this.slotSizeMap.containsKey(slot) ? this.slotSizeMap.get(slot) : this.maxStackSize;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return this.canInsert == null || this.canInsert.apply(slot, stack);
    }

    @Override
    public void clear() {
        this.stacks.clear();
        this.markDirty();
    }

    @Override
    public void markDirty() {
        if (this.listeners != null) {
            for (InventoryChangedListener inventoryChangedListener : this.listeners) {
                inventoryChangedListener.onInventoryChanged(this);
            }
        }
        if (this.onContentsChanged != null) {
            this.onContentsChanged.run();
        }
    }

    public DefaultedList<ItemStack> getStacks() {
        return this.stacks;
    }

    public int[] getOutputSlots() {
        return this.outputSlots;
    }

    public void setDefaultSlotLimit(int size) {
        this.maxStackSize = size;
    }

    /*public void addSlotLimit(int slot, int size) {
        this.slotSizeMap.put(slot, size);
    }*/

    public void setCanInsert(BiFunction<Integer, ItemStack, Boolean> validator) {
        this.canInsert = validator;
    }

    public void setCanExtract(Function<Integer, Boolean> canExtract) {
        this.canExtract = canExtract;
    }

    public void setOutputSlots(int... slots) {
        this.outputSlots = slots;
    }

    public Inventory toInventory() {
        return new SimpleInventory((ItemStack[])this.stacks.toArray(new ItemStack[0]));
    }

    public BaseItemStackHandler copy() {
        var newInventory = new BaseItemStackHandler(this.size(), this.onContentsChanged);

        newInventory.setDefaultSlotLimit(this.maxStackSize);
        newInventory.setCanInsert(this.canInsert);
        newInventory.setCanExtract(this.canExtract);
        newInventory.setOutputSlots(this.outputSlots);

        //this.slotSizeMap.forEach(newInventory::addSlotLimit);

        for (int i = 0; i < this.size(); i++) {
            var stack = this.getStack(i);

            newInventory.setStackInSlot(i, stack.copy());
        }

        return newInventory;
    }

    public static BaseItemStackHandler create(int size) {
        return create(size, (builder) -> {
        });
    }

    public static BaseItemStackHandler create(int size, Runnable onContentsChanged) {
        return create(size, onContentsChanged, (builder) -> {
        });
    }

    public static BaseItemStackHandler create(int size, Consumer<BaseItemStackHandler> builder) {
        return create(size, null, builder);
    }

    public static BaseItemStackHandler create(int size, Runnable onContentsChanged, Consumer<BaseItemStackHandler> builder) {
        BaseItemStackHandler handler = new BaseItemStackHandler(size, onContentsChanged);
        builder.accept(handler);
        return handler;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return availableSlots;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return (canExtract == null || canExtract.apply(slot)) && (outputSlots == null || ArrayUtils.contains(outputSlots, slot));
    }

    public Runnable getOnContentsChanged() {
        return onContentsChanged;
    }
}
