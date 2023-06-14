package com.alex.mysticalagriculture.blockentities;

import com.alex.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.cucumber.energy.DynamicEnergyStorage;
import com.alex.cucumber.helper.StackHelper;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.cucumber.mixin.CropBlockInvoker;
import com.alex.cucumber.util.Localizable;
import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.blocks.HarvesterBlock;
import com.alex.mysticalagriculture.container.HarvesterContainer;
import com.alex.mysticalagriculture.container.inventory.UpgradeItemStackHandler;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import com.alex.mysticalagriculture.screenhandler.inventory.UpgradeItemStackHandler;
import com.alex.mysticalagriculture.util.MachineUpgradeTier;
import com.alex.mysticalagriculture.util.IUpgradeableMachine;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class HarvesterBlockEntity extends BaseInventoryBlockEntity implements ExtendedScreenHandlerFactory, IUpgradeableMachine {
    private static final int FUEL_TICK_MULTIPLIER = 20;
    public static final int OPERATION_TIME = 100;
    public static final int FUEL_USAGE = 40;
    public static final int SCAN_FUEL_USAGE = 10;
    public static final int FUEL_CAPACITY = 80000;
    public static final int BASE_RANGE = 1;

    private final BaseItemStackHandler inventory;
    private final UpgradeItemStackHandler upgradeInventory;
    private final DynamicEnergyStorage energy;
    private int progress;
    private int fuelLeft;
    private int fuelItemValue;
    private long oldEnergy;
    private List<BlockPos> positions;
    private BlockPos lastPosition = BlockPos.ZERO;
    private MachineUpgradeTier tier;
    private Direction direction;

    public HarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HARVESTER, pos, state);
        this.inventory = createInventoryHandler(this::markDirtyAndDispatch);
        this.upgradeInventory = new UpgradeItemStackHandler();
        this.energy = new DynamicEnergyStorage(FUEL_CAPACITY, this::markDirtyAndDispatch);
    }

    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public Component getDisplayName() {
        return Localizable.of("container.mysticalagriculture.harvester").build();
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return HarvesterContainer.create(i, inventory, this.inventory, this.upgradeInventory, this.getBlockPos());
    }

    @Override
    public UpgradeItemStackHandler getUpgradeInventory() {
        return this.upgradeInventory;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        this.progress = tag.getInt("Progress");
        this.fuelLeft = tag.getInt("FuelLeft");
        this.fuelItemValue = tag.getInt("FuelItemValue");
        this.energy.deserializeNBT(tag.get("Energy"));
        this.lastPosition = BlockPos.of(tag.getLong("LastPosition"));
        this.upgradeInventory.deserializeNBT(tag.getCompound("UpgradeInventory"));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putInt("Progress", this.progress);
        tag.putInt("FuelLeft", this.fuelLeft);
        tag.putInt("FuelItemValue", this.fuelItemValue);
        tag.put("Energy", this.energy.serializeNBT());
        tag.putLong("LastPosition", this.lastPosition.asLong());
        tag.put("UpgradeInventory", this.upgradeInventory.serializeNBT());
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HarvesterBlockEntity blockEntity) {
        var mark = false;

        if (blockEntity.energy.getAmount() < blockEntity.energy.getCapacity()) {
            var fuel = blockEntity.inventory.getItem(0);

            if (blockEntity.fuelLeft <= 0 && !fuel.isEmpty()) {
                blockEntity.fuelItemValue = getFuelTime(fuel);

                if (blockEntity.fuelItemValue > 0) {
                    blockEntity.fuelLeft = blockEntity.fuelItemValue *= FUEL_TICK_MULTIPLIER;
                    blockEntity.inventory.setStackInSlot(0, StackHelper.shrink(blockEntity.inventory.getItem(0), 1, false));

                    mark = true;
                }
            }

            if (blockEntity.fuelLeft > 0) {
                var fuelPerTick = Math.min(Math.min(blockEntity.fuelLeft, blockEntity.getFuelUsage() * 2), blockEntity.energy.getCapacity() - blockEntity.energy.getAmount());

                try (Transaction transaction = Transaction.openOuter()) {
                    blockEntity.fuelLeft -= blockEntity.energy.insert(fuelPerTick, transaction);
                    transaction.commit();
                }
                if (blockEntity.fuelLeft <= 0)
                    blockEntity.fuelItemValue = 0;

                mark = true;
            }
        }

        var tier = blockEntity.getMachineTier();
        Direction direction = state.getValue(HarvesterBlock.FACING);

        if (tier != blockEntity.tier || direction != blockEntity.direction) {
            var range = tier != null ? BASE_RANGE + tier.getAddedRange() : BASE_RANGE;
            var center = pos.relative(direction, range + 1);

            blockEntity.tier = tier;
            blockEntity.direction = direction;
            blockEntity.positions = getWorkingArea(center, range);

            if (tier == null) {
                blockEntity.energy.resetMaxEnergyStorage();
            } else {
                blockEntity.energy.setMaxEnergyStorage((int) (FUEL_CAPACITY * tier.getFuelCapacityMultiplier()));
            }

            mark = true;
        }

        var operationTime = blockEntity.getOperationTime();

        if (blockEntity.progress > operationTime && !level.hasNeighborSignal(blockEntity.getBlockPos())) {
            var nextPos = blockEntity.findNextPosition();
            var cropState = level.getBlockState(nextPos);
            var block = cropState.getBlock();

            if (block instanceof CropBlock crop) {
                var seed = getSeed(block);
                if (seed != null && crop.isMaxAge(cropState)) {
                    var drops = Block.getDrops(cropState, (ServerLevel) level, nextPos, blockEntity);

                    for (var drop : drops) {
                        var item = drop.getItem();

                        if (!drop.isEmpty() && item == seed) {
                            drop.shrink(1);
                            break;
                        }
                    }

                    for (var drop : drops) {
                        if (!drop.isEmpty()) {
                            blockEntity.addItemToInventory(drop, level, nextPos);
                        }
                    }

                    level.setBlockAndUpdate(nextPos, crop.getStateForAge(0));

                    try (Transaction transaction = Transaction.openOuter()) {
                        blockEntity.energy.extract(blockEntity.getFuelUsage(), transaction);
                        transaction.commit();
                    }
                } else {
                    try (Transaction transaction = Transaction.openOuter()) {
                        blockEntity.energy.extract(SCAN_FUEL_USAGE, transaction);
                        transaction.commit();
                    }
                }
            }

            blockEntity.progress = 0;

            mark = true;
        }

        if (blockEntity.energy.getAmount() >= blockEntity.getFuelUsage()) {
            blockEntity.progress++;

            mark = true;
        }

        if (blockEntity.oldEnergy != blockEntity.energy.getAmount()) {
            blockEntity.oldEnergy = blockEntity.energy.getAmount();

            mark = true;
        }

        if (mark) {
            blockEntity.markDirtyAndDispatch();
        }
    }

    public static int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();
            return AbstractFurnaceBlockEntity.getFuel().getOrDefault(item, 0);
        }
    }

    public static BaseItemStackHandler createInventoryHandler() {
        return createInventoryHandler(null);
    }

    public static BaseItemStackHandler createInventoryHandler(Runnable onContentsChanged) {
        return BaseItemStackHandler.create(16, onContentsChanged, builder -> {
            builder.setCanInsert((slot, stack) -> slot == 0 && getFuelTime(stack) > 0);
            builder.setCanExtract(slot -> slot > 0);
        });
    }

    public DynamicEnergyStorage getEnergy() {
        return this.energy;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getOperationTime() {
        if (this.tier == null)
            return OPERATION_TIME;

        return (int) (OPERATION_TIME * this.tier.getOperationTimeMultiplier());
    }

    public int getFuelLeft() {
        return this.fuelLeft;
    }

    public int getFuelItemValue() {
        return this.fuelItemValue;
    }

    public int getFuelUsage() {
        if (this.tier == null)
            return FUEL_USAGE;

        return (int) (FUEL_USAGE * this.tier.getFuelUsageMultiplier());
    }

    private static List<BlockPos> getWorkingArea(BlockPos center, int range) {
        var positions = new ArrayList<BlockPos>();

        for (int x = -range; x < range + 1; x++) {
            for (int z = -range; z < range + 1; z++) {
                positions.add(new BlockPos(center.getX() + x, center.getY(), center.getZ() + z));
            }
        }

        return positions;
    }

    private BlockPos findNextPosition() {
        if (this.lastPosition == null) {
            this.lastPosition = this.positions.get(0);
            return this.lastPosition;
        }

        var index = this.positions.indexOf(this.lastPosition);
        if (index == -1 || index >= this.positions.size() - 1) {
            this.lastPosition = this.positions.get(0);
            return this.lastPosition;
        }

        this.lastPosition = this.positions.get(index + 1);

        return this.lastPosition;
    }

    private void addItemToInventory(ItemStack stack, Level level, BlockPos pos) {
        var remaining = stack.getCount();
        for (int i = 1; i < this.inventory.getContainerSize(); i++) {
            var stackInSlot = this.inventory.getItem(i);

            if (stackInSlot.isEmpty()) {
                this.inventory.setStackInSlot(i, stack.copy());
                return;
            }

            var insertSize = Math.min(remaining, stackInSlot.getMaxStackSize() - stackInSlot.getCount());

            remaining -= insertSize;

            if (StackHelper.areStacksEqual(stackInSlot, stack)) {
                this.inventory.setStackInSlot(i, StackHelper.grow(stackInSlot, insertSize));
            }

            if (remaining == 0)
                return;
        }

        Block.popResource(level, pos, StackHelper.withSize(stack, remaining, false));
    }

    private static Item getSeed(Block block) {
        try {
            return (Item) ((CropBlockInvoker) block).invokerGetSeedsItem();
        } catch (Exception e) {
            MysticalAgriculture.LOGGER.error("Unable to get seed from crop {}", e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeBlockPos(worldPosition);
    }
}