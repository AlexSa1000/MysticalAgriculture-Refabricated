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

public class JumpBoostAugment extends Augment {
    private final int amplifier;

    public JumpBoostAugment(Identifier id, int tier, int amplifier) {
        super(id, tier, EnumSet.of(AugmentType.BOOTS), getColor(0xFAFAFA, tier), getColor(0x458FAB, tier));
        this.amplifier = amplifier;
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 5, this.amplifier, true, false));
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 4, 1));
    }
}
