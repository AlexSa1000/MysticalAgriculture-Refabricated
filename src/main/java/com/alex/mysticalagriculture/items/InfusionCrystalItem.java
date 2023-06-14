package com.alex.mysticalagriculture.items;

import com.alex.cucumber.item.BaseReusableItem;
import com.alex.mysticalagriculture.config.ModConfigs;
import net.minecraft.world.item.ItemStack;

public class InfusionCrystalItem extends BaseReusableItem {

    public InfusionCrystalItem() {
        super(1000);
    }

    @Override
    public int getMaxDamage() {
        return ModConfigs.INFUSION_CRYSTAL_USES.get() - 1;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }
}
