package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.crafting.recipe.SoulExtractionRecipe;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.items.SoulJarItem;
import com.alex.mysticalagriculture.screenhandler.SoulExtractorScreenHandler;
import com.alex.mysticalagriculture.screenhandler.inventory.UpgradeItemStackHandler;
import com.alex.mysticalagriculture.util.MachineUpgradeTier;
import com.alex.mysticalagriculture.util.RecipeIngredientCache;
import com.alex.mysticalagriculture.util.UpgradeableMachine;
import com.alex.mysticalagriculture.zucchini.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.zucchini.energy.BaseEnergyStorage;
import com.alex.mysticalagriculture.zucchini.energy.DynamicEnergyStorage;
import com.alex.mysticalagriculture.zucchini.helper.StackHelper;
import com.alex.mysticalagriculture.zucchini.util.Localizable;
import com.alex.mysticalagriculture.zzz.BaseItemStackHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SoulExtractorBlockEntity extends BaseInventoryBlockEntity implements ExtendedScreenHandlerFactory, UpgradeableMachine {
    private static final int FUEL_TICK_MULTIPLIER = 20;
    public static final int OPERATION_TIME = 100;
    public static final int FUEL_USAGE = 40;
    public static final int FUEL_CAPACITY = 80000;

    private final BaseItemStackHandler inventory;
    private final UpgradeItemStackHandler upgradeInventory;
    private final DynamicEnergyStorage energy;
    private int progress;
    private int fuelLeft;
    private int fuelItemValue;
    private SoulExtractionRecipe recipe;
    private MachineUpgradeTier tier;

    public SoulExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.SOUL_EXTRACTOR, pos, state);
        //this.inventory = createInventoryHandler(this::markDirty);
        this.inventory = new BaseItemStackHandler(3, this::markDirty){
            @Override
            public int[] getAvailableSlots(Direction side) {
                if (side == Direction.UP) {
                    return new int[]{0};
                } else if (side == Direction.DOWN) {
                    return new int[]{2};
                } else {
                    return new int[]{1, 2};
                }
            }

            @Override
            public boolean canInsert(int slot, ItemStack stack, @Nullable Direction direction) {
                if (direction == null)
                    return true;
                if (slot == 0 && direction == Direction.UP)
                    return RecipeIngredientCache.INSTANCE.isValidInput(stack, RecipeTypes.SOUL_EXTRACTION);
                if (slot == 1 && direction == Direction.NORTH)
                    return AbstractFurnaceBlockEntity.canUseAsFuel(stack);
                if (slot == 2 && direction == Direction.NORTH)
                    return stack.getItem() instanceof SoulJarItem;

                return false;
            }

            @Override
            public boolean canExtract(int slot, ItemStack stack, Direction direction) {
                if (slot == 2 && direction == Direction.DOWN) {
                    //var stack = this.inventory.getStack(2);
                    return stack.getItem() instanceof SoulJarItem && MobSoulUtils.isJarFull(stack);
                }

                return false;
            }
        };
        this.upgradeInventory = new UpgradeItemStackHandler();
        this.energy = new DynamicEnergyStorage(FUEL_CAPACITY, this::markDirty);
    }

    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.progress = nbt.getInt("Progress");
        this.fuelLeft = nbt.getInt("FuelLeft");
        this.fuelItemValue = nbt.getInt("FuelItemValue");
        this.energy.deserializeNBT(nbt.get("Energy"));
        this.upgradeInventory.deserializeNBT(nbt.getCompound("UpgradeInventory"));
        Inventories.readNbt(nbt.getCompound("UpgradeInventory"), this.upgradeInventory.getStacks());
        //Inventories.readNbt(nbt, this.upgradeInventory.getStacks());

    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putInt("Progress", this.progress);
        nbt.putInt("FuelLeft", this.fuelLeft);
        nbt.putInt("FuelItemValue", this.fuelItemValue);
        nbt.put("Energy", this.energy.serializeNBT());
        //nbt.put("UpgradeInventory", this.upgradeInventory.serializeNBT());
        NbtCompound nbtCompound = new NbtCompound();
        nbt.put("UpgradeInventory", Inventories.writeNbt(nbtCompound, this.upgradeInventory.getStacks()));
        //Inventories.writeNbt(nbt.getCompound("UpgradeInventory"), this.upgradeInventory.getStacks());
    }

    @Override
    public Text getDisplayName() {
        return Localizable.of("container.mysticalagriculture.soul_extractor").build();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return SoulExtractorScreenHandler.create(syncId, playerInventory, this.inventory, this.upgradeInventory, this.getPos());
    }

    @Override
    public UpgradeItemStackHandler getUpgradeInventory() {
        return this.upgradeInventory;
    }

    public static void tick(World world, BlockPos pos, BlockState state, SoulExtractorBlockEntity block) {
        var mark = false;

        if (block.energy.getAmount() < block.energy.getCapacity()) {
            var fuel = block.inventory.getStack(1);

            if (block.fuelLeft <= 0 && !fuel.isEmpty()) {
                block.fuelItemValue = getFuelTime(fuel);

                if (block.fuelItemValue > 0) {
                    block.fuelLeft = block.fuelItemValue *= FUEL_TICK_MULTIPLIER;
                    block.inventory.setStackInSlot(1, StackHelper.shrink(block.inventory.getStack(1), 1, false));

                    mark = true;
                }
            }

            if (block.fuelLeft > 0) {
                var fuelPerTick = Math.min(Math.min(block.fuelLeft, block.getFuelUsage() * 2), block.energy.getCapacity() - block.energy.getAmount());

                try (Transaction transaction = Transaction.openOuter()) {
                    block.fuelLeft -= block.energy.insert(fuelPerTick, transaction);
                    transaction.commit();
                }
                if (block.fuelLeft <= 0)
                    block.fuelItemValue = 0;

                mark = true;
            }
        }

        if (block.recipe == null || !block.recipe.matches(block.inventory)) {
            var recipe = world.getRecipeManager().getFirstMatch(RecipeTypes.SOUL_EXTRACTION, block.inventory/*.toInventory()*/, world).orElse(null);
            block.recipe = recipe instanceof SoulExtractionRecipe ? (SoulExtractionRecipe) recipe : null;
        }

        var tier = block.getMachineTier();

        if (tier != block.tier) {
            block.tier = tier;

            if (tier == null) {
                block.energy.resetMaxEnergyStorage();
            } else {
                block.energy.setMaxEnergyStorage((int) (FUEL_CAPACITY * tier.getFuelCapacityMultiplier()));
            }

            mark = true;
        }

        if (block.recipe != null) {
            if (block.energy.getAmount() >= block.getFuelUsage()) {
                block.progress++;
                try (Transaction transaction = Transaction.openOuter()) {
                    block.energy.extract(block.getFuelUsage(), transaction);
                    transaction.commit();
                }
                if (block.progress >= block.getOperationTime()) {
                    block.inventory.setStackInSlot(0, StackHelper.shrink(block.inventory.getStack(0), 1, false));
                    block.inventory.setStackInSlot(2, block.recipe.craft(block.inventory, world.getRegistryManager()));

                    block.progress = 0;
                }

                mark = true;
            }
        } else {
            if (block.progress > 0) {
                block.progress = 0;

                mark = true;
            }
        }

        if (mark) {
            block.markDirty();
        }
    }

    public static int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();
            return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
        }
    }

    public static BaseItemStackHandler createInventoryHandler() {
        return createInventoryHandler(null);
    }

    public static BaseItemStackHandler createInventoryHandler(Runnable onContentsChanged) {
        return BaseItemStackHandler.create(3, onContentsChanged);
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

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }
}
