package com.alex.mysticalagriculture.util;

import com.alex.mysticalagriculture.items.MachineUpgradeItem;
import com.alex.mysticalagriculture.screenhandler.inventory.UpgradeItemStackHandler;
import net.minecraft.item.ItemStack;

public interface UpgradeableMachine {

    UpgradeItemStackHandler getUpgradeInventory();

    default MachineUpgradeTier getMachineTier() {
        return getUpgradeInventory().getUpgradeTier();
    }

    default ItemStack applyUpgrade(MachineUpgradeItem item) {
        var inventory = getUpgradeInventory();
        var current = inventory.getStack(0);

        inventory.setStackInSlot(0, new ItemStack(item));

        return current;
    }

    default boolean canApplyUpgrade(MachineUpgradeTier tier) {
        return getMachineTier() == null || getMachineTier().getValue() < tier.getValue();
    }
}
