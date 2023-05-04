package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.lib.AbilityCache;
import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
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
        var abilities = player.getAbilities();

        if (!abilities.allowFlying || !cache.isCached(this, player)) {
            abilities.allowFlying = true;

            cache.add(this, player, () -> {
                if (!abilities.creativeMode && !player.isSpectator()) {
                    abilities.allowFlying = false;
                    abilities.flying = false;
                }
            });
        }
    }
}
