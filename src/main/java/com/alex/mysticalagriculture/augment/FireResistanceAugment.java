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

public class FireResistanceAugment extends Augment {
    public FireResistanceAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.ARMOR), 0xFFAA3F, 0x623E4E);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 5, 0, true, false));

        if (player.isOnFire())
            player.extinguish();
    }
}
