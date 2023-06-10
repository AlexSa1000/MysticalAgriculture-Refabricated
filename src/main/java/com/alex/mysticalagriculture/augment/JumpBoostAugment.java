package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.cucumber.helper.ColorHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class JumpBoostAugment extends Augment {
    private final int amplifier;

    public JumpBoostAugment(ResourceLocation id, int tier, int amplifier) {
        super(id, tier, EnumSet.of(AugmentType.BOOTS), getColor(0xFAFAFA, tier), getColor(0x458FAB, tier));
        this.amplifier = amplifier;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.JUMP, 5, this.amplifier, true, false));
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 4, 1));
    }
}
