package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class FertilizedEssenceItem extends BoneMealItem {
    public FertilizedEssenceItem(Properties properties) {
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
                if (!level.isClientSide()){
                    level.levelEvent(1505, pos, 0);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        int chance = (int) (ModConfigs.FERTILIZED_ESSENCE_DROP_CHANCE.get() * 100);
        tooltip.add(ModTooltips.FERTILIZED_ESSENCE_CHANCE.args(chance + "%").build());
    }


    public static boolean applyFertilizer(ItemStack stack, Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof BonemealableBlock growable) {
            if (growable.isValidBonemealTarget(level, pos, state, level.isClientSide())) {
                if (!level.isClientSide()) {
                    var random = level.getRandom();
                    if (growable.isBonemealSuccess(level, random, pos, state) || canGrowRepointerCrops(growable)) {
                        growable.performBonemeal((ServerLevel) level, random, pos, state);

                    }
                    stack.shrink(1);
                }
                return true;
            }
        }
        return false;
    }

    private static boolean canGrowRepointerCrops(BonemealableBlock growable) {
        return growable instanceof CropProvider && ((CropProvider) growable).getCrop().getTier().isFertilizable();
    }

    public static class DispenserBehavior extends OptionalDispenseItemBehavior {
        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            this.setSuccess(true);

            var level = source.getLevel();
            var pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));

            if (FertilizedEssenceItem.applyFertilizer(stack, level, pos)) {
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
