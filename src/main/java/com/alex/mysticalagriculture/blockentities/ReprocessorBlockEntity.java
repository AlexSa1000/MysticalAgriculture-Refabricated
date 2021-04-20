package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.blocks.ReprocessorBlock;
import com.alex.mysticalagriculture.container.ReprocessorContainer;
import com.alex.mysticalagriculture.crafting.recipe.ReprocessorRecipe;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.util.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.util.helper.StackHelper;
import com.alex.mysticalagriculture.util.util.Localizable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class ReprocessorBlockEntity extends BaseInventoryBlockEntity implements NamedScreenHandlerFactory, Tickable, SidedInventory {
    private int progress;
    private int fuel;
    private int fuelLeft;
    private int fuelItemValue;
    private final PropertyDelegate data = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: return ReprocessorBlockEntity.this.getProgress();
                case 1: return ReprocessorBlockEntity.this.getFuel();
                case 2: return ReprocessorBlockEntity.this.getFuelLeft();
                case 3: return ReprocessorBlockEntity.this.getFuelItemValue();
                case 4: return ReprocessorBlockEntity.this.getOperationTime();
                case 5: return ReprocessorBlockEntity.this.getFuelCapacity();
                default: return 0;
            }
        }

        @Override
        public void set(int index, int value) { }

        @Override
        public int size() {
            return 6;
        }
    };

    public ReprocessorBlockEntity(BlockEntityType<?> type) {
        super(type, 3);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.progress = tag.getInt("Progress");
        this.fuel = tag.getInt("Fuel");
        this.fuelLeft = tag.getInt("FuelLeft");
        this.fuelItemValue = tag.getInt("FuelItemValue");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag = super.toTag(tag);
        tag.putInt("Progress", this.progress);
        tag.putInt("Fuel", this.fuel);
        tag.putInt("FuelLeft", this.fuelLeft);
        tag.putInt("FuelItemValue", this.fuelItemValue);

        return tag;
    }

    @Override
    public void tick() {
        World world = this.getWorld();
        if (world == null || world.isClient())
            return;

        boolean mark = false;
        int fuelPerTick = Math.min(Math.min(this.fuelLeft, this.getFuelUsage() * 2), this.getFuelCapacity() - this.fuel);
        if (this.fuel < this.getFuelCapacity()) {
            ItemStack fuel = this.getStack(1);

            if (this.fuelLeft <= 0 && !fuel.isEmpty()) {
                System.out.println(this.getFuelTime(fuel));
                this.fuelItemValue = this.getFuelTime(fuel);
                if (this.fuelItemValue > 0) {
                    this.fuelLeft = this.fuelItemValue;
                    this.removeStack(1, 1);
                }
            }

            if (this.fuelLeft > 0) {
                this.fuel += fuelPerTick;
                this.fuelLeft -= fuelPerTick;

                if (this.fuelLeft <= 0)
                    this.fuelItemValue = 0;

                mark = true;
            }
        }

        if (this.fuel >= this.getFuelUsage()) {
            ItemStack input = this.getStack(0);
            ItemStack output = this.getStack(2);

            if (!input.isEmpty()) {
                Optional<ReprocessorRecipe> recipe = world.getRecipeManager().getFirstMatch(RecipeTypes.REPROCESSOR, this, world);

                if (recipe.isPresent()) {
                    ItemStack recipeOutput = recipe.get().craft(this.toInventory());
                    if (!recipeOutput.isEmpty() && (output.isEmpty() || StackHelper.canCombineStacks(output, recipeOutput))) {
                        this.progress++;
                        this.fuel -= this.getFuelUsage();

                        if (this.progress >= this.getOperationTime()) {
                            this.removeStack(0, 1);
                            ItemStack result = StackHelper.combineStacks(output, recipeOutput);
                            this.setStack(2, result);
                            this.progress = 0;
                        }
                        mark = true;
                    }
                }
            } else {
                if (this.progress > 0) {
                    this.progress = 0;
                    ReprocessorRecipe recipe = null;
                    mark = true;
                }
            }
        }

        if (mark)
            this.markDirty();
    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();
            return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
        }
    }

    @Override
    public Text getDisplayName() {
        return Localizable.of("container.mysticalagriculture.reprocessor").build();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return ReprocessorContainer.create(syncId, inv, this::isUsableByPlayer, this, this.data);
    }


    public int getProgress() {
        return this.progress;
    }

    public int getOperationTime() {
        return this.getTier().getOperationTime();
    }

    public int getFuel() {
        return this.fuel;
    }

    public int getFuelUsage() {
        return this.getTier().getFuelUsage();
    }

    public int getFuelCapacity() {
        return this.getTier().getFuelCapacity();
    }

    public int getFuelLeft() {
        return this.fuelLeft;
    }

    public int getFuelItemValue() {
        return this.fuelItemValue;
    }


    @Override
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
    }

    public abstract ReprocessorBlock.ReprocessorTier getTier();

    public static class Basic extends ReprocessorBlockEntity {
        public Basic() {
            super(BlockEntities.BASIC_REPROCESSOR);
        }

        @Override
        public ReprocessorBlock.ReprocessorTier getTier() {
            return ReprocessorBlock.ReprocessorTier.BASIC;
        }
    }
    public static class Inferium extends ReprocessorBlockEntity {
        public Inferium() {
            super(BlockEntities.INFERIUM_REPROCESSOR);
        }

        @Override
        public ReprocessorBlock.ReprocessorTier getTier() {
            return ReprocessorBlock.ReprocessorTier.INFERIUM;
        }
    }
    public static class Prudentium extends ReprocessorBlockEntity {
        public Prudentium() {
            super(BlockEntities.PRUDENTIUM_REPROCESSOR);
        }

        @Override
        public ReprocessorBlock.ReprocessorTier getTier() {
            return ReprocessorBlock.ReprocessorTier.PRUDENTIUM;
        }
    }
    public static class Tertium extends ReprocessorBlockEntity {
        public Tertium() {
            super(BlockEntities.TERTIUM_REPROCESSOR);
        }

        @Override
        public ReprocessorBlock.ReprocessorTier getTier() {
            return ReprocessorBlock.ReprocessorTier.TERTIUM;
        }
    }
    public static class Imperium extends ReprocessorBlockEntity {
        public Imperium() {
            super(BlockEntities.IMPERIUM_REPROCESSOR);
        }

        @Override
        public ReprocessorBlock.ReprocessorTier getTier() {
            return ReprocessorBlock.ReprocessorTier.IMPERIUM;
        }
    }
    public static class Supremium extends ReprocessorBlockEntity {
        public Supremium() {
            super(BlockEntities.SUPREMIUM_REPROCESSOR);
        }

        @Override
        public ReprocessorBlock.ReprocessorTier getTier() {
            return ReprocessorBlock.ReprocessorTier.SUPREMIUM;
        }
    }
}
