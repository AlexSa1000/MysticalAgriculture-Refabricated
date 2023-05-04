package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.mysticalagriculture.zucchini.helper.ColorHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Map;

public class PathingAOEAugment extends Augment {
    private static final Map<Block, BlockState> PATH_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.getDefaultState()));
    private final int range;

    public PathingAOEAugment(Identifier id, int tier, int range) {
        super(id, tier, EnumSet.of(AugmentType.SHOVEL), getColor(0xAA8D4A, tier), getColor(0x856B3A, tier));
        this.range = range;
    }

    @Override
    public boolean onItemUse(ItemUsageContext context) {
        var player = context.getPlayer();
        if (player == null)
            return false;

        var stack = context.getStack();
        var world = context.getWorld();
        var pos = context.getBlockPos();
        var direction = context.getSide();
        var hand = context.getHand();

        var playedSound = false;

        if (tryPath(stack, player, world, pos, direction, hand)) {
            world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

            playedSound = true;

            if (!player.isInSneakingPose())
                return false;
        }

        if (player.isInSneakingPose()) {
            var positions = BlockPos.stream(pos.add(-this.range, 0, -this.range), pos.add(this.range, 0, this.range)).iterator();

            while (positions.hasNext()) {
                var aoePos = positions.next();

                if (tryPath(stack, player, world, aoePos, direction, hand) && !playedSound) {
                    world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    playedSound = true;
                }
            }
        }

        return true;
    }

    private static boolean tryPath(ItemStack stack, PlayerEntity player, World world, BlockPos pos, Direction direction, Hand hand) {
        if (direction != Direction.DOWN && world.isAir(pos.up())) {
            var state = PATH_LOOKUP.get(world.getBlockState(pos).getBlock());

            if (state != null) {
                if (!world.isClient()) {
                    world.setBlockState(pos, state, 11);
                    if (player != null) {
                        stack.damage(1, player, (entity) -> entity.sendToolBreakStatus(hand));
                    }
                }

                return true;
            }
        }

        return false;
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 5, 1));
    }
}
