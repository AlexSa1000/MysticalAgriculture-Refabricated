package com.alex.mysticalagriculture.blocks;

import com.alex.cucumber.block.BaseEntityBlock;
import com.alex.cucumber.helper.StackHelper;
import com.alex.cucumber.util.VoxelShapeBuilder;
import com.alex.mysticalagriculture.blockentities.InfusionAltarBlockEntity;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import com.alex.mysticalagriculture.items.WandItem;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfusionAltarBlock extends BaseEntityBlock {

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

    public InfusionAltarBlock() {
        super(SoundType.STONE, 10.0F, 12.0F, true);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InfusionAltarBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {
        var block = level.getBlockEntity(pos);

        if (block instanceof InfusionAltarBlockEntity altar) {
            var inventory = altar.getInventory();
            var input = inventory.getItem(0);
            var output = inventory.getItem(1);

            if (!output.isEmpty()) {
                var item = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), output);

                item.setNoPickUpDelay();
                level.addFreshEntity(item);
                inventory.setStackInSlot(1, ItemStack.EMPTY);
            } else {
                var held = player.getItemInHand(hand);
                if (input.isEmpty() && !held.isEmpty()) {
                    inventory.setStackInSlot(0, StackHelper.withSize(held, 1, false));
                    player.setItemInHand(hand, StackHelper.shrink(held, 1, false));
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else if (!input.isEmpty()) {
                    if (held.getItem() instanceof WandItem)
                        return InteractionResult.PASS;

                    var item = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), input);

                    item.setNoPickUpDelay();
                    level.addFreshEntity(item);
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            var block = world.getBlockEntity(pos);

            if (block instanceof InfusionAltarBlockEntity altar) {
                Containers.dropContents(world, pos, altar.getInventory().getStacks());
            }
        }

        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos post, CollisionContext context) {
        return ALTAR_SHAPE;
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(ModTooltips.ACTIVATE_WITH_REDSTONE.build());
    }

    @Override
    protected <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(type, ModBlockEntities.INFUSION_ALTAR, InfusionAltarBlockEntity::tick);
    }
}