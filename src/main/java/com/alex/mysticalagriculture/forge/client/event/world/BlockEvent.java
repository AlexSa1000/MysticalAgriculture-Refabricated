package com.alex.mysticalagriculture.forge.client.event.world;

import com.alex.mysticalagriculture.forge.common.ForgeHooks;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BlockEvent {
    private final WorldAccess world;
    private final BlockPos pos;
    private final BlockState state;
    public BlockEvent(WorldAccess world, BlockPos pos, BlockState state)
    {
        this.pos = pos;
        this.world = world;
        this.state = state;
    }

    public WorldAccess getLevel()
    {
        return world;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public BlockState getState()
    {
        return state;
    }

    public static class BreakEvent extends BlockEvent
    {
        /** Reference to the PlayerEntity who broke the block. If no player is available, use a EntityFakePlayerEntity */
        private final PlayerEntity player;
        private int exp;

        public BreakEvent(World world, BlockPos pos, BlockState state, PlayerEntity player)
        {
            super(world, pos, state);
            this.player = player;

            if (state == null || !ForgeHooks.isCorrectToolForDrops(state, player)) // Handle empty block or player unable to break block scenario
            {
                this.exp = 0;
            }
            else
            {
                int fortuneLevel = EnchantmentHelper.getLevel(Enchantments.FORTUNE, player.getMainHandStack());
                int silkTouchLevel = EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, player.getMainHandStack());
                //TODO
                this.exp = /*state.getExpDrop(world, world.random, pos, fortuneLevel, silkTouchLevel)*/0;
            }
        }

        public PlayerEntity getPlayerEntity()
        {
            return player;
        }

        /**
         * Get the experience dropped by the block after the event has processed
         *
         * @return The experience to drop or 0 if the event was canceled
         */
        public int getExpToDrop()
        {
            return /*this.isCanceled() ? 0 :*/ exp;
        }

        /**
         * Set the amount of experience dropped by the block after the event has processed
         *
         * @param exp 1 or higher to drop experience, else nothing will drop
         */
        public void setExpToDrop(int exp)
        {
            this.exp = exp;
        }
    }
}
