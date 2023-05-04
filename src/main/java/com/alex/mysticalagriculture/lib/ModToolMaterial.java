package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.init.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum ModToolMaterial implements ToolMaterial {

    INFERIUM(3, 2000, 9.0F, 4.0F, 12, () -> {
        return Ingredient.ofItems(Items.INFERIUM_INGOT);
    }),
    PRUDENTIUM(3, 2800, 11.0F, 6.0F, 14, () -> {
        return Ingredient.ofItems(Items.PRUDENTIUM_INGOT);
    }),
    TERTIUM(4, 4000, 14.0F, 9.0F, 16, () -> {
        return Ingredient.ofItems(Items.TERTIUM_INGOT);
    }),
    IMPERIUM(4, 6000, 19.0F, 13.0F, 18, () -> {
        return Ingredient.ofItems(Items.IMPERIUM_INGOT);
    }),
    SUPREMIUM(5, -1, 25.0F, 20.0F, 20, () -> {
        return Ingredient.ofItems(Items.SUPREMIUM_INGOT);
    }),
    SOULIUM(0, 400, 5.0F, 3.0F, 15, () -> {
        return Ingredient.ofItems(Items.SOULIUM_INGOT);
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairMaterial;

    ModToolMaterial(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new Lazy<>(repairMaterial);
    }

    @Override
    public int getDurability() {
        return this.maxUses;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
