package com.alex.mysticalagriculture.zucchini.item.tool;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

import java.util.function.Function;

public class BasePickaxeItem extends PickaxeItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BasePickaxeItem(ToolMaterial tier) {
        this(tier, 1, -2.8F, (p) -> {
            return p;
        });
    }

    public BasePickaxeItem(ToolMaterial tier, Function<Settings, Settings> settings) {
        this(tier, 1, -2.8F, settings);
    }

    public BasePickaxeItem(ToolMaterial tier, int attackDamage, float attackSpeed, Function<Settings, Settings> settings) {
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
