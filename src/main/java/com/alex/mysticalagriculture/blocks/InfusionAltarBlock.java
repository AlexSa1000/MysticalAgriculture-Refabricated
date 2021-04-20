package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.blockentities.InfusionAltarBlockEntity;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.blockentity.BaseBlockEntity;
import com.alex.mysticalagriculture.util.helper.StackHelper;
import com.alex.mysticalagriculture.util.util.VoxelShapeBuilder;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
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

public class InfusionAltarBlock extends BaseBlockEntity implements BlockEntityProvider {

    public static final VoxelShape ALTAR_SHAPE = new VoxelShapeBuilder()
            .cuboid(16, 8, 16, 0, 0, 0).cuboid(13, 14, 13, 3, 13, 3)
            .cuboid(10, 11, 10, 6, 10, 6).cuboid(11, 13, 11, 5, 11, 5)
            .cuboid(3, 14.5, 12, 2, 13.5, 4).cuboid(14, 14.5, 12, 13, 13.5, 4)
            .cuboid(12, 14.5, 3, 4, 13.5, 2).cuboid(12, 14.5, 14, 4, 13.5, 13)
            .cuboid(4, 10, 12, 0.5, 8, 4).cuboid(14, 10, 4, 2, 8, 0.5)
            .cuboid(15.5, 10, 12, 12, 8, 4).cuboid(14, 10, 15.5, 2, 8, 12)
            .cuboid(15.5, 10, 4, 14, 8, 2).cuboid(15.5, 10, 14, 14, 8, 12)
            .cuboid(2, 10, 14, 0.5, 8, 12).cuboid(2, 10, 4, 0.5, 8, 2)
            .cuboid(2, 9, 15.75, 0.25, 8, 14).cuboid(15.75, 9, 15.75, 14, 8, 14)
            .cuboid(15.75, 9, 2, 14, 8, 0.25).cuboid(2, 9, 2, 0.25, 8, 0.25)
            .cuboid(11, 10, 11, 5, 8, 5).build();

    public InfusionAltarBlock() {
        super(Material.STONE, BlockSoundGroup.STONE, 10.0F, 12.0F, FabricToolTags.PICKAXES);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new InfusionAltarBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity block = world.getBlockEntity(pos);

        if (block instanceof InfusionAltarBlockEntity) {
            InfusionAltarBlockEntity altar = (InfusionAltarBlockEntity) block;
            ItemStack input = altar.getStack(0);
            ItemStack output = altar.getStack(1);
            //Remove altar output
            if(!output.isEmpty()) {
                ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), output);
                item.resetPickupDelay();
                world.spawnEntity(item);
                altar.setStack(1, ItemStack.EMPTY);
            } else {
                ItemStack held = player.getStackInHand(hand);
                //Insert altar input
                if (input.isEmpty() && !held.isEmpty()) {
                    altar.setStack(0, StackHelper.withSize(held, 1, false));
                    player.setStackInHand(hand, StackHelper.shrink(held, 1, false));
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                //Remove altar input (not crafted)
                } else if (!input.isEmpty()){
                    ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), input);
                    item.resetPickupDelay();
                    world.spawnEntity(item);
                    altar.setStack(0, ItemStack.EMPTY);
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity block = world.getBlockEntity(pos);
            if (block instanceof InfusionAltarBlockEntity) {
                InfusionAltarBlockEntity altar = (InfusionAltarBlockEntity) block;
                ItemScatterer.spawn(world, pos, altar.getStacks());
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
}
