package com.alex.mysticalagriculture.zucchini.item.tool;

import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

import java.util.function.Function;

public class BaseShovelItem extends ShovelItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseShovelItem(ToolMaterial tier) {
        this(tier, 1.5F, -3.0F, (p) -> {
            return p;
        });
    }

    public BaseShovelItem(ToolMaterial tier, Function<Settings, Settings> settings) {
        this(tier, 1.5F, -3.0F, settings);
    }

    public BaseShovelItem(ToolMaterial tier, float attackDamage, float attackSpeed, Function<Settings, Settings> settings) {
        super(tier, attackDamage, attackSpeed, settings.apply(new Settings()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getMaterial().getAttackDamage();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}
