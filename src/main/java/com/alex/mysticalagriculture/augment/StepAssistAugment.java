package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.lib.AbilityCache;
import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.EnumSet;

public class StepAssistAugment extends Augment {
    public StepAssistAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.LEGGINGS, AugmentType.BOOTS), 0xFC4F00, 0x602600);
    }

    @Override
    public void onPlayerTick(World world, PlayerEntity player, AbilityCache cache) {
        if (player.stepHeight < 1.0625F || !cache.isCached(this, player)) {
            player.stepHeight = 1.0625F;

            cache.add(this, player, () -> player.stepHeight = 0.6F);
        }
    }
}
