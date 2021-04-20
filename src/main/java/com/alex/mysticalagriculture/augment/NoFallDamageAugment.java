package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.EnumSet;

public class NoFallDamageAugment extends Augment {
    public NoFallDamageAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.BOOTS), 0x0092F4, 0x004272);
    }

    @Override
    public void onPlayerFall(World world, PlayerEntity player) {
        player.fallDistance = 0;
    }
}
