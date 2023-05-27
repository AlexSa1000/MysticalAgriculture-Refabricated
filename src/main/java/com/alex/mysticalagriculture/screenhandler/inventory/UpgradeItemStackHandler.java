package com.alex.mysticalagriculture.screenhandler.inventory;

import com.alex.mysticalagriculture.items.MachineUpgradeItem;
import com.alex.mysticalagriculture.util.MachineUpgradeTier;
import com.alex.mysticalagriculture.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.item.ItemStack;

public class UpgradeItemStackHandler extends BaseItemStackHandler {
    public UpgradeItemStackHandler() {
        super(1, null);
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        var item = stack.getItem();
        return item instanceof MachineUpgradeItem;
    }

    public MachineUpgradeTier getUpgradeTier() {
        var item = this.getStack(0).getItem();
        if (item instanceof MachineUpgradeItem upgrade)
            return upgrade.getTier();

        return null;
    }
}
