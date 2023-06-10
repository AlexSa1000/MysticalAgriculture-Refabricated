package com.alex.mysticalagriculture.util;

import com.alex.mysticalagriculture.blockentities.EssenceFurnaceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

public enum FurnaceTier {
    INFERIUM("inferium", 0.84D, 0.84D, EssenceFurnaceBlockEntity.Inferium::new),
    PRUDENTIUM("prudentium", 0.625D, 0.84D, EssenceFurnaceBlockEntity.Prudentium::new),
    TERTIUM("tertium", 0.4D, 0.68D, EssenceFurnaceBlockEntity.Tertium::new),
    IMPERIUM("imperium", 0.145D, 0.5D, EssenceFurnaceBlockEntity.Imperium::new),
    SUPREMIUM("supremium", 0.025D, 0.2D, EssenceFurnaceBlockEntity.Supremium::new),
    AWAKENED_SUPREMIUM("awakened_supremium", 0.005D, 0.1D, EssenceFurnaceBlockEntity.AwakenedSupremium::new);

    private final String name;
    private final double cookTimeMultiplier;
    private final double burnTimeMultiplier;
    private final BiFunction<BlockPos, BlockState, EssenceFurnaceBlockEntity> tileEntityFunc;

    FurnaceTier(String name, double cookTimeMultiplier, double burnTimeMultiplier, BiFunction<BlockPos, BlockState, EssenceFurnaceBlockEntity> tileEntityFunc) {
        this.name = name;
        this.cookTimeMultiplier = cookTimeMultiplier;
        this.burnTimeMultiplier = burnTimeMultiplier;
        this.tileEntityFunc = tileEntityFunc;
    }

    public String getName() {
        return this.name;
    }

    public double getCookTimeMultiplier() {
        return this.cookTimeMultiplier;
    }

    public double getBurnTimeMultiplier() {
        return this.burnTimeMultiplier;
    }

    public EssenceFurnaceBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return this.tileEntityFunc.apply(pos, state);
    }
}
