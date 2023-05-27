package com.alex.mysticalagriculture.cucumber.item.tool;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

import java.util.function.Function;

public class BaseHoeItem extends HoeItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseHoeItem(ToolMaterial tier) {
        this(tier, -1, -2.0F, p -> p);
    }

    public BaseHoeItem(ToolMaterial tier, int attackDamage, float attackSpeed, Function<Settings, Settings> settings) {
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
