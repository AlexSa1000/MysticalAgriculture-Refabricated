package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.cucumber.item.BaseReusableItem;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class InfusionCrystalItem extends BaseReusableItem {

    public InfusionCrystalItem(Function<Properties, Properties> properties) {
        super(1000, properties);
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
