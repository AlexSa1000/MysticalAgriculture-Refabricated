package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.AwakeningAltarBlockEntity;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.blockentity.BaseBlockEntity;
import com.alex.mysticalagriculture.cucumber.helper.StackHelper;
import com.alex.mysticalagriculture.cucumber.util.VoxelShapeBuilder;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AwakeningAltarBlock extends BaseBlockEntity implements BlockEntityProvider {
    public static final VoxelShape ALTAR_SHAPE = VoxelShapeBuilder.builder()
            .cuboid(0, 0, 0, 16, 8, 16).cuboid(3, 13, 3, 13, 14, 13)
            .cuboid(6, 10, 6, 10, 11, 10).cuboid(5, 11, 5, 11, 13, 11)
            .cuboid(2, 13.5, 4, 3, 14.5, 12).cuboid(13, 13.5, 4, 14, 14.5, 12)
            .cuboid(4, 13.5, 2, 12, 14.5, 3).cuboid(4, 13.5, 13, 12, 14.5, 14)
            .cuboid(0.5, 8, 4, 4, 10, 12).cuboid(2, 8, 0.5, 14, 10, 4)
            .cuboid(12, 8, 4, 15.5, 10, 12).cuboid(2, 8, 12, 14, 10, 15.5)
            .cuboid(14, 8, 2, 15.5, 10, 4).cuboid(14, 8, 12, 15.5, 10, 14)
            .cuboid(0.5, 8, 12, 2, 10, 14).cuboid(0.5, 8, 2, 2, 10, 4)
            .cuboid(0.25, 8, 14, 2, 9, 15.75).cuboid(14, 8, 14, 15.75, 9, 15.75)
            .cuboid(14, 8, 0.25, 15.75, 9, 2).cuboid(0.25, 8, 0.25, 2, 9, 2)
            .cuboid(5, 8, 5, 11, 10, 11).build();

    public AwakeningAltarBlock() {
        super(Material.STONE, BlockSoundGroup.STONE, 10.0F, 12.0F, true);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AwakeningAltarBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var block = world.getBlockEntity(pos);

        if (block instanceof AwakeningAltarBlockEntity altar) {
            var inventory = altar.getInventory();
            var input = inventory.getStack(0);
            var output = inventory.getStack(1);

            if (!output.isEmpty()) {
                var item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), output);

                item.resetPickupDelay();
                world.spawnEntity(item);
                inventory.setStack(1, ItemStack.EMPTY);
            } else {
                var held = player.getStackInHand(hand);

                if (input.isEmpty() && !held.isEmpty()) {
                    inventory.setStack(0, StackHelper.withSize(held, 1, false));
                    player.setStackInHand(hand, StackHelper.shrink(held, 1, false));
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);

                } else if (!input.isEmpty()){
                    var item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), input);

                    item.resetPickupDelay();
                    world.spawnEntity(item);
                    inventory.setStack(0, ItemStack.EMPTY);
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            var block = world.getBlockEntity(pos);
            if (block instanceof AwakeningAltarBlockEntity altar) {
                ItemScatterer.spawn(world, pos, altar.getInventory().getStacks());
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ALTAR_SHAPE;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(ModTooltips.ACTIVATE_WITH_REDSTONE.build());
    }

    @Override
    protected <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockEntities.AWAKENING_ALTAR, AwakeningAltarBlockEntity::tick);
    }
}
