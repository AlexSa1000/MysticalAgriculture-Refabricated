package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.EssenceVesselBlockEntity;
import com.alex.mysticalagriculture.cucumber.blockentity.BaseBlockEntity;
import com.alex.mysticalagriculture.cucumber.util.VoxelShapeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EssenceVesselBlock extends BaseBlockEntity {
    public static final VoxelShape VESSEL_SHAPE = VoxelShapeBuilder.fromShapes(
            VoxelShapes.cuboid(0.690625, 0, 0.1875, 0.815625, 0.125, 0.3125),
            VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.3125, 0.125, 0.3125),
            VoxelShapes.cuboid(0.1875, 0, 0.6875, 0.3125, 0.125, 0.8125),
            VoxelShapes.cuboid(0.6875, 0, 0.6875, 0.8125, 0.125, 0.8125),
            VoxelShapes.cuboid(0.1875, 0.71875, 0.1875, 0.8125, 1.15625, 0.8125),
            VoxelShapes.cuboid(0.4375, 0.6875, 0.15625, 0.5625, 1.1875, 0.21875),
            VoxelShapes.cuboid(0.15625, 0.6875, 0.4375, 0.21875, 1.1875, 0.5625),
            VoxelShapes.cuboid(0.78125, 0.6875, 0.4375, 0.84375, 1.1875, 0.5625),
            VoxelShapes.cuboid(0.4375, 0.6875, 0.78125, 0.5625, 1.1875, 0.84375),
            VoxelShapes.cuboid(0.21875, 0.6875, 0.4375, 0.40625, 0.71875, 0.5625),
            VoxelShapes.cuboid(0.59375, 0.6875, 0.4375, 0.78125, 0.71875, 0.5625),
            VoxelShapes.cuboid(0.4375, 0.6875, 0.59375, 0.5625, 0.71875, 0.78125),
            VoxelShapes.cuboid(0.4375, 0.6875, 0.21875, 0.5625, 0.71875, 0.40625),
            VoxelShapes.cuboid(0.3125, 0.625, 0.3125, 0.6875, 0.6875, 0.6875),
            VoxelShapes.cuboid(0.375, 0.15625, 0.375, 0.625, 0.625, 0.625),
            VoxelShapes.cuboid(0.25, 0.125, 0.25, 0.75, 0.1875, 0.75)
    ).build();

    public EssenceVesselBlock() {
        super(Material.STONE, BlockSoundGroup.STONE, 10.0F, 12.0F, true);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EssenceVesselBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var block = world.getBlockEntity(pos);

        if (block instanceof EssenceVesselBlockEntity vessel) {
            var inventory = vessel.getInventory();
            var input = inventory.getStack(0);
            var held = player.getStackInHand(hand);

            var remaining = vessel.insert(held);
            if (held != remaining) {
                player.setStackInHand(hand, remaining);
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            } else if (!input.isEmpty()) {
                inventory.setStack(0, ItemStack.EMPTY);

                var item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), input);

                item.resetPickupDelay();
                world.spawnEntity(item);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            var blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof EssenceVesselBlockEntity vessel) {
                ItemScatterer.spawn(world, pos, vessel.getInventory().getStacks());
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VESSEL_SHAPE;
    }
}
