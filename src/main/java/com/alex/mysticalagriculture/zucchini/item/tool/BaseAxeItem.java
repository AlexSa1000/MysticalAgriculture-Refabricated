package com.alex.mysticalagriculture.zucchini.item.tool;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;

import java.util.function.Function;

public class BaseAxeItem extends AxeItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseAxeItem(ToolMaterial tier) {
        this(tier, 6.0F, -3.0F, (p) -> {
            return p;
        });
    }

    public BaseAxeItem(ToolMaterial tier, Function<Settings, Settings> settings) {
        this(tier, 6.0F, -3.0F, settings);
    }

    public BaseAxeItem(ToolMaterial tier, float attackDamage, float attackSpeed, Function<Settings, Settings> settings) {
        super(tier, attackDamage, attackSpeed, settings.apply(new Settings()));
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
