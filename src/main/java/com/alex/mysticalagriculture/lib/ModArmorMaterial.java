package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.init.Items;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum ModArmorMaterial implements ArmorMaterial {
    INFERIUM("inferium", 40, new int[]{3, 6, 8, 4}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F, 0.0F, () -> {
        return Ingredient.ofItems(Items.INFERIUM_INGOT);
    }),
    PRUDENTIUM("prudentium", 60, new int[]{4, 7, 8, 4}, 14, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.25F, 0.0F, () -> {
        return Ingredient.ofItems(Items.PRUDENTIUM_INGOT);
    }),
    TERTIUM("tertium", 80, new int[]{4, 8, 9, 5}, 16, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.5F, 0.0F, () -> {
        return Ingredient.ofItems(Items.TERTIUM_INGOT);
    }),
    IMPERIUM("imperium", 140, new int[]{5, 8, 9, 5}, 18, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.75F, 0.0F, () -> {
        return Ingredient.ofItems(Items.IMPERIUM_INGOT);
    }),
    SUPREMIUM("supremium", 280, new int[]{5, 8, 10, 6}, 20, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 3.0F, 0.0F, () -> {
        return Ingredient.ofItems(Items.SUPREMIUM_INGOT);
    }),
    AWAKENED_SUPREMIUM("mysticalagriculture:awakened_supremium", 320, new int[] { 6, 12, 10, 8 }, 22, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 3.5F, 0.1F, () -> {
        return Ingredient.ofItems(Items.AWAKENED_SUPREMIUM_INGOT);
    });

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final Lazy<Ingredient> repairMaterial;

    ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new Lazy<>(repairMaterial);
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return MAX_DAMAGE_ARRAY[slot.getEntitySlotId()] * this.maxDamageFactor;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return this.damageReductionAmountArray[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
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
