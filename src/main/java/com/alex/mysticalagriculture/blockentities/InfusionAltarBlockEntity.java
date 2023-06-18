package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.crafting.recipe.InfusionRecipe;
import com.alex.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.cucumber.util.MultiblockPositions;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
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
import java.util.List;

public class InfusionAltarBlockEntity extends BaseInventoryBlockEntity implements IActivatable {
    private final BaseItemStackHandler inventory;
    private final BaseItemStackHandler recipeInventory;
    private final MultiblockPositions pedestalLocations = new MultiblockPositions.Builder()
            .pos(3, 0, 0).pos(0, 0, 3).pos(-3, 0, 0).pos(0, 0, -3)
            .pos(2, 0, 2).pos(2, 0, -2).pos(-2, 0, 2).pos(-2, 0, -2).build();
    private InfusionRecipe recipe;
    private int progress;
    private boolean active;

    public InfusionAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INFUSION_ALTAR, pos, state);
        this.inventory = createInventoryHandler(this::markDirtyAndDispatch);
        this.recipeInventory = BaseItemStackHandler.create(9);
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
            var world = this.getLevel();
            this.active = world != null && world.hasNeighborSignal(this.getBlockPos());
        }

        return this.active;
    }

    @Override
    public void activate() {
        this.active = true;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, InfusionAltarBlockEntity block) {
        var input = block.inventory.getItem(0);

        if (input.isEmpty()) {
            block.reset();
            return;
        }

        if (block.isActive()) {
            var recipe = block.getActiveRecipe();

            if (recipe != null) {
                block.progress++;

                var pedestals = block.getPedestals();

                if (block.progress >= 100) {
                    var remaining = recipe.getRemainingItems(block.recipeInventory);

                    for (int i = 0; i < pedestals.size(); i++) {
                        var pedestal = pedestals.get(i);
                        pedestal.getInventory().setItem(0, remaining.get(i + 1));
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

    public static BaseItemStackHandler createInventoryHandler(Runnable onContentsChanged) {
        return BaseItemStackHandler.create(2, onContentsChanged, builder -> {
            builder.setDefaultSlotLimit(1);
            builder.setCanInsert((slot, stack) -> builder.getItem(1).isEmpty());
            builder.setOutputSlots(1);
        });
    }

    public List<BlockPos> getPedestalPositions() {
        return this.pedestalLocations.get(this.getBlockPos());
    }

    private void reset() {
        this.progress = 0;
        this.active = false;
    }

    public InfusionRecipe getActiveRecipe() {
        if (this.level == null)
            return null;

        this.updateRecipeInventory(this.getPedestals());

        if (this.recipe == null || !this.recipe.matches(this.recipeInventory)) {
            var recipe = this.level.getRecipeManager()
                    .getRecipeFor(ModRecipeTypes.INFUSION, this.recipeInventory, this.level)
                    .orElse(null);

            this.recipe = recipe instanceof InfusionRecipe ? (InfusionRecipe) recipe : null;
        }

        return this.recipe;
    }

    private void updateRecipeInventory(List < InfusionPedestalBlockEntity > pedestals) {
        this.recipeInventory.setSize(InfusionRecipe.RECIPE_SIZE);
        this.recipeInventory.setItem(0, this.inventory.getItem(0));

        for (int i = 0; i < pedestals.size(); i++) {
            var stack = pedestals.get(i).getInventory().getItem(0);

            this.recipeInventory.setItem(i + 1, stack);
        }
    }

    private List<InfusionPedestalBlockEntity> getPedestals() {
        if (this.getLevel() == null)
            return new ArrayList<>();

        List<InfusionPedestalBlockEntity> pedestals = new ArrayList<>();

        this.getPedestalPositions().forEach(pos -> {
            var block = this.getLevel().getBlockEntity(pos);
            if (block instanceof InfusionPedestalBlockEntity pedestal)
                pedestals.add(pedestal);
        });

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

    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    private void setOutput(ItemStack stack) {
        this.inventory.getStacks().set(0, ItemStack.EMPTY);
        this.inventory.getStacks().set(1, stack);
    }

    /*@Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.getStack(0).isEmpty() && this.getStack(1).isEmpty();
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return !getStack(1).isEmpty();
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }*/
}
