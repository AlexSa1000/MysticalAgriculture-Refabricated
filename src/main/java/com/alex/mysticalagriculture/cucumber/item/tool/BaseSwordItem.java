package com.alex.mysticalagriculture.cucumber.item.tool;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

import java.util.function.Function;

public class BaseSwordItem extends SwordItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseSwordItem(ToolMaterial tier) {
        this(tier, 3, -2.4F, (p) -> {
            return p;
        });
    }

    public BaseSwordItem(ToolMaterial tier, Function<Settings, Settings> properties) {
        this(tier, 3, -2.4F, properties);
    }

    public BaseSwordItem(ToolMaterial tier, int attackDamage, float attackSpeed, Function<Settings, Settings> properties) {
        super(tier, attackDamage, attackSpeed, properties.apply(new Settings()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage + this.getMaterial().getAttackDamage();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}
