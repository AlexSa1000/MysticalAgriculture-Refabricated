package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.crafting.recipe.AwakeningRecipe;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.util.Activatable;
import com.alex.mysticalagriculture.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.cucumber.helper.StackHelper;
import com.alex.mysticalagriculture.cucumber.util.MultiblockPositions;
import com.alex.mysticalagriculture.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AwakeningAltarBlockEntity extends BaseInventoryBlockEntity implements Activatable/*implements SidedInventory*/ {
    private final MultiblockPositions PEDESTAL_LOCATIONS = new MultiblockPositions.Builder()
            .pos(3, 0, 0).pos(2, 0, 2).pos(-3, 0, 0).pos(-2, 0, -2)
            .pos(0, 0, 3).pos(2, 0, -2).pos(0, 0, -3).pos(-2, 0, 2).build();
    private final BaseItemStackHandler inventory;
    private final BaseItemStackHandler recipeInventory;
    private AwakeningRecipe recipe;
    private int progress;
    private boolean active;

    public AwakeningAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.AWAKENING_ALTAR, pos, state);
        this.inventory = BaseItemStackHandler.create(2, this::markDirty, handler -> {
            handler.setDefaultSlotLimit(1);
            handler.setCanInsert((slot, stack) -> handler.getStack(1).isEmpty());
            handler.setOutputSlots(1);
        });
        this.recipeInventory = BaseItemStackHandler.create(9);
    }

    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.progress = nbt.getInt("Progress");
        this.active = nbt.getBoolean("Active");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putInt("Progress", this.progress);
        nbt.putBoolean("Active", this.active);
    }

    @Override
    public boolean isActive() {
        if (!this.active) {
            World world = this.getWorld();
            this.active = world != null && world.isReceivingRedstonePower(this.getPos());
        }
        return this.active;
    }

    @Override
    public void activate() {
        this.active = true;
    }

    public static void tick(World world, BlockPos pos, BlockState state, AwakeningAltarBlockEntity block) {
        var input = block.inventory.getStack(0);

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
                    var remaining = recipe.getRemainder(block.recipeInventory);

                    for (int i = 0; i < pedestals.size(); i++) {
                        var pedestal = pedestals.get(i);
                        var inventory = pedestal.getInventory();

                        if (pedestal instanceof EssenceVesselBlockEntity) {
                            decrementVesselInventory(inventory, recipe.getEssenceRequirements());
                        } else {
                            inventory.setStack(0, remaining.get(i + 1));
                        }

                        block.spawnParticles(ParticleTypes.SMOKE, pedestal.getPos(), 1.2D, 20);
                    }

                    var result = recipe.craft(block.recipeInventory, world.getRegistryManager());

                    block.setOutput(result);
                    block.reset();
                    block.markDirty();
                    block.spawnParticles(ParticleTypes.HAPPY_VILLAGER, pos, 1.0D, 10);
                } else {
                    for (var pedestal : pedestals) {
                        var pedestalPos = pedestal.getPos();
                        var stack = pedestal.getInventory().getStack(0);

                        block.spawnItemParticles(pedestalPos, stack);
                    }
                }
            } else {
                block.progress = 0;
            }
        }
    }

    public List<BlockPos> getPedestalPositions() {
        return PEDESTAL_LOCATIONS.get(this.getPos());
    }

    private void reset() {
        this.progress = 0;
        this.active = false;
    }

    public AwakeningRecipe getActiveRecipe() {
        if (this.world == null)
            return null;

        this.updateRecipeInventory(this.getPedestals());

        if (this.recipe == null || !this.recipe.matches(this.recipeInventory)) {
            var recipe = this.world.getRecipeManager()
                    .getFirstMatch(RecipeTypes.AWAKENING, this.recipeInventory, this.world)
                    .orElse(null);

            this.recipe = recipe instanceof AwakeningRecipe ? (AwakeningRecipe) recipe : null;
        }

        return this.recipe;
    }

    private void updateRecipeInventory(List<BaseInventoryBlockEntity> pedestals) {
        this.recipeInventory.setSize(AwakeningRecipe.RECIPE_SIZE);
        this.recipeInventory.setStack(0, this.inventory.getStack(0));

        for (int i = 0; i < pedestals.size(); i++) {
            var stack = pedestals.get(i).getInventory().getStack(0);

            this.recipeInventory.setStack(i + 1, stack);
        }
    }

    private List<BaseInventoryBlockEntity> getPedestals() {
        if (this.getWorld() == null)
            return Collections.emptyList();

        List<BaseInventoryBlockEntity> pedestals = new ArrayList<>();

        for (var pos : this.getPedestalPositions()) {
            var tile = this.getWorld().getBlockEntity(pos);
            if (tile instanceof AwakeningPedestalBlockEntity || tile instanceof EssenceVesselBlockEntity)
                pedestals.add((BaseInventoryBlockEntity) tile);
        }

        return pedestals;
    }

    private <T extends ParticleEffect> void spawnParticles(T particle, BlockPos pos, double yOffset, int count) {
        if (this.getWorld() == null || this.getWorld().isClient)
            return;

        var world = (ServerWorld) this.getWorld();

        double x = pos.getX() + 0.5D;
        double y = pos.getY() + yOffset;
        double z = pos.getZ() + 0.5D;

        world.spawnParticles(particle, x, y, z, count, 0, 0, 0, 0.1D);
    }

    private void spawnItemParticles(BlockPos pedestalPos, ItemStack stack) {
        if (this.getWorld() == null || this.getWorld().isClient || stack.isEmpty())
            return;

        var world = (ServerWorld) this.getWorld();
        var pos = this.getPos();

        double x = pedestalPos.getX() + (world.getRandom().nextDouble() * 0.2D) + 0.4D;
        double y = pedestalPos.getY() + (world.getRandom().nextDouble() * 0.2D) + 1.2D;
        double z = pedestalPos.getZ() + (world.getRandom().nextDouble() * 0.2D) + 0.4D;

        double velX = pos.getX() - pedestalPos.getX();
        double velY = 0.25D;
        double velZ = pos.getZ() - pedestalPos.getZ();

        world.spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), x, y, z, 0, velX, velY, velZ, 0.18D);
    }

    private void setOutput(ItemStack stack) {
        this.inventory.getStacks().set(0, ItemStack.EMPTY);
        this.inventory.getStacks().set(1, stack);
    }

    private boolean hasRequiredEssences() {
        boolean hasAir = false, hasEarth = false, hasWater = false, hasFire = false;
        var requirements = this.recipe.getEssenceRequirements();

        for (int i = 1; i < this.recipeInventory.size(); i++) {
            var stack = this.recipeInventory.getStack(i);
            var item = stack.getItem();

            if (item instanceof CropProvider provider) {
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
        var item = inventory.getStack(0).getItem();

        if (item instanceof CropProvider provider) {
            var crop = provider.getCrop();

            if (crop == ModCrops.AIR) inventory.setStack(0, StackHelper.shrink(inventory.getStack(0), requirements.air(), false));
            else if (crop == ModCrops.EARTH) inventory.setStack(0, StackHelper.shrink(inventory.getStack(0), requirements.earth(), false));
            else if (crop == ModCrops.WATER) inventory.setStack(0, StackHelper.shrink(inventory.getStack(0), requirements.water(), false));
            else if (crop == ModCrops.FIRE) inventory.setStack(0, StackHelper.shrink(inventory.getStack(0), requirements.fire(), false));
        }
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
