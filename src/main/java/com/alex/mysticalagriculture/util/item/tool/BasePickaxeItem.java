package com.alex.mysticalagriculture.util.item.tool;

import com.alex.mysticalagriculture.util.iface.Enableable;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Function;

public class BasePickaxeItem extends PickaxeItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BasePickaxeItem(ToolMaterial tier, Function<Settings, Settings> settings) {
        this(tier, 1, -2.8F, settings);
    }

    public BasePickaxeItem(ToolMaterial tier, int attackDamage, float attackSpeed, Function<Settings, Settings> settings) {
        super(tier, attackDamage, attackSpeed, settings.apply(new Settings()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
        if (this instanceof Enableable) {
            Enableable enableable = (Enableable) this;
            if (enableable.isEnabled())
                super.appendStacks(group, items);
        } else {
            super.appendStacks(group, items);
        }
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getMaterial().getAttackDamage();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}
