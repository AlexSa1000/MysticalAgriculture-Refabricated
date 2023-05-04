package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.enchantment.MysticalEnlightenmentEnchantment;
import com.alex.mysticalagriculture.enchantment.SoulSiphonerEnchant;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Enchantments {
    public static final Enchantment MYSTICAL_ENLIGHTENMENT = new MysticalEnlightenmentEnchantment();
    public static final Enchantment SOUL_SIPHONER = new SoulSiphonerEnchant();


    public static void registerEnchantments() {
        Registry.register(Registries.ENCHANTMENT, "mysticalagriculture:mystical_enlightenment", MYSTICAL_ENLIGHTENMENT);
        Registry.register(Registries.ENCHANTMENT, "mysticalagriculture:soul_siphoner", SOUL_SIPHONER);
    }
}
