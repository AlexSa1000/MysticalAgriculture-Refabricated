package com.alex.mysticalagriculture.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class MysticalEnlightenmentEnchantment extends Enchantment {
    private static final EquipmentSlot[] SLOTS = new EquipmentSlot[] { EquipmentSlot.MAINHAND };

    public MysticalEnlightenmentEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, SLOTS);
    }

    @Override
    public int getMinPower(int level) {
        return 2 + (level - 1) * 11;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
