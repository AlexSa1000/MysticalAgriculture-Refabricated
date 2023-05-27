package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.cucumber.util.Localizable;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MysticalCropBlock extends CropBlock implements CropProvider {
    private final Crop crop;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

    public MysticalCropBlock(Crop crop) {
        super(Settings.copy(Blocks.WHEAT));
        this.crop = crop;
    }

    @Override
    public boolean canGrow(World world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
        return false;
    }

    /*@Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.canGrow(world, pos))
            return;

        super.randomTick(state, world, pos, random);
    }*/

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        int age = state.get(AGE);

        int crop = 0;
        int seed = 1;
        int fertilizer = 0;

        if (age == this.getMaxAge()) {
            crop = 1;

            var vec = builder.get(LootContextParameters.ORIGIN);

            if (vec != null) {
                var world = builder.getWorld();
                var pos = new BlockPos((int) Math.floor(vec.x), (int) Math.floor(vec.y), (int) Math.floor(vec.z));
                var below = world.getBlockState(pos.down()).getBlock();
                double chance = this.crop.getSecondaryChance(below);

                if (Math.random() < chance)
                    crop = 2;

                if ((ModConfigs.SECONDARY_SEED_DROPS.get() && Math.random() < chance))
                    seed = 2;

                double fertilizerChance = ModConfigs.FERTILIZED_ESSENCE_DROP_CHANCE.get();
                if (Math.random() < fertilizerChance)
                    fertilizer = 1;
            }
        }

        List<ItemStack> drops = new ArrayList<>();
        if (crop > 0)
            drops.add(new ItemStack(this.getCropsItem(), crop));

        drops.add(new ItemStack(this.getSeedsItem(), seed));

        if (fertilizer > 0)
            drops.add(new ItemStack(Items.FERTILIZED_ESSENCE));

        return drops;
    }



    /*@Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return super.getPickStack(world, pos, state);
    }*/

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.getBlock() instanceof FarmlandBlock;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return this.crop.getSeedsItem();
    }

    @Override
    public MutableText getName() {
        return Localizable.of("block.mysticalagriculture.mystical_crop").args(this.crop.getDisplayName()).build();
    }

    public Crop getCrop() {
        return this.crop;
    }

    protected ItemConvertible getCropsItem() {
        return this.crop.getEssenceItem();
    }
}
