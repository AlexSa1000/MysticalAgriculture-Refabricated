package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.HarvesterBlockEntity;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.zucchini.blockentity.BaseBlockEntity;
import com.alex.mysticalagriculture.zucchini.util.Formatting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
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

public class HarvesterBlock extends BaseBlockEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public HarvesterBlock() {
        super(Material.METAL, BlockSoundGroup.METAL, 3.5F, 3.5F, true);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HarvesterBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            var block = world.getBlockEntity(pos);

            if (block instanceof HarvesterBlockEntity extractor) {
                player.openHandledScreen(extractor);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            var tile = world.getBlockEntity(pos);

            if (tile instanceof HarvesterBlockEntity extractor) {
                ItemScatterer.spawn(world, pos, extractor.getInventory().getStacks());
                ItemScatterer.spawn(world, pos, extractor.getUpgradeInventory().getStacks());
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
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
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (Screen.hasShiftDown()) {
            tooltip.add(ModTooltips.MACHINE_SPEED.args(com.alex.mysticalagriculture.zucchini.util.Formatting.number(HarvesterBlockEntity.OPERATION_TIME)).build());
            tooltip.add(ModTooltips.MACHINE_FUEL_RATE.args(com.alex.mysticalagriculture.zucchini.util.Formatting.number(HarvesterBlockEntity.FUEL_USAGE)).build());
            tooltip.add(ModTooltips.MACHINE_FUEL_CAPACITY.args(Formatting.number(HarvesterBlockEntity.FUEL_CAPACITY)).build());
        } else {
            tooltip.add(ModTooltips.HOLD_SHIFT_FOR_INFO.build());
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockEntities.HARVESTER, HarvesterBlockEntity::tick);
    }
}
