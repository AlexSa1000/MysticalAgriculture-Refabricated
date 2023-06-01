package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.ReprocessorTier;
import com.alex.mysticalagriculture.cucumber.blockentity.BaseBlockEntity;
import com.alex.mysticalagriculture.cucumber.util.Formatting;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
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

public class ReprocessorBlock extends BaseBlockEntity /*implements BlockEntityProvider*/ {
    private static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    private final ReprocessorTier tier;

    public ReprocessorBlock(ReprocessorTier tier) {
        super(Material.METAL, BlockSoundGroup.METAL, 3.5F, 3.5F, true);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
        this.tier = tier;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return this.tier.createTileEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            var block = world.getBlockEntity(pos);

            if (block instanceof ReprocessorBlockEntity) {
                player.openHandledScreen((NamedScreenHandlerFactory) block);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            var blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof ReprocessorBlockEntity furnace) {
                ItemScatterer.spawn(world, pos, furnace.getInventory().getStacks());
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
        if (Screen.hasShiftDown()) {
            tooltip.add(ModTooltips.MACHINE_SPEED.args(this.getStatText(this.tier.getOperationTime())).build());
            tooltip.add(ModTooltips.MACHINE_FUEL_RATE.args(this.getStatText(this.tier.getFuelUsage())).build());
            tooltip.add(ModTooltips.MACHINE_FUEL_CAPACITY.args(this.getStatText(this.tier.getFuelCapacity())).build());
        } else {
            tooltip.add(ModTooltips.HOLD_SHIFT_FOR_INFO.build());
        }
    }

    @Override
    protected <T extends BlockEntity> BlockEntityTicker getServerTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, this.tier.getBlockEntityType(), ReprocessorBlockEntity::tick);
    }

    private Text getStatText(Object stat) {
        return Formatting.number(stat).formatted(this.tier.getTextColor());
    }
}
