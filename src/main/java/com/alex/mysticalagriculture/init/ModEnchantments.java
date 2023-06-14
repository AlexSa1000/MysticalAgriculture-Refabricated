package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.enchantment.MysticalEnlightenmentEnchantment;
import com.alex.mysticalagriculture.enchantment.SoulSiphonerEnchant;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final Enchantment MYSTICAL_ENLIGHTENMENT = new MysticalEnlightenmentEnchantment();
    public static final Enchantment SOUL_SIPHONER = new SoulSiphonerEnchant();


    public static void registerEnchantments() {
        Registry.register(BuiltInRegistries.ENCHANTMENT, "mysticalagriculture:mystical_enlightenment", MYSTICAL_ENLIGHTENMENT);
        Registry.register(BuiltInRegistries.ENCHANTMENT, "mysticalagriculture:soul_siphoner", SOUL_SIPHONER);
    }
}
