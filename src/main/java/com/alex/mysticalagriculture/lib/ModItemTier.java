package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.init.Items;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModItemTier implements Tier {

    INFERIUM(3, 2000, 9.0F, 4.0F, 12, () -> {
        return Ingredient.of(Items.INFERIUM_INGOT);
    }),
    PRUDENTIUM(3, 2800, 11.0F, 6.0F, 14, () -> {
        return Ingredient.of(Items.PRUDENTIUM_INGOT);
    }),
    TERTIUM(4, 4000, 14.0F, 9.0F, 16, () -> {
        return Ingredient.of(Items.TERTIUM_INGOT);
    }),
    IMPERIUM(4, 6000, 19.0F, 13.0F, 18, () -> {
        return Ingredient.of(Items.IMPERIUM_INGOT);
    }),
    SUPREMIUM(5, -1, 25.0F, 20.0F, 20, () -> {
        return Ingredient.of(Items.SUPREMIUM_INGOT);
    }),
    AWAKENED_SUPREMIUM(5, -1, 30.0F, 25.0F, 22, () -> {
        return Ingredient.of(Items.AWAKENED_SUPREMIUM_INGOT);
    }),
    SOULIUM(0, 400, 5.0F, 3.0F, 15, () -> {
        return Ingredient.of(Items.SOULIUM_INGOT);
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
