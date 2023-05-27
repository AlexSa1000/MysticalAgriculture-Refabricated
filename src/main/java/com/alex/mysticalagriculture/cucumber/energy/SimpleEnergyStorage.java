package com.alex.mysticalagriculture.cucumber.energy;

import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import team.reborn.energy.api.EnergyStorage;

public class SimpleEnergyStorage extends SnapshotParticipant<Long> implements EnergyStorage {
	public long amount;
	public long capacity;
	public long maxInsert;
	public long maxExtract;

	public SimpleEnergyStorage(long capacity, long maxInsert, long maxExtract) {
		StoragePreconditions.notNegative(capacity);
		StoragePreconditions.notNegative(maxInsert);
		StoragePreconditions.notNegative(maxExtract);

		this.capacity = capacity;
		this.maxInsert = maxInsert;
		this.maxExtract = maxExtract;
	}

	public SimpleEnergyStorage(long capacity, long maxInsert, long maxExtract, long amount) {
		StoragePreconditions.notNegative(capacity);
		StoragePreconditions.notNegative(maxInsert);
		StoragePreconditions.notNegative(maxExtract);

		this.capacity = capacity;
		this.maxInsert = maxInsert;
		this.maxExtract = maxExtract;
		//this.amount = Math.max(0 , Math.min(capacity, amount));
	}

	@Override
	public long insert(long maxAmount, TransactionContext transaction) {
		StoragePreconditions.notNegative(maxAmount);

		//if (!supportsInsertion())
		//	return 0;

		/*long energyReceived = Math.min(capacity - amount, Math.min(this.maxInsert, maxAmount));
		updateSnapshots(transaction);
		amount += energyReceived;
		return energyReceived;*/

		long inserted = Math.min(maxInsert, Math.min(maxAmount, capacity - amount));

		if (inserted > 0) {
			updateSnapshots(transaction);
			amount += inserted;
			return inserted;
		}

		return 0;
	}

	@Override
	public long extract(long maxAmount, TransactionContext transaction) {
		StoragePreconditions.notNegative(maxAmount);

		//if (!supportsExtraction())
		//	return 0;

		/*long energyExtracted = Math.min(amount, Math.min(this.maxExtract, maxAmount));
		updateSnapshots(transaction);
		amount -= energyExtracted;
		return energyExtracted;*/

		long extracted = Math.min(maxExtract, Math.min(maxAmount, amount));

		if (extracted > 0) {
			updateSnapshots(transaction);
			amount -= extracted;
			return extracted;
		}

		return 0;
	}

	@Override
	public long getAmount() {
		return amount;
	}

	@Override
	public long getCapacity() {
		return capacity;
	}

	@Override
	public boolean supportsInsertion() {
		return maxInsert > 0;
		//return false;
	}

	@Override
	public boolean supportsExtraction() {
		//return maxExtract > 0;
		return false;
	}

	@Override
	protected Long createSnapshot() {
		return amount;
	}

	@Override
	protected void readSnapshot(Long snapshot) {
		amount = snapshot;
	}
}