package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.lib.AbilityCache;
import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class FlightAugment extends Augment {
    public FlightAugment(ResourceLocation id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.CHESTPLATE), 0xCBD6D6, 0x556B6B);
    }

    @Override
    public void onPlayerTick(Level world, Player player, AbilityCache cache) {
        var abilities = player.getAbilities();

        if (!abilities.mayfly || !cache.isCached(this, player)) {
            abilities.mayfly = true;

            cache.add(this, player, () -> {
                if (!abilities.instabuild && !player.isSpectator()) {
                    abilities.mayfly = false;
                    abilities.flying = false;
                }
            });
        }
    }
}
