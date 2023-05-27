package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.InfusionPedestalBlockEntity;
import com.alex.mysticalagriculture.cucumber.blockentity.BaseBlockEntity;
import com.alex.mysticalagriculture.cucumber.helper.StackHelper;
import com.alex.mysticalagriculture.cucumber.util.VoxelShapeBuilder;
import net.minecraft.block.BlockEntityProvider;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class InfusionPedestalBlock extends BaseBlockEntity implements BlockEntityProvider {

    public static final VoxelShape PEDESTAL_SHAPE = VoxelShapeBuilder.builder()
            .cuboid(2.0, 0.0, 2.0, 5.0, 2.0, 5.0).cuboid(11.0, 0.0, 2.0, 14.0, 2.0, 5.0)
            .cuboid(2.0, 0.0, 11.0, 5.0, 2.0, 14.0).cuboid(11.0, 0.0, 11.0, 14.0, 2.0, 14.0)
            .cuboid(3.0, 2.0, 3.0, 13.0, 4.0, 13.0).cuboid(4.0, 4.0, 4.0, 12.0, 14.0, 12.0)
            .cuboid(3.0, 14.0, 3.0, 13.0, 16.0, 5.0).cuboid(3.0, 14.0, 11.0, 13.0, 16.0, 13.0)
            .cuboid(3.0, 14.0, 5.0, 5.0, 16.0, 11.0).cuboid(11.0, 14.0, 5.0, 13.0, 16.0, 11.0).build();

    public InfusionPedestalBlock() {
        super(Material.STONE, BlockSoundGroup.STONE, 10.0F, 12.0F, true);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InfusionPedestalBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var block = world.getBlockEntity(pos);

        if (block instanceof InfusionPedestalBlockEntity pedestal) {
            var inventory = pedestal.getInventory();
            var input = inventory.getStack(0);
            var held = player.getStackInHand(hand);

            if (input.isEmpty() && !held.isEmpty()) {
                inventory.setStack(0, StackHelper.withSize(held, 1, false));
                player.setStackInHand(hand, StackHelper.shrink(held, 1, false));
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            } else if (!input.isEmpty()) {
                inventory.setStack(0, ItemStack.EMPTY);

                var item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), input);

                item.resetPickupDelay();
                world.spawnEntity(item);
            }
        }

        return ActionResult.SUCCESS;
        /*var block = world.getBlockEntity(pos);

        if (block instanceof InfusionPedestalBlockEntity pedestal) {

            var input = pedestal.getStack(0);
            var held = player.getStackInHand(hand);

            if (input.isEmpty() && !held.isEmpty()) {
                pedestal.setStack(0, StackHelper.withSize(held, 1, false));
                player.setStackInHand(hand, StackHelper.shrink(held, 1, false));
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            } else if (!input.isEmpty()) {
                pedestal.setStack(0, ItemStack.EMPTY);

                var item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), input);

                item.resetPickupDelay();
                world.spawnEntity(item);
            }
        }

        return ActionResult.SUCCESS;*/
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            var blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof InfusionPedestalBlockEntity pedestal) {
                ItemScatterer.spawn(world, pos, pedestal.getInventory().getStacks());
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return PEDESTAL_SHAPE;
    }
}
