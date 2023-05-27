package com.alex.mysticalagriculture.cucumber.energy;

import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtLong;

public class BaseEnergyStorage extends SimpleEnergyStorage {
    private final Runnable onContentsChanged;

    public BaseEnergyStorage(long capacity, Runnable onContentsChanged) {
        this(capacity, capacity, capacity, onContentsChanged);
    }

    public BaseEnergyStorage(long capacity, long maxInsert, long maxExtract, Runnable onContentsChanged) {
        this(capacity, maxInsert, maxExtract, 0, onContentsChanged);
    }

    public BaseEnergyStorage(long capacity, long maxInsert, long maxExtract, long energy, Runnable onContentsChanged) {
        super(capacity, maxInsert, maxExtract);
        this.amount = Math.max(0 , Math.min(capacity, energy));
        this.onContentsChanged = onContentsChanged;
    }

    /*public BaseEnergyStorage(long capacity, long maxReceive, long maxExtract, long energy, Runnable onContentsChanged) {
        super(capacity, maxReceive, maxExtract, energy);
        this.onContentsChanged = onContentsChanged;
    }*/

    @Override
    public long insert(long maxAmount, TransactionContext transaction) {
        long received = super.insert(maxAmount, transaction);
        if (received != 0 && this.onContentsChanged != null) {
            this.onContentsChanged.run();
        }
        return received;
    }

    @Override
    public long extract(long maxAmount, TransactionContext transaction) {
        long extracted = super.extract(maxAmount, transaction);
        if (extracted != 0 && this.onContentsChanged != null) {
            this.onContentsChanged.run();
        }
        return extracted;
    }

    public void setEnergyStored(int energy) {
        this.amount = energy;
        if (this.onContentsChanged != null) {
            this.onContentsChanged.run();
        }
    }

    public NbtElement serializeNBT()
    {
        return NbtLong.of(this.getAmount());
    }

    public void deserializeNBT(NbtElement nbt)
    {
        if (!(nbt instanceof NbtLong longNbt))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        this.amount = longNbt.longValue();
    }
}
