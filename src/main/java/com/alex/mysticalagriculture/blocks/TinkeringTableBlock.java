package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.TinkeringTableBlockEntity;
import com.alex.mysticalagriculture.cucumber.blockentity.BaseBlockEntity;
import com.alex.mysticalagriculture.cucumber.util.VoxelShapeBuilder;
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

    public static final VoxelShape TABLE_SHAPE = VoxelShapeBuilder.builder()
            .cuboid(1, 0, 1, 15, 4, 15).cuboid(3.5, 5, 3.5, 12.5, 9, 12.5)
            .cuboid(0, 10, 0, 16, 13, 16).cuboid(3, 12.8, 14.8, 13, 13.8, 15.8)
            .cuboid(14.8, 12.8, 3, 15.8, 13.8, 13).cuboid(3, 12.8, 0.2, 13, 13.8, 1.2)
            .cuboid(1, 13, 1, 15, 14, 15).cuboid(0.2, 12.8, 3, 1.2, 13.8, 13)
            .cuboid(2.5, 4, 2.5, 13.5, 5, 13.5).cuboid(2.5, 9, 2.5, 13.5, 10, 13.5)
            .cuboid(0, 0, 0, 1, 3, 3).cuboid(0, 0, 13, 1, 3, 16)
            .cuboid(15, 0, 0, 16, 3, 3).cuboid(15, 0, 13, 16, 3, 16)
            .cuboid(1, 0, 0, 3, 3, 1).cuboid(13, 0, 0, 15, 3, 1)
            .cuboid(1, 0, 15, 3, 3, 16).cuboid(13, 0, 15, 15, 3, 16)
            .build();

    public TinkeringTableBlock() {
        super(Material.STONE, BlockSoundGroup.STONE, 10.0F, 12.0F, true);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TinkeringTableBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            var block = world.getBlockEntity(pos);

            //TODO See this!
            if (block instanceof TinkeringTableBlockEntity) {
                player.openHandledScreen((NamedScreenHandlerFactory) block);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            var tile = world.getBlockEntity(pos);

            if (tile instanceof TinkeringTableBlockEntity table) {

                var stack = table.getInventory().getStack(0);

                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
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
