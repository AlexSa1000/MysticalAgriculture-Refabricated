package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class FertilizedEssenceItem extends BoneMealItem {
    public FertilizedEssenceItem() {
        super(new Item.Settings());

        DispenserBlock.registerBehavior(this, new DispenserBehavior());
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        /*World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(context.getSide());
        if (applyFertilizer(context.getStack(), world, blockPos)) {
            if (!world.isClient) {
                world.syncWorldEvent(2005, blockPos, 0);
            }

            return ActionResult.success(world.isClient);
        } else {
            BlockState blockState = world.getBlockState(blockPos);
            boolean bl = blockState.isSideSolidFullSquare(world, blockPos, context.getSide());
            if (bl && useOnGround(context.getStack(), world, blockPos2, context.getSide())) {
                if (!world.isClient) {
                    world.syncWorldEvent(2005, blockPos2, 0);
                }

                return ActionResult.success(world.isClient);
            } else {
                return ActionResult.PASS;
            }
        }*/
        var stack = context.getStack();
        var pos = context.getBlockPos();
        var player = context.getPlayer();
        var world = context.getWorld();
        var direction = context.getSide();

        if (player == null || !player.canPlaceOn(pos.offset(direction), direction, stack)) {
            return ActionResult.FAIL;
        } else {
            if (applyFertilizer(stack, world, pos)) {
                if (!world.isClient()){
                    world.syncWorldEvent(1505, pos, 0);
                }

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int chance = (int) (ModConfigs.FERTILIZED_ESSENCE_DROP_CHANCE.get() * 100);
        tooltip.add(ModTooltips.FERTILIZED_ESSENCE_CHANCE.args(chance + "%").build());
    }

    public static boolean applyFertilizer(ItemStack stack, World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof Fertilizable) {
            Fertilizable growable = (Fertilizable) block;
            if (growable.isFertilizable(world, pos, state, world.isClient())) {
                if (!world.isClient()) {
                    var rand = world.getRandom();
                    if (growable.canGrow(world, rand, pos, state) || canGrowRepointerCrops(growable)) {
                        ServerWorld serverWorld = (ServerWorld) world;
                        growable.grow(serverWorld, rand, pos, state);
                    }
                    stack.decrement(1);
                }
                return true;
            }
        }
        return false;
    }

    private static boolean canGrowRepointerCrops(Fertilizable growable) {
        return growable instanceof CropProvider && ((CropProvider) growable).getCrop().getTier().isFertilizable();
    }

    public static class DispenserBehavior extends FallibleItemDispenserBehavior {
        @Override
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            this.setSuccess(true);

            var level = pointer.getWorld();
            var pos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));

            if (FertilizedEssenceItem.applyFertilizer(stack, level, pos)) {
                if (!level.isClient()) {
                    level.syncWorldEvent(2005, pos, 0);
                }
            } else {
                this.setSuccess(false);
            }

            return stack;
        }
    }
}
