package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.SoulExtractorBlockEntity;
import com.alex.cucumber.block.BaseEntityBlock;
import com.alex.cucumber.lib.Tooltips;
import com.alex.cucumber.util.Formatting;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import com.alex.mysticalagriculture.lib.ModTooltips;
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

import java.util.List;

public class SoulExtractorBlock extends BaseEntityBlock {
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public SoulExtractorBlock() {
        super(Material.METAL, SoundType.METAL, 3.5F, 3.5F, true);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SoulExtractorBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            var block = level.getBlockEntity(pos);

            if (block instanceof SoulExtractorBlockEntity extractor) {
                //NetworkHooks.openScreen((ServerPlayer) player, extractor, pos);
                player.openMenu(extractor);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            var block = level.getBlockEntity(pos);

            if (block instanceof SoulExtractorBlockEntity extractor) {
                Containers.dropContents(level, pos, extractor.getInventory().getStacks());
                Containers.dropContents(level, pos, extractor.getUpgradeInventory().getStacks());
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
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
    public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            tooltip.add(ModTooltips.MACHINE_SPEED.args(Formatting.number(SoulExtractorBlockEntity.OPERATION_TIME)).build());
            tooltip.add(ModTooltips.MACHINE_FUEL_RATE.args(Formatting.number(SoulExtractorBlockEntity.FUEL_USAGE)).build());
            tooltip.add(ModTooltips.MACHINE_FUEL_CAPACITY.args(Formatting.number(SoulExtractorBlockEntity.FUEL_CAPACITY)).build());
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT_FOR_INFO.build());
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(type, ModBlockEntities.SOUL_EXTRACTOR, SoulExtractorBlockEntity::tick);
    }
}
