package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.TinkeringTableBlockEntity;
import com.alex.mysticalagriculture.util.blockentity.BaseBlockEntity;
import com.alex.mysticalagriculture.util.util.VoxelShapeBuilder;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TinkeringTableBlock extends BaseBlockEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final VoxelShape TABLE_SHAPE = new VoxelShapeBuilder()
            .cuboid(15, 4, 15, 1, 0, 1).cuboid(12.5, 9, 12.5, 3.5, 5, 3.5)
            .cuboid(16, 13, 16, 0, 10, 0).cuboid(13, 13.8, 15.8, 3, 12.8, 14.8)
            .cuboid(15.8, 13.8, 13, 14.8, 12.8, 3).cuboid(13, 13.8, 1.2, 3, 12.8, 0.2)
            .cuboid(15, 14, 15, 1, 13, 1).cuboid(1.2, 13.8, 13, 0.2, 12.8, 3)
            .cuboid(13.5, 5, 13.5, 2.5, 4, 2.5).cuboid(13.5, 10, 13.5, 2.5, 9, 2.5)
            .cuboid(1, 3, 3, 0, 0, 0).cuboid(1, 3, 16, 0, 0, 13)
            .cuboid(16, 3, 3, 15, 0, 0).cuboid(16, 3, 16, 15, 0, 13)
            .cuboid(3, 3, 1, 1, 0, 0).cuboid(15, 3, 1, 13, 0, 0)
            .cuboid(3, 3, 16, 1, 0, 15).cuboid(15, 3, 16, 13, 0, 15)
            .build();

    public TinkeringTableBlock() {
        super(Material.STONE, BlockSoundGroup.STONE, 10.0F, 12.0F, FabricToolTags.PICKAXES);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TinkeringTableBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            BlockEntity block = world.getBlockEntity(pos);
            if (block instanceof TinkeringTableBlockEntity) {
                player.openHandledScreen((NamedScreenHandlerFactory) block);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TinkeringTableBlockEntity) {
                TinkeringTableBlockEntity table = (TinkeringTableBlockEntity) tile;
                ItemScatterer.spawn(world, pos, table.getStacks());
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return TABLE_SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing());
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
}
