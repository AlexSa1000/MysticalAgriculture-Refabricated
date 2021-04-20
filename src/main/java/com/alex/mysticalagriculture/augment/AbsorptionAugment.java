package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentType;
import com.alex.mysticalagriculture.util.helper.ColorHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.EnumSet;

public class AbsorptionAugment extends Augment {
    private final int amplifier;

    public AbsorptionAugment(Identifier id, int tier, int amplifier) {
        super(id, tier, EnumSet.of(AugmentType.ARMOR), getColor(0x7E95A5, tier), getColor(0x3AAAC4, tier));
        this.amplifier = amplifier;
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (player.getStatusEffect(StatusEffects.ABSORPTION) == null) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 9600, this.amplifier, true, false));
        }
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 5, 1));
    }
}
