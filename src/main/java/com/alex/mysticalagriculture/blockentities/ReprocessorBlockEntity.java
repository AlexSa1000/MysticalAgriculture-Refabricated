package com.alex.mysticalagriculture.blockentities;

import com.alex.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.cucumber.energy.BaseEnergyStorage;
import com.alex.cucumber.forge.common.util.LazyOptional;
import com.alex.cucumber.helper.StackHelper;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.cucumber.inventory.SidedItemStackHandler;
import com.alex.cucumber.util.Localizable;
import com.alex.mysticalagriculture.container.ReprocessorContainer;
import com.alex.mysticalagriculture.crafting.recipe.ReprocessorRecipe;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import com.alex.mysticalagriculture.util.RecipeIngredientCache;
import com.alex.mysticalagriculture.util.ReprocessorTier;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ReprocessorBlockEntity extends BaseInventoryBlockEntity implements ExtendedScreenHandlerFactory {
    private static final int FUEL_TICK_MULTIPLIER = 20;
    private final BaseItemStackHandler inventory;
    private final BaseEnergyStorage energy;
    public final LazyOptional<SidedItemStackHandler>[] inventoryCapabilities;
    private final ReprocessorTier tier;
    private ReprocessorRecipe recipe;
    private int progress;
    private int fuelLeft;
    private int fuelItemValue;

    public ReprocessorBlockEntity(BlockEntityType<?> type, ReprocessorTier tier, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.inventory = createInventoryHandler(this::markDirtyAndDispatch);
        this.energy = new BaseEnergyStorage(tier.getFuelCapacity(), this::markDirtyAndDispatch);
        this.inventoryCapabilities = SidedItemStackHandler.create(this.inventory, new Direction[] { Direction.UP, Direction.DOWN, Direction.NORTH }, this::canInsertStackSided, null);
        this.tier = tier;
    }

    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        this.progress = tag.getInt("Progress");
        this.fuelLeft = tag.getInt("FuelLeft");
        this.fuelItemValue = tag.getInt("FuelItemValue");
        this.energy.deserializeNBT(tag.get("Energy"));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putInt("Progress", this.progress);
        tag.putInt("FuelLeft", this.fuelLeft);
        tag.putInt("FuelItemValue", this.fuelItemValue);
        tag.put("Energy", this.energy.serializeNBT());
    }

    @Override
    public Component getDisplayName() {
        return Localizable.of("container.mysticalagriculture.reprocessor").build();
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return ReprocessorContainer.create(id, playerInventory, this.inventory, this.getBlockPos());
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ReprocessorBlockEntity block) {
        var mark = false;

        if (block.energy.getAmount() < block.energy.getCapacity()) {
            var fuel = block.inventory.getItem(1);

            if (block.fuelLeft <= 0 && !fuel.isEmpty()) {
                block.fuelItemValue = block.getFuelTime(fuel);

                if (block.fuelItemValue > 0) {
                    block.fuelLeft = block.fuelItemValue *= FUEL_TICK_MULTIPLIER;
                    block.inventory.setItem(1, StackHelper.shrink(block.inventory.getItem(1), 1, false));

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
            var input = block.inventory.getItem(0);
            var output = block.inventory.getItem(2);

            if (!input.isEmpty()) {
                if (block.recipe == null || !block.recipe.matches(block.inventory)) {
                    var recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.REPROCESSOR, block.inventory, level).orElse(null);
                    block.recipe = recipe instanceof ReprocessorRecipe ? (ReprocessorRecipe) recipe : null;
                }

                if (block.recipe != null) {
                    var recipeOutput = block.recipe.assemble(block.inventory, level.registryAccess());
                    if (!recipeOutput.isEmpty() && (output.isEmpty() || StackHelper.canCombineStacks(output, recipeOutput))) {
                        block.progress++;
                        try (Transaction transaction = Transaction.openOuter()) {
                            block.energy.extract(block.tier.getFuelUsage(), transaction);
                            transaction.commit();
                        }
                        if (block.progress >= block.tier.getOperationTime()) {
                            block.inventory.setItem(0, StackHelper.shrink(block.inventory.getItem(0), 1, false));

                            var result = StackHelper.combineStacks(output, recipeOutput);
                            block.inventory.setItem(2, result);

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
            block.markDirtyAndDispatch();
        }
    }

    protected int getFuelTime(ItemStack fuel) {
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

    private boolean canInsertStackSided(int slot, ItemStack stack, Direction direction) {
        if (direction == null)
            return true;
        if (slot == 0 && direction == Direction.UP)
            return RecipeIngredientCache.INSTANCE.isValidInput(stack, ModRecipeTypes.REPROCESSOR);
        if (slot == 1 && direction == Direction.NORTH)
            return FurnaceBlockEntity.isFuel(stack);

        return false;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeBlockPos(worldPosition);
    }

    public static class Basic extends ReprocessorBlockEntity {
        public Basic(BlockPos pos, BlockState state) {
            super(ModBlockEntities.BASIC_REPROCESSOR, ReprocessorTier.BASIC, pos, state);
        }
    }
    public static class Inferium extends ReprocessorBlockEntity {
        public Inferium(BlockPos pos, BlockState state) {
            super(ModBlockEntities.INFERIUM_REPROCESSOR, ReprocessorTier.INFERIUM, pos, state);
        }
    }
    public static class Prudentium extends ReprocessorBlockEntity {
        public Prudentium(BlockPos pos, BlockState state) {
            super(ModBlockEntities.PRUDENTIUM_REPROCESSOR, ReprocessorTier.PRUDENTIUM, pos, state);
        }
    }
    public static class Tertium extends ReprocessorBlockEntity {
        public Tertium(BlockPos pos, BlockState state) {
            super(ModBlockEntities.TERTIUM_REPROCESSOR, ReprocessorTier.TERTIUM, pos, state);
        }
    }
    public static class Imperium extends ReprocessorBlockEntity {
        public Imperium(BlockPos pos, BlockState state) {
            super(ModBlockEntities.IMPERIUM_REPROCESSOR, ReprocessorTier.IMPERIUM, pos, state);
        }
    }
    public static class Supremium extends ReprocessorBlockEntity {
        public Supremium(BlockPos pos, BlockState state) {
            super(ModBlockEntities.SUPREMIUM_REPROCESSOR, ReprocessorTier.SUPREMIUM, pos, state);
        }
    }

    public static class AwakenedSupremium extends ReprocessorBlockEntity {
        public AwakenedSupremium(BlockPos pos, BlockState state) {
            super(ModBlockEntities.AWAKENED_SUPREMIUM_REPROCESSOR, ReprocessorTier.AWAKENED_SUPREMIUM, pos, state);
        }
    }
}