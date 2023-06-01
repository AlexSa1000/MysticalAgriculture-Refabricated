package com.alex.mysticalagriculture.forge.client.event;

import com.alex.mysticalagriculture.forge.client.event.entity.player.PlayerEvent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;

public class ForgeEventFactory {
    public static boolean doPlayerHarvestCheck(PlayerEntity player, BlockState state, boolean success)
    {
        PlayerEvent.HarvestCheck event = new PlayerEvent.HarvestCheck(player, state, success);
        //MinecraftForge.EVENT_BUS.post(event);
        return event.canHarvest();
    }
}
