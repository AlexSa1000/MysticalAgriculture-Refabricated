package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentType;
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
        player.removeStatusEffect(StatusEffects.WITHER);
    }
}
