package com.alex.mysticalagriculture.util.item;

import com.alex.mysticalagriculture.util.iface.Enableable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Function;

public class BaseArmorItem extends ArmorItem {
    public BaseArmorItem(ArmorMaterial material, EquipmentSlot slot, Function<Settings, Settings> settings) {
        super(material, slot, settings.apply(new Settings()));
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
}
