package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.api.crop.ICropProvider;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.cucumber.util.Localizable;
import com.alex.mysticalagriculture.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class MysticalCropBlock extends CropBlock implements ICropProvider {
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    private final Crop crop;

    public MysticalCropBlock(Crop crop) {
        super(Properties.copy(Blocks.WHEAT));
        this.crop = crop;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return false;
    }

    /*@Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.canGrow(world, pos))
            return;

        super.randomTick(state, world, pos, random);
    }*/

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public String getDescriptionId() {
        return Localizable.of("block.mysticalagriculture.mystical_crop").args(this.crop.getDisplayName()).buildString();
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        int age = state.getValue(AGE);

        int crop = 0;
        int seed = 1;
        int fertilizer = 0;

        if (age == this.getMaxAge()) {
            crop = 1;

            var vec = builder.getOptionalParameter(LootContextParams.ORIGIN);

            if (vec != null) {
                var level = builder.getLevel();
                var pos = new BlockPos((int) Math.floor(vec.x), (int) Math.floor(vec.y), (int) Math.floor(vec.z));
                var below = level.getBlockState(pos.below()).getBlock();
                double chance = this.crop.getSecondaryChance(below);

                if (Math.random() < chance)
                    crop = 2;

                if (ModConfigs.SECONDARY_SEED_DROPS.get() && Math.random() < chance)
                    seed = 2;

                double fertilizerChance = ModConfigs.FERTILIZED_ESSENCE_DROP_CHANCE.get();
                if (Math.random() < fertilizerChance)
                    fertilizer = 1;
            }
        }

        List<ItemStack> drops = new ArrayList<>();

        if (crop > 0)
            drops.add(new ItemStack(this.getCropsItem(), crop));

        drops.add(new ItemStack(this.getBaseSeedId(), seed));

        if (fertilizer > 0)
            drops.add(new ItemStack(ModItems.FERTILIZED_ESSENCE));

        return drops;
    }

    /*@Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return super.getPickStack(world, pos, state);
    }*/

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getBlock() instanceof FarmBlock;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.crop.getSeedsItem();
    }

    public Crop getCrop() {
        return this.crop;
    }

    protected ItemLike getCropsItem() {
        return this.crop.getEssenceItem();
    }
}