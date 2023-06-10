package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.cucumber.helper.ColorHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;
import java.util.Map;

public class PathingAOEAugment extends Augment {
    private static final Map<Block, BlockState> PATH_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState()));
    private final int range;

    public PathingAOEAugment(ResourceLocation id, int tier, int range) {
        super(id, tier, EnumSet.of(AugmentType.SHOVEL), getColor(0xAA8D4A, tier), getColor(0x856B3A, tier));
        this.range = range;
    }

    @Override
    public boolean onItemUse(UseOnContext context) {
        var player = context.getPlayer();

        if (player == null)
            return false;

        var stack = context.getItemInHand();
        var world = context.getLevel();
        var pos = context.getClickedPos();
        var direction = context.getClickedFace();
        var hand = context.getHand();

        var playedSound = false;

        if (tryPath(stack, player, world, pos, direction, hand)) {
            world.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);

            playedSound = true;

            if (!player.isCrouching())
                return false;
        }

        if (player.isCrouching()) {
            var positions = BlockPos.betweenClosedStream(pos.offset(-this.range, 0, -this.range), pos.offset(this.range, 0, this.range)).iterator();

            while (positions.hasNext()) {
                var aoePos = positions.next();

                if (tryPath(stack, player, world, aoePos, direction, hand) && !playedSound) {
                    world.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);

                    playedSound = true;
                }
            }
        }

        return true;
    }

    private static boolean tryPath(ItemStack stack, Player player, Level world, BlockPos pos, Direction direction, InteractionHand hand) {
        if (direction != Direction.DOWN && world.isEmptyBlock(pos.above())) {
            var state = PATH_LOOKUP.get(world.getBlockState(pos).getBlock());

            if (state != null) {
                if (!world.isClientSide()) {
                    world.setBlock(pos, state, 11);

                    if (player != null) {
                        stack.hurtAndBreak(1, player, (entity) -> {
                            entity.broadcastBreakEvent(hand);
                        });
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
