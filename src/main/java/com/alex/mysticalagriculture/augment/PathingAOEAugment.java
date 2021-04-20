package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentType;
import com.alex.mysticalagriculture.util.helper.ColorHelper;
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
    private static final Map<Block, BlockState> PATH_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));
    private final int range;

    public PathingAOEAugment(Identifier id, int tier, int range) {
        super(id, tier, EnumSet.of(AugmentType.SHOVEL), getColor(0xAA8D4A, tier), getColor(0x856B3A, tier));
        this.range = range;
    }

    @Override
    public boolean onItemUse(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null)
            return false;

        ItemStack stack = context.getStack();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Direction direction = context.getSide();
        Hand hand = context.getHand();

        if (this.tryPath(stack, player, world, pos, direction, hand)) {
            world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if (!player.isInSneakingPose())
                return false;
        }

        if (player.isInSneakingPose()) {
            BlockPos.stream(pos.add(-this.range, 0, -this.range), pos.add(this.range, 0, this.range)).forEach(aoePos -> this.tryPath(stack, player, world, aoePos, direction, hand));
        }

        return true;
    }

    private boolean tryPath(ItemStack stack, PlayerEntity player, World world, BlockPos pos, Direction direction, Hand hand) {
        if (direction != Direction.DOWN && world.isAir(pos.up())) {
            BlockState state = PATH_LOOKUP.get(world.getBlockState(pos).getBlock());
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
