package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.item.BaseItem;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static net.minecraft.block.SaplingBlock.STAGE;
import static net.minecraft.item.BoneMealItem.useOnGround;

public class MysticalFertilizerItem extends BaseItem {

    public MysticalFertilizerItem(Function<Settings, Settings> settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
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
        }
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
                    Random rand = world.getRandom();
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
}
