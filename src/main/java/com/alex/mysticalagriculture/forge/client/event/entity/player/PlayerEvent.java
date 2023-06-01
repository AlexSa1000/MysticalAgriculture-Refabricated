package com.alex.mysticalagriculture.forge.client.event.entity.player;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerEvent {
    private final PlayerEntity player;

    public PlayerEvent(PlayerEntity player)
    {
        //super(player);
        this.player = player;
    }

    public PlayerEntity getEntity()
    {
        return player;
    }
    
    public static class HarvestCheck extends PlayerEvent
    {
        private final BlockState state;
        private boolean success;

        public HarvestCheck(PlayerEntity player, BlockState state, boolean success)
        {
            super(player);
            this.state = state;
            this.success = success;
        }

        public BlockState getTargetBlock() { return this.state; }
        public boolean canHarvest() { return this.success; }
        public void setCanHarvest(boolean success){ this.success = success; }
    }
}
