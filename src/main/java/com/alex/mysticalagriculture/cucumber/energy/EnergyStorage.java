package com.alex.mysticalagriculture.cucumber.energy;

import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;

public class EnergyStorage extends SnapshotParticipant<Long> implements team.reborn.energy.api.EnergyStorage {
    protected long energy;
    protected long capacity;
    protected long maxReceive;
    protected long maxExtract;

    public EnergyStorage(long capacity)
    {
        this(capacity, capacity, capacity, 0);
    }

    public EnergyStorage(long capacity, long maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyStorage(long capacity, long maxReceive, long maxExtract)
    {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public EnergyStorage(long capacity, long maxReceive, long maxExtract, long energy) {
        StoragePreconditions.notNegative(capacity);
        StoragePreconditions.notNegative(maxReceive);
        StoragePreconditions.notNegative(maxExtract);

        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = Math.max(0 , Math.min(capacity, energy));
    }

    @Override
    public long insert(long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notNegative(maxAmount);

        if (!supportsInsertion())
            return 0;

        long energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        updateSnapshots(transaction);
        energy += energyReceived;
        return energyReceived;
    }

    @Override
    public long extract(long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notNegative(maxAmount);

        if (!supportsExtraction())
            return 0;

        long energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        updateSnapshots(transaction);
        energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public long getAmount() {
        return energy;
    }

    @Override
    public long getCapacity() {
        return capacity;
    }

    @Override
    public boolean supportsExtraction() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean supportsInsertion() {
        return this.maxReceive > 0;
    }

    @Override
    protected Long createSnapshot() {
        return energy;
    }

    @Override
    protected void readSnapshot(Long snapshot) {
        energy = snapshot;
    }
}
