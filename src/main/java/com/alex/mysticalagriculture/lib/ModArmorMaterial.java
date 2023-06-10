package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.init.Items;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterial implements ArmorMaterial {
    INFERIUM("mysticalagriculture:inferium", 40, new int[]{3, 6, 8, 4}, 12, SoundEvents.ARMOR_EQUIP_GOLD, 2.0F, 0.0F, () -> {
        return Ingredient.of(Items.INFERIUM_INGOT);
    }),
    PRUDENTIUM("mysticalagriculture:prudentium", 60, new int[]{4, 7, 8, 4}, 14, SoundEvents.ARMOR_EQUIP_GOLD, 2.25F, 0.0F, () -> {
        return Ingredient.of(Items.PRUDENTIUM_INGOT);
    }),
    TERTIUM("mysticalagriculture:tertium", 80, new int[]{4, 8, 9, 5}, 16, SoundEvents.ARMOR_EQUIP_GOLD, 2.5F, 0.0F, () -> {
        return Ingredient.of(Items.TERTIUM_INGOT);
    }),
    IMPERIUM("mysticalagriculture:imperium", 140, new int[]{5, 8, 9, 5}, 18, SoundEvents.ARMOR_EQUIP_GOLD, 2.75F, 0.0F, () -> {
        return Ingredient.of(Items.IMPERIUM_INGOT);
    }),
    SUPREMIUM("mysticalagriculture:supremium", 280, new int[]{5, 8, 10, 6}, 20, SoundEvents.ARMOR_EQUIP_GOLD, 3.0F, 0.0F, () -> {
        return Ingredient.of(Items.SUPREMIUM_INGOT);
    }),
    AWAKENED_SUPREMIUM("mysticalagriculture:awakened_supremium", 320, new int[] { 6, 12, 10, 8 }, 22, SoundEvents.ARMOR_EQUIP_GOLD, 3.5F, 0.1F, () -> {
        return Ingredient.of(Items.AWAKENED_SUPREMIUM_INGOT);
    });

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return MAX_DAMAGE_ARRAY[slot.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return this.damageReductionAmountArray[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
