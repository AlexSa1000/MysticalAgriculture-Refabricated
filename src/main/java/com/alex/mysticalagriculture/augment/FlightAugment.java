package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.lib.AbilityCache;
import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.EnumSet;

public class FlightAugment extends Augment {
    public FlightAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.CHESTPLATE), 0xCBD6D6, 0x556B6B);
    }

    @Override
    public void onPlayerTick(World world, PlayerEntity player, AbilityCache cache) {
        if (!player.abilities.allowFlying || !cache.isCached(this, player)) {
            player.abilities.allowFlying = true;

            cache.add(this, player, () -> {
                if (!player.abilities.creativeMode && !player.isSpectator()) {
                    player.abilities.allowFlying = false;
                    player.abilities.flying = false;
                }
            });
        }
    }
}
