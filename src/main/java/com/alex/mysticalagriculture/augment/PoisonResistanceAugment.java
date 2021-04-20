package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.EnumSet;

public class PoisonResistanceAugment extends Augment {
    public PoisonResistanceAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.ARMOR), 0x76DB4C, 0x364B15);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        player.removeStatusEffect(StatusEffects.POISON);
    }
}
