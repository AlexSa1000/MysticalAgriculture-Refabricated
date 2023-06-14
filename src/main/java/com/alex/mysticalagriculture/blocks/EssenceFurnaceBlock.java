package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.EssenceFurnaceBlockEntity;
import com.alex.cucumber.util.Formatting;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.FurnaceTier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EssenceFurnaceBlock extends AbstractFurnaceBlock {
    private final FurnaceTier tier;

    public EssenceFurnaceBlock(FurnaceTier tier) {
        super(Properties.copy(Blocks.FURNACE));
        this.tier = tier;
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        var tile = level.getBlockEntity(pos);

        if (tile instanceof EssenceFurnaceBlockEntity furnace) {
            player.openMenu(furnace);
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return this.tier.createBlockEntity(pos, state);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) { }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            var tile = level.getBlockEntity(pos);

            if (tile instanceof EssenceFurnaceBlockEntity furnace) {
                Containers.dropContents(level, pos, furnace);
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        double cookingSpeedDifference = 200D * this.tier.getCookTimeMultiplier();
        double cookingSpeedValue = Math.ceil(((200D - cookingSpeedDifference) / cookingSpeedDifference) * 100D) + 100D;
        var cookingSpeed = Formatting.percent(cookingSpeedValue);
        double burnTimeDifference = (1600D * this.tier.getBurnTimeMultiplier()) / cookingSpeedDifference;
        double burnTimeValue = Math.ceil(((burnTimeDifference - 8D) / 8D) * 100D) + 100D;
        var fuelEfficiency = Formatting.percent(burnTimeValue);

        tooltip.add(ModTooltips.COOKING_SPEED.args(cookingSpeed).build());
        tooltip.add(ModTooltips.FUEL_EFFICIENCY.args(fuelEfficiency).build());
    }

    public static class Inferium extends EssenceFurnaceBlock {
        public Inferium() {
            super(FurnaceTier.INFERIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
            return createTicker(level, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type) {
            return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.INFERIUM_FURNACE, EssenceFurnaceBlockEntity.Inferium::tick);
        }
    }

    public static class Prudentium extends EssenceFurnaceBlock {
        public Prudentium() {
            super(FurnaceTier.PRUDENTIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
            return createTicker(level, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type) {
            return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.PRUDENTIUM_FURNACE, EssenceFurnaceBlockEntity.Prudentium::tick);
        }
    }

    public static class Tertium extends EssenceFurnaceBlock {
        public Tertium() {
            super(FurnaceTier.TERTIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
            return createTicker(level, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type) {
            return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.TERTIUM_FURNACE, EssenceFurnaceBlockEntity.Tertium::tick);
        }
    }

    public static class Imperium extends EssenceFurnaceBlock {
        public Imperium() {
            super(FurnaceTier.IMPERIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
            return createTicker(level, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type) {
            return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.IMPERIUM_FURNACE, EssenceFurnaceBlockEntity.Imperium::tick);
        }
    }

    public static class Supremium extends EssenceFurnaceBlock {
        public Supremium() {
            super(FurnaceTier.SUPREMIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
            return createTicker(level, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type) {
            return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.SUPREMIUM_FURNACE, EssenceFurnaceBlockEntity.Supremium::tick);
        }
    }

    public static class AwakenedSupremium extends EssenceFurnaceBlock {
        public AwakenedSupremium() {
            super(FurnaceTier.AWAKENED_SUPREMIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
            return createTicker(level, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type) {
            return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.AWAKENED_SUPREMIUM_FURNACE, EssenceFurnaceBlockEntity.AwakenedSupremium::tick);
        }
    }
}