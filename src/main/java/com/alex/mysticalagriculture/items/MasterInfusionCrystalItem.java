package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.zucchini.item.BaseReusableItem;
import net.minecraft.item.ItemStack;

public class MasterInfusionCrystalItem extends BaseReusableItem {
    public MasterInfusionCrystalItem() {
        super(0);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
