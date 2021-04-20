package com.alex.mysticalagriculture.util.item.tool;

import com.alex.mysticalagriculture.util.iface.Enableable;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Function;

public class BaseSwordItem extends SwordItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseSwordItem(ToolMaterial tier, Function<Settings, Settings> properties) {
        this(tier, 3, -2.4F, properties);
    }

    public BaseSwordItem(ToolMaterial tier, int attackDamage, float attackSpeed, Function<Settings, Settings> properties) {
        super(tier, attackDamage, attackSpeed, properties.apply(new Settings()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this instanceof Enableable) {
            Enableable enableable = (Enableable) this;
            if (enableable.isEnabled())
                super.appendStacks(group, stacks);
        } else {
            super.appendStacks(group, stacks);
        }
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage + this.getMaterial().getAttackDamage();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}
