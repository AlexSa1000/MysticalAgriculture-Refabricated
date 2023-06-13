package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.AwakeningPedestalBlockEntity;
import com.alex.cucumber.block.BaseEntityBlock;
import com.alex.cucumber.helper.StackHelper;
import com.alex.cucumber.util.VoxelShapeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AwakeningPedestalBlock extends BaseEntityBlock {
    public static final VoxelShape PEDESTAL_SHAPE = VoxelShapeBuilder.fromShapes(
            Shapes.box(0.125, 0, 0.125, 0.3125, 0.125, 0.3125),
            Shapes.box(0.6875, 0, 0.125, 0.875, 0.125, 0.3125),
            Shapes.box(0.125, 0, 0.6875, 0.3125, 0.125, 0.875),
            Shapes.box(0.6875, 0, 0.6875, 0.875, 0.125, 0.875),
            Shapes.box(0.1875, 0.125, 0.1875, 0.8125, 0.25, 0.8125),
            Shapes.box(0.25, 0.25, 0.25, 0.75, 0.875, 0.75),
            Shapes.box(0.1875, 0.875, 0.1875, 0.8125, 1, 0.3125),
            Shapes.box(0.1875, 0.875, 0.6875, 0.8125, 1, 0.8125),
            Shapes.box(0.1875, 0.875, 0.3125, 0.3125, 1, 0.6875),
            Shapes.box(0.6875, 0.875, 0.3125, 0.8125, 1, 0.6875),
            Shapes.box(0.8125, 0.6875, 0.3125, 0.8125, 0.875, 0.6875),
            Shapes.box(0.1875, 0.6875, 0.3125, 0.1875, 0.875, 0.6875),
            Shapes.box(0.3125, 0.6875, 0.1875, 0.6875, 0.875, 0.1875),
            Shapes.box(0.3125, 0.6875, 0.8125, 0.6875, 0.875, 0.8125)
    ).build();

    public AwakeningPedestalBlock() {
        super(Material.STONE, SoundType.STONE, 10.0F, 12.0F, true);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AwakeningPedestalBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        var block = level.getBlockEntity(pos);

        if (block instanceof AwakeningPedestalBlockEntity pedestal) {
            var inventory = pedestal.getInventory();
            var input = inventory.getItem(0);
            var held = player.getItemInHand(hand);

            if (input.isEmpty() && !held.isEmpty()) {
                inventory.setStackInSlot(0, StackHelper.withSize(held, 1, false));
                player.setItemInHand(hand, StackHelper.shrink(held, 1, false));
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if (!input.isEmpty()) {
                inventory.setStackInSlot(0, ItemStack.EMPTY);

                var item = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), input);

                item.setNoPickUpDelay();
                level.addFreshEntity(item);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            var block = level.getBlockEntity(pos);

            if (block instanceof AwakeningPedestalBlockEntity pedestal) {
                Containers.dropContents(level, pos, pedestal.getInventory().getStacks());
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return PEDESTAL_SHAPE;
    }
}