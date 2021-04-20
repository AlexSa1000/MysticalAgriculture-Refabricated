package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.EnumSet;

public class WaterBreathingAugment extends Augment {
    public WaterBreathingAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.HELMET), 0xF2FFFF, 0x5AAFCF);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 5, 0, true, false));
    }
}
