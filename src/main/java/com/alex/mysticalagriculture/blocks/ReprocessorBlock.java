package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.blockentity.BaseBlockEntity;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ReprocessorBlock extends BaseBlockEntity implements BlockEntityProvider {
    private static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    private final ReprocessorTier tier;

    public ReprocessorBlock(ReprocessorTier tier) {
        super(Material.METAL, BlockSoundGroup.METAL, 3.5F, 3.5F, FabricToolTags.PICKAXES);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
        this.tier = tier;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            BlockEntity block = world.getBlockEntity(pos);
            if (block instanceof ReprocessorBlockEntity) {
                player.openHandledScreen((NamedScreenHandlerFactory) block);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof ReprocessorBlockEntity) {
                ReprocessorBlockEntity furnace = (ReprocessorBlockEntity) tile;
                ItemScatterer.spawn(world, pos, furnace.getStacks());
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(ModTooltips.REPROCESSOR_SPEED.args(this.tier.getOperationTime()).build());
        tooltip.add(ModTooltips.REPROCESSOR_FUEL_RATE.args(this.tier.getFuelUsage()).build());
        tooltip.add(ModTooltips.REPROCESSOR_FUEL_CAPACITY.args(this.tier.getFuelCapacity()).build());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return this.tier.getNewTileEntity();
    }

    public enum ReprocessorTier {
        BASIC("basic", 200, 1, 1600, ReprocessorBlockEntity.Basic::new),
        INFERIUM("inferium", 100, 2, 6400, ReprocessorBlockEntity.Inferium::new),
        PRUDENTIUM("prudentium", 80, 2, 9600, ReprocessorBlockEntity.Prudentium::new),
        TERTIUM("tertium", 54, 3, 14400, ReprocessorBlockEntity.Tertium::new),
        IMPERIUM("imperium", 20, 7, 20800, ReprocessorBlockEntity.Imperium::new),
        SUPREMIUM("supremium", 5, 26, 28000, ReprocessorBlockEntity.Supremium::new);

        private final String name;
        private final int operationTime;
        private final int fuelUsage;
        private final int fuelCapacity;
        private final Supplier<ReprocessorBlockEntity> tileEntitySupplier;

        ReprocessorTier(String name, int operationTime, int fuelUsage, int fuelCapacity, Supplier<ReprocessorBlockEntity> tileEntitySupplier) {
            this.name = name;
            this.operationTime = operationTime;
            this.fuelUsage = fuelUsage;
            this.fuelCapacity = fuelCapacity;
            this.tileEntitySupplier = tileEntitySupplier;
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

        public ReprocessorBlockEntity getNewTileEntity() {
            return this.tileEntitySupplier.get();
        }
    }
}
