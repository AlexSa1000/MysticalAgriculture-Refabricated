package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.api.crop.ICropProvider;
import com.alex.mysticalagriculture.crafting.recipe.AwakeningRecipe;
import com.alex.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.cucumber.helper.StackHelper;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.cucumber.util.MultiblockPositions;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.util.IActivatable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AwakeningAltarBlockEntity extends BaseInventoryBlockEntity implements IActivatable {
    private final MultiblockPositions PEDESTAL_LOCATIONS = new MultiblockPositions.Builder()
            .pos(3, 0, 0).pos(2, 0, 2).pos(-3, 0, 0).pos(-2, 0, -2)
            .pos(0, 0, 3).pos(2, 0, -2).pos(0, 0, -3).pos(-2, 0, 2).build();
    private final BaseItemStackHandler inventory;
    private final BaseItemStackHandler recipeInventory;
    private AwakeningRecipe recipe;
    private int progress;
    private boolean active;

    public AwakeningAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AWAKENING_ALTAR, pos, state);
        this.inventory = BaseItemStackHandler.create(2, this::markDirtyAndDispatch, handler -> {
            handler.setDefaultSlotLimit(1);
            handler.setCanInsert((slot, stack) -> handler.getItem(1).isEmpty());
            handler.setOutputSlots(1);
        });
        this.recipeInventory = BaseItemStackHandler.create(9);
    }

    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        this.progress = tag.getInt("Progress");
        this.active = tag.getBoolean("Active");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putInt("Progress", this.progress);
        tag.putBoolean("Active", this.active);
    }

    @Override
    public boolean isActive() {
        if (!this.active) {
            Level level = this.getLevel();
            this.active = level != null && level.hasNeighborSignal(this.getBlockPos());
        }
        return this.active;
    }

    @Override
    public void activate() {
        this.active = true;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AwakeningAltarBlockEntity block) {
        var input = block.inventory.getItem(0);

        if (input.isEmpty()) {
            block.reset();
            return;
        }

        if (block.isActive()) {
            var recipe = block.getActiveRecipe();

            if (recipe != null && block.hasRequiredEssences()) {
                block.progress++;

                var pedestals = block.getPedestals();

                if (block.progress >= 100) {
                    var remaining = recipe.getRemainingItems(block.recipeInventory);

                    for (int i = 0; i < pedestals.size(); i++) {
                        var pedestal = pedestals.get(i);
                        var inventory = pedestal.getInventory();

                        if (pedestal instanceof EssenceVesselBlockEntity) {
                            decrementVesselInventory(inventory, recipe.getEssenceRequirements());
                        } else {
                            inventory.setItem(0, remaining.get(i + 1));
                        }

                        block.spawnParticles(ParticleTypes.SMOKE, pedestal.getBlockPos(), 1.2D, 20);
                    }

                    var result = recipe.assemble(block.recipeInventory);

                    block.setOutput(result);
                    block.reset();
                    block.markDirtyAndDispatch();
                    block.spawnParticles(ParticleTypes.HAPPY_VILLAGER, pos, 1.0D, 10);
                } else {
                    for (var pedestal : pedestals) {
                        var pedestalPos = pedestal.getBlockPos();
                        var stack = pedestal.getInventory().getItem(0);

                        block.spawnItemParticles(pedestalPos, stack);
                    }
                }
            } else {
                block.progress = 0;
            }
        }
    }

    public List<BlockPos> getPedestalPositions() {
        return PEDESTAL_LOCATIONS.get(this.getBlockPos());
    }

    private void reset() {
        this.progress = 0;
        this.active = false;
    }

    public AwakeningRecipe getActiveRecipe() {
        if (this.level == null)
            return null;

        this.updateRecipeInventory(this.getPedestals());

        if (this.recipe == null || !this.recipe.matches(this.recipeInventory)) {
            var recipe = this.level.getRecipeManager()
                    .getRecipeFor(ModRecipeTypes.AWAKENING, this.recipeInventory, this.level)
                    .orElse(null);

            this.recipe = recipe instanceof AwakeningRecipe ? (AwakeningRecipe) recipe : null;
        }

        return this.recipe;
    }

    public List<EssenceVesselBlockEntity> getEssenceVessels() {
        return this.getPedestals()
                .stream()
                .filter(p -> p instanceof EssenceVesselBlockEntity)
                .map(p -> (EssenceVesselBlockEntity) p)
                .toList();
    }

    private void updateRecipeInventory(List<BaseInventoryBlockEntity> pedestals) {
        this.recipeInventory.setSize(AwakeningRecipe.RECIPE_SIZE);
        this.recipeInventory.setItem(0, this.inventory.getItem(0));

        for (int i = 0; i < pedestals.size(); i++) {
            var stack = pedestals.get(i).getInventory().getItem(0);

            this.recipeInventory.setItem(i + 1, stack);
        }
    }

    private List<BaseInventoryBlockEntity> getPedestals() {
        if (this.getLevel() == null)
            return Collections.emptyList();

        List<BaseInventoryBlockEntity> pedestals = new ArrayList<>();

        for (var pos : this.getPedestalPositions()) {
            var tile = this.getLevel().getBlockEntity(pos);
            if (tile instanceof AwakeningPedestalBlockEntity || tile instanceof EssenceVesselBlockEntity)
                pedestals.add((BaseInventoryBlockEntity) tile);
        }

        return pedestals;
    }

    private <T extends ParticleOptions> void spawnParticles(T particle, BlockPos pos, double yOffset, int count) {
        if (this.getLevel() == null || this.getLevel().isClientSide())
            return;

        var level = (ServerLevel) this.getLevel();

        double x = pos.getX() + 0.5D;
        double y = pos.getY() + yOffset;
        double z = pos.getZ() + 0.5D;

        level.sendParticles(particle, x, y, z, count, 0, 0, 0, 0.1D);
    }

    private void spawnItemParticles(BlockPos pedestalPos, ItemStack stack) {
        if (this.getLevel() == null || this.getLevel().isClientSide() || stack.isEmpty())
            return;

        var level = (ServerLevel) this.getLevel();
        var pos = this.getBlockPos();

        double x = pedestalPos.getX() + (level.getRandom().nextDouble() * 0.2D) + 0.4D;
        double y = pedestalPos.getY() + (level.getRandom().nextDouble() * 0.2D) + 1.2D;
        double z = pedestalPos.getZ() + (level.getRandom().nextDouble() * 0.2D) + 0.4D;

        double velX = pos.getX() - pedestalPos.getX();
        double velY = 0.25D;
        double velZ = pos.getZ() - pedestalPos.getZ();

        level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), x, y, z, 0, velX, velY, velZ, 0.18D);
    }

    private void setOutput(ItemStack stack) {
        this.inventory.getStacks().set(0, ItemStack.EMPTY);
        this.inventory.getStacks().set(1, stack);
    }

    private boolean hasRequiredEssences() {
        boolean hasAir = false, hasEarth = false, hasWater = false, hasFire = false;
        var requirements = this.recipe.getEssenceRequirements();

        for (int i = 1; i < this.recipeInventory.getContainerSize(); i++) {
            var stack = this.recipeInventory.getItem(i);
            var item = stack.getItem();

            if (item instanceof ICropProvider provider) {
                var crop = provider.getCrop();
                var count = stack.getCount();

                if (!hasAir && crop == ModCrops.AIR) hasAir = count >= requirements.air();
                if (!hasEarth && crop == ModCrops.EARTH) hasEarth = count >= requirements.earth();
                if (!hasWater && crop == ModCrops.WATER) hasWater = count >= requirements.water();
                if (!hasFire && crop == ModCrops.FIRE) hasFire = count >= requirements.fire();
            }
        }

        return hasAir && hasEarth && hasWater && hasFire;
    }

    private static void decrementVesselInventory(BaseItemStackHandler inventory, AwakeningRecipe.EssenceVesselRequirements requirements) {
        var item = inventory.getItem(0).getItem();

        if (item instanceof ICropProvider provider) {
            var crop = provider.getCrop();

            if (crop == ModCrops.AIR) inventory.setStackInSlot(0, StackHelper.shrink(inventory.getItem(0), requirements.air(), false));
            else if (crop == ModCrops.EARTH) inventory.setStackInSlot(0, StackHelper.shrink(inventory.getItem(0), requirements.earth(), false));
            else if (crop == ModCrops.WATER) inventory.setStackInSlot(0, StackHelper.shrink(inventory.getItem(0), requirements.water(), false));
            else if (crop == ModCrops.FIRE) inventory.setStackInSlot(0, StackHelper.shrink(inventory.getItem(0), requirements.fire(), false));
        }
    }
}
