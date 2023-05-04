package com.alex.mysticalagriculture.zucchini.item;

import net.minecraft.item.*;

import java.util.function.Function;

public class BaseArmorItem extends ArmorItem {
    public BaseArmorItem(ArmorMaterial material, ArmorItem.Type type) {
        super(material, type, new Item.Settings());
    }

    public BaseArmorItem(ArmorMaterial material, ArmorItem.Type type, Function<Item.Settings, Item.Settings> properties) {
        super(material, type, (Item.Settings)properties.apply(new Item.Settings()));
    }
}
