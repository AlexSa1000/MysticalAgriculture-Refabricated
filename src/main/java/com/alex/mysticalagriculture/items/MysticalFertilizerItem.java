package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.item.BaseItem;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.client.item.TooltipContext;
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
import java.util.function.Function;

import static net.minecraft.block.SaplingBlock.STAGE;

public class MysticalFertilizerItem extends BaseItem {
    public MysticalFertilizerItem(Function<Settings, Settings> settings) {
        super(settings);

        DispenserBlock.registerBehavior(this, new DispenserBehavior());
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
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
        tooltip.add(ModTooltips.MYSTICAL_FERTILIZER.build());
    }

    public static boolean applyFertilizer(ItemStack stack, World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof Fertilizable) {
            Fertilizable growable = (Fertilizable) block;
            if (growable.isFertilizable(world, pos, state, world.isClient())) {
                if (!world.isClient()) {
                    var rand = world.getRandom();
                    if (growable.canGrow(world, rand, pos, state) || canGrowResourceCrops(growable) || growable instanceof SaplingBlock) {
                        ServerWorld serverWorld = (ServerWorld) world;
                        if (growable instanceof CropBlock) {
                            CropBlock crop = (CropBlock) block;
                            world.setBlockState(pos, crop.withAge(crop.getMaxAge()), 2);
                        } else if (growable instanceof SaplingBlock) {
                            ((SaplingBlock) growable).generate(serverWorld, pos, state.with(STAGE, 1), rand);
                        } else {
                            growable.grow(serverWorld, rand, pos, state);
                        }
                    }
                    stack.decrement(1);
                }
                return true;
            }
        }
        return false;
    }

    private static boolean canGrowResourceCrops(Fertilizable growable) {
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
