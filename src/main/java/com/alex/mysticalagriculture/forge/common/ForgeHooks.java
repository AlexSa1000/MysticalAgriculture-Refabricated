package com.alex.mysticalagriculture.forge.common;

import com.alex.mysticalagriculture.api.util.AugmentUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ForgeHooks {

    public static float[] onLivingFall(LivingEntity entity, float distance, float damageMultiplier)
    {
        LivingFallEvent event = new LivingFallEvent(entity, distance, damageMultiplier);

        if (entity instanceof PlayerEntity player) {
            var world = player.getEntityWorld();

            AugmentUtils.getArmorAugments(player).forEach(a -> {
                a.onPlayerFall(world, player, event);
            });
        }

        return new float[]{event.getDistance(), event.getDamageMultiplier()};
    }

    public static class LivingFallEvent
    {
        private final LivingEntity livingEntity;
        private float distance;
        private float damageMultiplier;
        public LivingFallEvent(LivingEntity entity, float distance, float damageMultiplier)
        {
            this.livingEntity = entity;
            this.setDistance(distance);
            this.setDamageMultiplier(damageMultiplier);
        }
        public LivingEntity getEntity()
        {
            return livingEntity;
        }
        public float getDistance() { return distance; }
        public void setDistance(float distance) { this.distance = distance; }
        public float getDamageMultiplier() { return damageMultiplier; }
        public void setDamageMultiplier(float damageMultiplier) { this.damageMultiplier = damageMultiplier; }
    }
}
