package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.EnumSet;

public class WitherResistanceAugment extends Augment {
    public WitherResistanceAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.ARMOR), 0x764857, 0x1A1616);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.WITHER)) {
            player.removeStatusEffect(StatusEffects.WITHER);
        }
    }
}
