package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.util.BaseReusableItem;

import java.util.function.Function;

public class InfusionCrystalItem extends BaseReusableItem {

    public InfusionCrystalItem(Function<Settings, Settings> properties) {
        super(1000, properties);
    }
}
