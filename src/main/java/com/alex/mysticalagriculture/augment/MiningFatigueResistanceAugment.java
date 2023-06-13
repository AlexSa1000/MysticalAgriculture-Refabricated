package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class MiningFatigueResistanceAugment extends Augment {
    public MiningFatigueResistanceAugment(ResourceLocation id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.ARMOR), 0xADB4E4, 0x101534);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
            player.removeEffect(MobEffects.DIG_SLOWDOWN);
        }
    }
}
