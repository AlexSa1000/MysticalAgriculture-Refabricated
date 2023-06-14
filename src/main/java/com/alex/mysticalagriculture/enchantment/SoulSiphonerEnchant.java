package com.alex.mysticalagriculture.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SoulSiphonerEnchant extends Enchantment {
    private static final EquipmentSlot[] SLOTS = new EquipmentSlot[] { EquipmentSlot.MAINHAND };

    public SoulSiphonerEnchant() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, SLOTS);
    }

    @Override
    public int getMinCost(int level) {
        return 2 + (level - 1) * 11;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}