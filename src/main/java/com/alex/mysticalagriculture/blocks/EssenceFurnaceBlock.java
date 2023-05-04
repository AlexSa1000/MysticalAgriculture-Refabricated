package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.EssenceFurnaceBlockEntity;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.FurnaceTier;
import com.alex.mysticalagriculture.zucchini.util.Formatting;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EssenceFurnaceBlock extends AbstractFurnaceBlock {
    private final FurnaceTier tier;

    public EssenceFurnaceBlock(FurnaceTier tier) {
        super(Settings.copy(Blocks.FURNACE));
        this.tier = tier;
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        var tile = world.getBlockEntity(pos);

        if (tile instanceof EssenceFurnaceBlockEntity) {
            player.openHandledScreen((EssenceFurnaceBlockEntity) tile);
            player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return this.tier.createBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            var tile = world.getBlockEntity(pos);

            if (tile instanceof EssenceFurnaceBlockEntity furnace) {
                ItemScatterer.spawn(world, pos, furnace);
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
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
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return createTicker(world, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(World world, BlockEntityType<T> type) {
            return world.isClient ? null : checkType(type, BlockEntities.INFERIUM_FURNACE, EssenceFurnaceBlockEntity.Inferium::tick);
        }
    }

    public static class Prudentium extends EssenceFurnaceBlock {
        public Prudentium() {
            super(FurnaceTier.PRUDENTIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return createTicker(world, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(World world, BlockEntityType<T> type) {
            return world.isClient ? null : checkType(type, BlockEntities.PRUDENTIUM_FURNACE, EssenceFurnaceBlockEntity.Prudentium::tick);
        }
    }

    public static class Tertium extends EssenceFurnaceBlock {
        public Tertium() {
            super(FurnaceTier.TERTIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return createTicker(world, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(World world, BlockEntityType<T> type) {
            return world.isClient ? null : checkType(type, BlockEntities.TERTIUM_FURNACE, EssenceFurnaceBlockEntity.Tertium::tick);
        }
    }

    public static class Imperium extends EssenceFurnaceBlock {
        public Imperium() {
            super(FurnaceTier.IMPERIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return createTicker(world, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(World world, BlockEntityType<T> type) {
            return world.isClient ? null : checkType(type, BlockEntities.IMPERIUM_FURNACE, EssenceFurnaceBlockEntity.Imperium::tick);
        }
    }

    public static class Supremium extends EssenceFurnaceBlock {
        public Supremium() {
            super(FurnaceTier.SUPREMIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return createTicker(world, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(World world, BlockEntityType<T> type) {
            return world.isClient ? null : checkType(type, BlockEntities.SUPREMIUM_FURNACE, EssenceFurnaceBlockEntity.Supremium::tick);
        }
    }

    public static class AwakenedSupremium extends EssenceFurnaceBlock {
        public AwakenedSupremium() {
            super(FurnaceTier.AWAKENED_SUPREMIUM);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return createTicker(world, type);
        }

        protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(World world, BlockEntityType<T> type) {
            return world.isClient ? null : checkType(type, BlockEntities.AWAKENED_SUPREMIUM_FURNACE, EssenceFurnaceBlockEntity.AwakenedSupremium::tick);
        }
    }
}
