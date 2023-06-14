package com.alex.mysticalagriculture.container.inventory;

import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.mysticalagriculture.items.MachineUpgradeItem;
import com.alex.mysticalagriculture.util.MachineUpgradeTier;
import net.minecraft.world.item.ItemStack;

public class UpgradeItemStackHandler extends BaseItemStackHandler {
    public UpgradeItemStackHandler() {
        super(1, null);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        var item = stack.getItem();
        return item instanceof MachineUpgradeItem;
    }

    public MachineUpgradeTier getUpgradeTier() {
        var item = this.getItem(0).getItem();
        if (item instanceof MachineUpgradeItem upgrade)
            return upgrade.getTier();

        return null;
    }
}
