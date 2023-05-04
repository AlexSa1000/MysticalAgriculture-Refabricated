package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.crafting.recipe.InfusionRecipe;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.zucchini.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.zucchini.util.MultiblockPositions;
import com.alex.mysticalagriculture.zzz.BaseItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InfusionAltarBlockEntity extends BaseInventoryBlockEntity {
    private final BaseItemStackHandler inventory;
    private final BaseItemStackHandler recipeInventory;
    private final MultiblockPositions pedestalLocations = new MultiblockPositions.Builder()
            .pos(3, 0, 0).pos(0, 0, 3).pos(-3, 0, 0).pos(0, 0, -3)
            .pos(2, 0, 2).pos(2, 0, -2).pos(-2, 0, 2).pos(-2, 0, -2).build();
    private InfusionRecipe recipe;
    private int progress;
    private boolean active;

    public InfusionAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.INFUSION_ALTAR, pos, state);
        this.inventory = createInventoryHandler(this::markDirty);
        this.recipeInventory = BaseItemStackHandler.create(9);
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

    public boolean isActive() {
        if (!this.active) {
            World world = this.getWorld();
            this.active = world != null && world.isReceivingRedstonePower(this.getPos());
        }
        return this.active;
    }

    public static void tick(World world, BlockPos pos, BlockState state, InfusionAltarBlockEntity block) {
        var input = block.inventory.getStack(0);

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
                    var remaining = recipe.getRemainder(block.recipeInventory);

                    for (int i = 0; i < pedestals.size(); i++) {
                        var pedestal = pedestals.get(i);
                        pedestal.getInventory().setStack(0, remaining.get(i + 1));
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

    public static BaseItemStackHandler createInventoryHandler(Runnable onContentsChanged) {
        return BaseItemStackHandler.create(2, onContentsChanged, builder -> {
            builder.setDefaultSlotLimit(1);
            builder.setCanInsert((slot, stack) -> builder.getStack(1).isEmpty());
            builder.setOutputSlots(1);
        });
    }

    public List<BlockPos> getPedestalPositions() {
        return this.pedestalLocations.get(this.getPos());
    }

    private void reset() {
        this.progress = 0;
        this.active = false;
    }

    public InfusionRecipe getActiveRecipe() {
        if (this.world == null)
            return null;

        this.updateRecipeInventory(this.getPedestals());

        if (this.recipe == null || !this.recipe.matches(this.recipeInventory)) {
            var recipe = this.world.getRecipeManager()
                    .getFirstMatch(RecipeTypes.INFUSION, this.recipeInventory, this.world)
                    .orElse(null);

            this.recipe = recipe instanceof InfusionRecipe ? (InfusionRecipe) recipe : null;
        }

        return this.recipe;
    }

    private void updateRecipeInventory(List < InfusionPedestalBlockEntity > pedestals) {
        this.recipeInventory.setSize(InfusionRecipe.RECIPE_SIZE);
        this.recipeInventory.setStack(0, this.inventory.getStack(0));

        for (int i = 0; i < pedestals.size(); i++) {
            var stack = pedestals.get(i).getInventory().getStack(0);

            this.recipeInventory.setStack(i + 1, stack);
        }
    }

    private List<InfusionPedestalBlockEntity> getPedestals() {
        if (this.getWorld() == null)
            return new ArrayList<>();

        List<InfusionPedestalBlockEntity> pedestals = new ArrayList<>();

        this.getPedestalPositions().forEach(pos -> {
            var block = this.getWorld().getBlockEntity(pos);
            if (block instanceof InfusionPedestalBlockEntity pedestal)
                pedestals.add(pedestal);
        });

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
