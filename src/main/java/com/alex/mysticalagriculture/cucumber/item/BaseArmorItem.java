package com.alex.mysticalagriculture.cucumber.item;

import com.alex.mysticalagriculture.cucumber.iface.Enableable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Function;

public class BaseArmorItem extends ArmorItem {
    public BaseArmorItem(ArmorMaterial material, EquipmentSlot slot, Function<Settings, Settings> properties) {
        super(material, slot, properties.apply(new Item.Settings()));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this instanceof Enableable enableable) {
            if (enableable.isEnabled()) {
                super.appendStacks(group, stacks);
            }
        } else {
            super.appendStacks(group, stacks);
        }
    }
}
