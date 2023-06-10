package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.cucumber.block.BaseEntityBlock;
import com.alex.cucumber.lib.Tooltips;
import com.alex.cucumber.util.Formatting;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.ReprocessorTier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.util.List;

public class ReprocessorBlock extends BaseEntityBlock {
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private final ReprocessorTier tier;

    public ReprocessorBlock(ReprocessorTier tier) {
        super(Material.METAL, SoundType.METAL, 3.5F, 3.5F, true);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
        this.tier = tier;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return this.tier.createTileEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide()) {
            var tile = world.getBlockEntity(pos);

            if (tile instanceof ReprocessorBlockEntity reprocessor) {
                //NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) tile, pos);
                player.openMenu(reprocessor);

            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            var tile = world.getBlockEntity(pos);

            if (tile instanceof ReprocessorBlockEntity furnace) {
                Containers.dropContents(world, pos, furnace.getInventory().getStacks());
            }
        }

        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter world, List<Component> tooltip, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            tooltip.add(ModTooltips.MACHINE_SPEED.args(this.getStatText(this.tier.getOperationTime())).build());
            tooltip.add(ModTooltips.MACHINE_FUEL_RATE.args(this.getStatText(this.tier.getFuelUsage())).build());
            tooltip.add(ModTooltips.MACHINE_FUEL_CAPACITY.args(this.getStatText(this.tier.getFuelCapacity())).build());
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT_FOR_INFO.build());
        }
    }

    @Override
    protected <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(type, this.tier.getBlockEntityType(), ReprocessorBlockEntity::tick);
    }

    private Component getStatText(Object stat) {
        return Formatting.number(stat).withStyle(this.tier.getTextColor());
    }
}
