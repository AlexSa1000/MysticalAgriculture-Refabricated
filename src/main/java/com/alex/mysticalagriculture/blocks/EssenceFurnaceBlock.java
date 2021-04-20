package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.EssenceFurnaceBlockEntity;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class EssenceFurnaceBlock extends AbstractFurnaceBlock {
    private final FurnaceTier tier;

    public EssenceFurnaceBlock(FurnaceTier tier) {
        super(Settings.copy(Blocks.FURNACE));
        this.tier = tier;
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof EssenceFurnaceBlockEntity) {
            player.openHandledScreen((EssenceFurnaceBlockEntity) tile);
            player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return this.tier.getNewTileEntity();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof EssenceFurnaceBlockEntity) {
                EssenceFurnaceBlockEntity furnace = (EssenceFurnaceBlockEntity) tile;
                ItemScatterer.spawn(world, pos, furnace);
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        double cookingSpeedDifference = 200D * this.tier.getCookTimeMultiplier();
        double cookingSpeedValue = Math.ceil(((200D - cookingSpeedDifference) / cookingSpeedDifference) * 100D) + 100D;
        Text cookingSpeed = new LiteralText(String.valueOf((int) cookingSpeedValue)).append("%");
        double burnTimeDifference = (1600D * this.tier.getBurnTimeMultiplier()) / cookingSpeedDifference;
        double burnTimeValue = Math.ceil(((burnTimeDifference - 8D) / 8D) * 100D) + 100D;
        Text fuelEfficiency = new LiteralText(String.valueOf((int) burnTimeValue)).append("%");

        tooltip.add(ModTooltips.COOKING_SPEED.args(cookingSpeed).build());
        tooltip.add(ModTooltips.FUEL_EFFICIENCY.args(fuelEfficiency).build());
    }

    public enum FurnaceTier {
        INFERIUM("inferium", 0.84D, 0.84D, EssenceFurnaceBlockEntity.Inferium::new),
        PRUDENTIUM("prudentium", 0.625D, 0.84D, EssenceFurnaceBlockEntity.Prudentium::new),
        TERTIUM("tertium", 0.4D, 0.68D, EssenceFurnaceBlockEntity.Tertium::new),
        IMPERIUM("imperium", 0.145D, 0.5D, EssenceFurnaceBlockEntity.Imperium::new),
        SUPREMIUM("supremium", 0.025D, 0.2D, EssenceFurnaceBlockEntity.Supremium::new);

        private final String name;
        private final double cookTimeMultiplier;
        private final double burnTimeMultiplier;
        private final Supplier<EssenceFurnaceBlockEntity> tileEntitySupplier;

        FurnaceTier(String name, double cookTimeMultiplier, double burnTimeMultiplier, Supplier<EssenceFurnaceBlockEntity> tileEntitySupplier) {
            this.name = name;
            this.cookTimeMultiplier = cookTimeMultiplier;
            this.burnTimeMultiplier = burnTimeMultiplier;
            this.tileEntitySupplier = tileEntitySupplier;
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

        public EssenceFurnaceBlockEntity getNewTileEntity() {
            return this.tileEntitySupplier.get();
        }
    }
}
