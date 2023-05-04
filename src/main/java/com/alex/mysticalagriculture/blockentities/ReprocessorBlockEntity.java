package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.crafting.recipe.ReprocessorRecipe;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.screenhandler.ReprocessorScreenHandler;
import com.alex.mysticalagriculture.util.ReprocessorTier;
import com.alex.mysticalagriculture.zucchini.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.zucchini.energy.BaseEnergyStorage;
import com.alex.mysticalagriculture.zucchini.helper.StackHelper;
import com.alex.mysticalagriculture.zucchini.util.Localizable;
import com.alex.mysticalagriculture.zzz.BaseItemStackHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static com.alex.mysticalagriculture.blockentities.SoulExtractorBlockEntity.createInventoryHandler;

public abstract class ReprocessorBlockEntity extends BaseInventoryBlockEntity implements ExtendedScreenHandlerFactory {
    private static final int FUEL_TICK_MULTIPLIER = 20;
    private final BaseItemStackHandler inventory;
    private final BaseEnergyStorage energy;
    private final ReprocessorTier tier;
    private ReprocessorRecipe recipe;
    private int progress;
    private int fuelLeft;
    private int fuelItemValue;

    public ReprocessorBlockEntity(BlockEntityType<?> type, ReprocessorTier tier, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.inventory = createInventoryHandler(this::markDirty);
        this.energy = new BaseEnergyStorage(tier.getFuelCapacity(), this::markDirty);
        this.tier = tier;
    }

    @Override
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
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putInt("Progress", this.progress);
        nbt.putInt("FuelLeft", this.fuelLeft);
        nbt.putInt("FuelItemValue", this.fuelItemValue);
        nbt.put("Energy", this.energy.serializeNBT());
    }

    @Override
    public Text getDisplayName() {
        return Localizable.of("container.mysticalagriculture.reprocessor").build();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return ReprocessorScreenHandler.create(syncId, inv, this.inventory, this.getPos());
    }

    public static void tick(World world, BlockPos pos, BlockState state, ReprocessorBlockEntity block) {
        var mark = false;

        if (block.energy.getAmount() < block.energy.getCapacity()) {
            var fuel = block.inventory.getStack(1);
            
            if (block.fuelLeft <= 0 && !fuel.isEmpty()) {
                block.fuelItemValue = block.getFuelTime(fuel);

                if (block.fuelItemValue > 0) {
                    block.fuelLeft = block.fuelItemValue *= FUEL_TICK_MULTIPLIER;
                    block.inventory.setStack(1, StackHelper.shrink(block.inventory.getStack(1), 1, false));
                    
                    mark = true;
                }
            }
            
            if (block.fuelLeft > 0) {
                var fuelPerTick = Math.min(Math.min(block.fuelLeft, block.tier.getFuelUsage() * 2), block.energy.getCapacity() - block.energy.getAmount());

                try (Transaction transaction = Transaction.openOuter()) {
                    block.fuelLeft -= block.energy.insert(fuelPerTick, transaction);
                    transaction.commit();
                }

                if (block.fuelLeft <= 0)
                    block.fuelItemValue = 0;

                mark = true;
            }
        }

        if (block.energy.getAmount() >= block.tier.getFuelUsage()) {
            var input = block.inventory.getStack(0);
            var output = block.inventory.getStack(2);

            if (!input.isEmpty()) {
                if (block.recipe == null || !block.recipe.matches(block.inventory)) {
                    var recipe = world.getRecipeManager().getFirstMatch(RecipeTypes.REPROCESSOR, block.inventory, world).orElse(null);
                    block.recipe = recipe instanceof ReprocessorRecipe ? (ReprocessorRecipe) recipe : null;
                }

                if (block.recipe != null) {
                    var recipeOutput = block.recipe.craft(block.inventory, world.getRegistryManager());
                    if (!recipeOutput.isEmpty() && (output.isEmpty() || StackHelper.canCombineStacks(output, recipeOutput))) {
                        block.progress++;
                        try (Transaction transaction = Transaction.openOuter()) {
                            block.energy.extract(block.tier.getFuelUsage(), transaction);
                            transaction.commit();
                        }
                        if (block.progress >= block.tier.getOperationTime()) {
                            block.inventory.setStack(0, StackHelper.shrink(block.inventory.getStack(0), 1, false));

                            var result = StackHelper.combineStacks(output, recipeOutput);
                            block.inventory.setStack(2, result);

                            block.progress = 0;
                        }

                        mark = true;
                    }
                }
            } else {
                if (block.progress > 0) {
                    block.progress = 0;
                    block.recipe = null;
                    mark = true;
                }
            }
        }

        if (mark) {
            block.markDirty();
        }
    }

    protected int getFuelTime(ItemStack fuel) {
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
        return BaseItemStackHandler.create(3, onContentsChanged, builder -> {
            builder.setOutputSlots(2);
        });
    }

    public ReprocessorTier getTier() {
        return this.tier;
    }

    public BaseEnergyStorage getEnergy() {
        return this.energy;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getFuelLeft() {
        return this.fuelLeft;
    }

    public int getFuelItemValue() {
        return this.fuelItemValue;
    }

    /*@Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        } else if (slot != 1) {
            return true;
        } else {
            return FurnaceBlockEntity.canUseAsFuel(stack);
        }
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return new int[]{2};
        } else {
            return side == Direction.UP ? new int[]{0} : new int[]{1};
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 2;
    }*/

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static class Basic extends ReprocessorBlockEntity {
        public Basic(BlockPos pos, BlockState state) {
            super(BlockEntities.BASIC_REPROCESSOR, ReprocessorTier.BASIC, pos, state);
        }
    }
    public static class Inferium extends ReprocessorBlockEntity {
        public Inferium(BlockPos pos, BlockState state) {
            super(BlockEntities.INFERIUM_REPROCESSOR, ReprocessorTier.INFERIUM, pos, state);
        }
    }
    public static class Prudentium extends ReprocessorBlockEntity {
        public Prudentium(BlockPos pos, BlockState state) {
            super(BlockEntities.PRUDENTIUM_REPROCESSOR, ReprocessorTier.PRUDENTIUM, pos, state);
        }
    }
    public static class Tertium extends ReprocessorBlockEntity {
        public Tertium(BlockPos pos, BlockState state) {
            super(BlockEntities.TERTIUM_REPROCESSOR, ReprocessorTier.TERTIUM, pos, state);
        }
    }
    public static class Imperium extends ReprocessorBlockEntity {
        public Imperium(BlockPos pos, BlockState state) {
            super(BlockEntities.IMPERIUM_REPROCESSOR, ReprocessorTier.IMPERIUM, pos, state);
        }
    }
    public static class Supremium extends ReprocessorBlockEntity {
        public Supremium(BlockPos pos, BlockState state) {
            super(BlockEntities.SUPREMIUM_REPROCESSOR, ReprocessorTier.SUPREMIUM, pos, state);
        }
    }

    public static class AwakenedSupremium extends ReprocessorBlockEntity {
        public AwakenedSupremium(BlockPos pos, BlockState state) {
            super(BlockEntities.AWAKENED_SUPREMIUM_REPROCESSOR, ReprocessorTier.AWAKENED_SUPREMIUM, pos, state);
        }
    }
}
