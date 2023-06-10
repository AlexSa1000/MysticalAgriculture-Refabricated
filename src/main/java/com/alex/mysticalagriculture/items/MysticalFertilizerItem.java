package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.cucumber.item.BaseItem;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SaplingBlock;

import java.util.List;
import java.util.function.Function;

public class MysticalFertilizerItem extends BaseItem {
    public MysticalFertilizerItem(Function<Properties, Properties> properties) {
        super(properties);

        DispenserBlock.registerBehavior(this, new DispenserBehavior());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var stack = context.getItemInHand();
        var pos = context.getClickedPos();
        var player = context.getPlayer();
        var level = context.getLevel();
        var direction = context.getClickedFace();

        if (player == null || !player.mayUseItemAt(pos.relative(direction), direction, stack)) {
            return InteractionResult.FAIL;
        } else {
            if (applyFertilizer(stack, level, pos)) {
                if (!level.isClientSide()) {
                    level.levelEvent(1505, pos, 0);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(ModTooltips.MYSTICAL_FERTILIZER.build());
    }

    public static boolean applyFertilizer(ItemStack stack, Level level, BlockPos pos) {
        var state = level.getBlockState(pos);

        var block = state.getBlock();

        if (block instanceof BonemealableBlock growable && growable.isValidBonemealTarget(level, pos, state, level.isClientSide())) {
            if (!level.isClientSide()) {
                var rand = level.getRandom();

                if (growable.isBonemealSuccess(level, rand, pos, state) || canGrowResourceCrops(growable) || growable instanceof SaplingBlock) {
                    var serverWorld = (ServerLevel) level;

                    if (growable instanceof CropBlock crop) {
                        level.setBlock(pos, crop.getStateForAge(crop.getMaxAge()), 2);
                    } else if (growable instanceof SaplingBlock sapling) {
                        sapling.advanceTree(serverWorld, pos, state.setValue(SaplingBlock.STAGE, 1), rand);
                    } else {
                        growable.performBonemeal(serverWorld, rand, pos, state);
                    }
                }

                stack.shrink(1);
            }

            return true;
        }

        return false;
    }

    private static boolean canGrowResourceCrops(BonemealableBlock growable) {
        return growable instanceof CropProvider cropGetter && cropGetter.getCrop().getTier().isFertilizable();
    }

    public static class DispenserBehavior extends OptionalDispenseItemBehavior {
        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            this.setSuccess(true);

            var level = source.getLevel();
            var pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));

            if (MysticalFertilizerItem.applyFertilizer(stack, level, pos)) {
                if (!level.isClientSide()) {
                    level.levelEvent(2005, pos, 0);
                }
            } else {
                this.setSuccess(false);
            }

            return stack;
        }
    }
}
