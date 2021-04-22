package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.crafting.recipe.InfusionRecipe;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.util.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.util.util.MultiblockPositions;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class
InfusionAltarBlockEntity extends BaseInventoryBlockEntity implements Tickable, SidedInventory {

    private final SimpleInventory recipeInventory = new SimpleInventory(9);
    private final MultiblockPositions pedestalLocations = new MultiblockPositions.Builder()
            .pos(3, 0, 0).pos(0, 0, 3).pos(-3, 0, 0).pos(0, 0, -3)
            .pos(2, 0, 2).pos(2, 0, -2).pos(-2, 0, 2).pos(-2, 0, -2).build();

    private int progress;
    private boolean active;

    public InfusionAltarBlockEntity() {
        super(BlockEntities.INFUSION_ALTAR, 2);
    }


    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, getStacks());
        this.progress = tag.getInt("Progress");
        this.active = tag.getBoolean("Active");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, getStacks());
        tag.putInt("Progress", this.progress);
        tag.putBoolean("Active", this.active);
        return super.toTag(tag);
    }

    @Override
    public void tick() {
        World world = this.getWorld();
        if (world != null && !world.isClient()) {
            ItemStack input = this.getStack(0);
            if (input.isEmpty()) {
                this.resetBlock();
                return;
            }

            if (this.isActive()) {
                List<InfusionPedestalBlockEntity> pedestals = this.getPedestalsWithStuff();
                this.updateRecipeInventory(pedestals);
                Optional<InfusionRecipe> match = world.getRecipeManager().getFirstMatch(RecipeTypes.INFUSION, this.recipeInventory, world);
                if (match.isPresent()) {
                    this.progress++;
                    if (this.progress >= 100) {
                        DefaultedList<ItemStack> remaining = match.get().getRemainingStacks(this.recipeInventory);
                        for (int i = 0; i < pedestals.size(); i++) {
                            InfusionPedestalBlockEntity pedestal = pedestals.get(i);
                            pedestal.setStack(0, remaining.get(i + 1));
                            this.spawnParticles(ParticleTypes.SMOKE, pedestal.getPos(), 1.2D, 20);
                        }

                        ItemStack result = match.get().craft(this.recipeInventory);

                        this.setOutput(result);
                        this.reset();
                        this.markDirty();
                        this.spawnParticles(ParticleTypes.HAPPY_VILLAGER, this.getPos(), 1.0D, 10);
                    } else {
                        pedestals.forEach(pedestal -> {
                            BlockPos pos = pedestal.getPos();
                            ItemStack stack = pedestal.getStack(0);
                            this.spawnItemParticles(pos, stack);
                        });
                    }
                } else {
                    this.reset();
                }
            } else {
                this.progress = 0;
            }
        }
    }


    public List<BlockPos> getPedestalPositions() {
        return this.pedestalLocations.get(this.getPos());
    }

    public boolean isActive() {
        if (!this.active) {
            World world = this.getWorld();
            this.active = world != null && world.isReceivingRedstonePower(this.getPos());
        }
        return this.active;
    }

    private void reset() {
        this.progress = 0;
        this.active = false;
    }
    private void updateRecipeInventory(List<InfusionPedestalBlockEntity> pedestals) {
        this.recipeInventory.setStack(0, this.getStack(0));
        for (int i = 0; i < pedestals.size(); i++) {
            ItemStack stack = pedestals.get(i).getStack(0);
            this.recipeInventory.setStack(i + 1, stack);
        }
    }

    private List<InfusionPedestalBlockEntity> getPedestalsWithStuff() {
        if (this.getWorld() == null)
            return new ArrayList<>();

        List<InfusionPedestalBlockEntity> pedestals = new ArrayList<>();
        this.getPedestalPositions().forEach(pos -> {
            BlockEntity block = this.getWorld().getBlockEntity(pos);
            if (block instanceof InfusionPedestalBlockEntity) {
                InfusionPedestalBlockEntity pedestal = (InfusionPedestalBlockEntity) block;
                ItemStack stack = pedestal.getStack(0);
                if (!stack.isEmpty())
                    pedestals.add(pedestal);
            }
        });

        return pedestals;
    }

    private <T extends ParticleEffect> void spawnParticles(T particle, BlockPos pos, double yOffset, int count) {
        if (this.getWorld() == null || this.getWorld().isClient)
            return;

        ServerWorld world = (ServerWorld) this.getWorld();

        double x = pos.getX() + 0.5D;
        double y = pos.getY() + yOffset;
        double z = pos.getZ() + 0.5D;

        world.spawnParticles(particle, x, y, z, count, 0, 0, 0, 0.1D);
    }

    private void spawnItemParticles(BlockPos pedestalPos, ItemStack stack) {
        if (this.getWorld() == null || this.getWorld().isClient) return;
        ServerWorld world = (ServerWorld) this.getWorld();
        BlockPos pos = this.getPos();

        double x = pedestalPos.getX() + (world.getRandom().nextDouble() * 0.2D) + 0.4D;
        double y = pedestalPos.getY() + (world.getRandom().nextDouble() * 0.2D) + 1.2D;
        double z = pedestalPos.getZ() + (world.getRandom().nextDouble() * 0.2D) + 0.4D;

        double velX = pos.getX() - pedestalPos.getX();
        double velY = 0.25D;
        double velZ = pos.getZ() - pedestalPos.getZ();

        world.spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), x, y, z, 0, velX, velY, velZ, 0.18D);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.getStack(0).isEmpty() && this.getStack(1).isEmpty();
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return !getStack(1).isEmpty();
    }

    private void setOutput(ItemStack stack) {
        this.setStack(0, ItemStack.EMPTY);
        this.setStack(1, stack);
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

}
