package com.alex.mysticalagriculture.util;

import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.mysticalagriculture.init.BlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public enum ReprocessorTier {
    BASIC("basic", 200, 20, 80000, ReprocessorBlockEntity.Basic::new, BlockEntities.BASIC_REPROCESSOR, Formatting.GRAY),
    INFERIUM("inferium", 100, 40, 120000, ReprocessorBlockEntity.Inferium::new, BlockEntities.INFERIUM_REPROCESSOR, Formatting.YELLOW),
    PRUDENTIUM("prudentium", 80, 60, 180000, ReprocessorBlockEntity.Prudentium::new, BlockEntities.PRUDENTIUM_REPROCESSOR, Formatting.GREEN),
    TERTIUM("tertium", 54, 100, 300000, ReprocessorBlockEntity.Tertium::new, BlockEntities.TERTIUM_REPROCESSOR, Formatting.GOLD),
    IMPERIUM("imperium", 20, 320, 420000, ReprocessorBlockEntity.Imperium::new, BlockEntities.IMPERIUM_REPROCESSOR, Formatting.AQUA),
    SUPREMIUM("supremium", 5, 1440, 640000, ReprocessorBlockEntity.Supremium::new, BlockEntities.SUPREMIUM_REPROCESSOR, Formatting.RED),
    AWAKENED_SUPREMIUM("awakened_supremium", 1, 2880, 1280000, ReprocessorBlockEntity.AwakenedSupremium::new, BlockEntities.AWAKENED_SUPREMIUM_REPROCESSOR, Formatting.RED);

    private final String name;
    private final int operationTime;
    private final int fuelUsage;
    private final int fuelCapacity;
    private final BiFunction<BlockPos, BlockState, ReprocessorBlockEntity> blockEntityFunc;
    private BlockEntityType<? extends ReprocessorBlockEntity> blockEntityType;
    private final Formatting textColor;

    @SuppressWarnings("unchecked")
    ReprocessorTier(String name, int operationTime, int fuelUsage, int fuelCapacity, BiFunction<BlockPos, BlockState, ReprocessorBlockEntity> blockEntityFunc, BlockEntityType<? extends ReprocessorBlockEntity> blockEntityType, Formatting textColor) {
        this.name = name;
        this.operationTime = operationTime;
        this.fuelUsage = fuelUsage;
        this.fuelCapacity = fuelCapacity;
        this.blockEntityFunc = blockEntityFunc;
        this.blockEntityType = blockEntityType;
        this.textColor = textColor;
    }

    public String getName() {
        return this.name;
    }

    public int getOperationTime() {
        return this.operationTime;
    }

    public int getFuelUsage() {
        return this.fuelUsage;
    }

    public int getFuelCapacity() {
        return this.fuelCapacity;
    }

    /*public void setBlockEntityType(BlockEntityType<? extends ReprocessorBlockEntity> blockEntityType) {
        this.blockEntityType = blockEntityType;
    }*/

    @SuppressWarnings("unchecked")
    public BlockEntityType<? extends ReprocessorBlockEntity> getBlockEntityType() {
        if (Objects.equals(this.name, "basic")) {
            return BlockEntities.BASIC_REPROCESSOR;
        } else if (Objects.equals(this.name, "inferium")) {
            return BlockEntities.INFERIUM_REPROCESSOR;
        } else if (Objects.equals(this.name, "prudentium")) {
            return BlockEntities.PRUDENTIUM_REPROCESSOR;
        } else if (Objects.equals(this.name, "tertium")) {
            return BlockEntities.TERTIUM_REPROCESSOR;
        } else if (Objects.equals(this.name, "imperium")) {
            return BlockEntities.IMPERIUM_REPROCESSOR;
        } else if (Objects.equals(this.name, "supremium")) {
            return BlockEntities.SUPREMIUM_REPROCESSOR;
        } else {
            return BlockEntities.AWAKENED_SUPREMIUM_REPROCESSOR;
        }

    }

    public ReprocessorBlockEntity createTileEntity(BlockPos pos, BlockState state) {
        return this.blockEntityFunc.apply(pos, state);
    }

    public Formatting getTextColor() {
        return this.textColor;
    }
}
