package com.alex.mysticalagriculture.util;

import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.function.BiFunction;

public enum ReprocessorTier {
    BASIC("basic", 200, 20, 80000, ReprocessorBlockEntity.Basic::new, ModBlockEntities.BASIC_REPROCESSOR, ChatFormatting.GRAY),
    INFERIUM("inferium", 100, 40, 120000, ReprocessorBlockEntity.Inferium::new, ModBlockEntities.INFERIUM_REPROCESSOR, ChatFormatting.YELLOW),
    PRUDENTIUM("prudentium", 80, 60, 180000, ReprocessorBlockEntity.Prudentium::new, ModBlockEntities.PRUDENTIUM_REPROCESSOR, ChatFormatting.GREEN),
    TERTIUM("tertium", 54, 100, 300000, ReprocessorBlockEntity.Tertium::new, ModBlockEntities.TERTIUM_REPROCESSOR, ChatFormatting.GOLD),
    IMPERIUM("imperium", 20, 320, 420000, ReprocessorBlockEntity.Imperium::new, ModBlockEntities.IMPERIUM_REPROCESSOR, ChatFormatting.AQUA),
    SUPREMIUM("supremium", 5, 1440, 640000, ReprocessorBlockEntity.Supremium::new, ModBlockEntities.SUPREMIUM_REPROCESSOR, ChatFormatting.RED),
    AWAKENED_SUPREMIUM("awakened_supremium", 1, 2880, 1280000, ReprocessorBlockEntity.AwakenedSupremium::new, ModBlockEntities.AWAKENED_SUPREMIUM_REPROCESSOR, ChatFormatting.RED);

    private final String name;
    private final int operationTime;
    private final int fuelUsage;
    private final int fuelCapacity;
    private final BiFunction<BlockPos, BlockState, ReprocessorBlockEntity> blockEntityFunc;
    private BlockEntityType<? extends ReprocessorBlockEntity> blockEntityType;
    private final ChatFormatting textColor;

    @SuppressWarnings("unchecked")
    ReprocessorTier(String name, int operationTime, int fuelUsage, int fuelCapacity, BiFunction<BlockPos, BlockState, ReprocessorBlockEntity> blockEntityFunc, BlockEntityType<? extends ReprocessorBlockEntity> blockEntityType, ChatFormatting textColor) {
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
            return ModBlockEntities.BASIC_REPROCESSOR;
        } else if (Objects.equals(this.name, "inferium")) {
            return ModBlockEntities.INFERIUM_REPROCESSOR;
        } else if (Objects.equals(this.name, "prudentium")) {
            return ModBlockEntities.PRUDENTIUM_REPROCESSOR;
        } else if (Objects.equals(this.name, "tertium")) {
            return ModBlockEntities.TERTIUM_REPROCESSOR;
        } else if (Objects.equals(this.name, "imperium")) {
            return ModBlockEntities.IMPERIUM_REPROCESSOR;
        } else if (Objects.equals(this.name, "supremium")) {
            return ModBlockEntities.SUPREMIUM_REPROCESSOR;
        } else {
            return ModBlockEntities.AWAKENED_SUPREMIUM_REPROCESSOR;
        }

    }

    public ReprocessorBlockEntity createTileEntity(BlockPos pos, BlockState state) {
        return this.blockEntityFunc.apply(pos, state);
    }

    public ChatFormatting getTextColor() {
        return this.textColor;
    }
}
