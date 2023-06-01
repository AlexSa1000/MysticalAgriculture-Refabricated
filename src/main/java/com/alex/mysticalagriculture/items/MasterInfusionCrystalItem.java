package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.cucumber.item.BaseReusableItem;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class MasterInfusionCrystalItem extends BaseReusableItem {
    public MasterInfusionCrystalItem(Function<Settings, Settings> settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
