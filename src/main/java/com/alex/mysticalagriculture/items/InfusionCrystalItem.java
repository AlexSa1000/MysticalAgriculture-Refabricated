package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.cucumber.item.BaseReusableItem;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class InfusionCrystalItem extends BaseReusableItem {

    public InfusionCrystalItem(Function<Settings, Settings> settings) {
        super(1000, settings);
    }

    @Override
    public int getMaxDamage() {
        return ModConfigs.INFUSION_CRYSTAL_USES.get() - 1;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return false;
    }
}
