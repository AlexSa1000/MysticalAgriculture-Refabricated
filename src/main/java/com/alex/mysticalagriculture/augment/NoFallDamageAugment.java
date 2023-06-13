package com.alex.mysticalagriculture.augment;

import com.alex.cucumber.forge.event.entity.living.LivingFallEvent;
import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class NoFallDamageAugment extends Augment {
    public NoFallDamageAugment(ResourceLocation id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.BOOTS), 0x0092F4, 0x004272);
    }

    @Override
    public void onPlayerFall(Level world, Player player, LivingFallEvent event) {
        event.setDamageMultiplier(0);
    }
}
