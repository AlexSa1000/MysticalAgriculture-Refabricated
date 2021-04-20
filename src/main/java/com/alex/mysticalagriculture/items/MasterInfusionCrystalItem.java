package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.util.BaseReusableItem;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class MasterInfusionCrystalItem extends BaseReusableItem {
    public MasterInfusionCrystalItem(Function<Settings, Settings> properties) {
        super(properties);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
