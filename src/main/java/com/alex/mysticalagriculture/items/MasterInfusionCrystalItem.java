package com.alex.mysticalagriculture.items;

import com.alex.cucumber.item.BaseReusableItem;
import net.minecraft.world.item.ItemStack;

public class MasterInfusionCrystalItem extends BaseReusableItem {
    public MasterInfusionCrystalItem() {
        super(0);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
