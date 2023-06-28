package com.alex.mysticalagriculture.handler;

import com.alex.cucumber.forge.event.entity.living.LivingFallEvent;
import com.alex.mysticalagriculture.api.lib.AbilityCache;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AugmentHandler {
    public static final AbilityCache ABILITY_CACHE = new AbilityCache();

    public static float[] onLivingFall(LivingEntity entity, float distance, float damageMultiplier)
    {
        LivingFallEvent event = new LivingFallEvent(entity, distance, damageMultiplier);

        if (entity instanceof Player player) {
            var world = player.getCommandSenderWorld();

            AugmentUtils.getArmorAugments(player).forEach(a -> {
                a.onPlayerFall(world, player, event);
            });
        }

        return new float[]{event.getDistance(), event.getDamageMultiplier()};
    }
}
