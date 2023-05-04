package com.alex.mysticalagriculture.zucchini.item;

import net.minecraft.item.Item;

import java.util.function.Function;

public class BaseItem extends Item {
    public BaseItem() {
        super(new Settings());
    }

    public BaseItem(Function<Settings, Settings> properties) {
        super((Settings)properties.apply(new Settings()));
    }
}
