package com.alex.mysticalagriculture.cucumber.item.tool;

import draylar.magna.api.BlockProcessor;
import draylar.magna.api.MagnaTool;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Function;

public class BaseShearsItem extends ShearsItem implements MagnaTool {
    public BaseShearsItem(Function<Settings, Settings> settings) {
        super(settings.apply(new Settings()));
    }


    @Override
    public int getRadius(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean playBreakEffects() {
        return false;
    }

    @Override
    public boolean attemptBreak(World world, BlockPos pos, PlayerEntity player, int breakRadius, BlockProcessor processor) {
        if (world.isClient())
            return false;

        var state = world.getBlockState(pos);
        var block = state.getBlock();

        if (block instanceof Shearable) {
            var tile = world.getBlockEntity(pos);
            var context = (new LootContext.Builder((ServerWorld) world))
                    .random(world.getRandom())
                    .parameter(LootContextParameters.ORIGIN, new Vec3d(pos.getX(), pos.getY(), pos.getZ()))
                    .parameter(LootContextParameters.TOOL, new ItemStack(Items.SHEARS))
                    .optionalParameter(LootContextParameters.THIS_ENTITY, player)
                    .optionalParameter(LootContextParameters.BLOCK_ENTITY, tile);
            var drops = state.getDroppedStacks(context);
            var rand = new Random();

            for (var drop : drops) {
                float f = 0.7F;
                double d = rand.nextFloat() * f + (1D - f) * 0.5;
                double d1 = rand.nextFloat() * f + (1D - f) * 0.5;
                double d2 = rand.nextFloat() * f + (1D - f) * 0.5;

                var item = new ItemEntity(world, pos.getX() + d, pos.getY() + d1, pos.getZ() + d2, drop);

                item.setPickupDelay(10);

                world.spawnEntity(item);
            }

            ItemStack stack = player.getMainHandStack();

            stack.damage(1, player, entity -> {
                entity.sendToolBreakStatus(player.getActiveHand());
            });

            player.increaseStat(Stats.MINED.getOrCreateStat(block), 1);

            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);

            return true;
        }

        return false;
    }
}
