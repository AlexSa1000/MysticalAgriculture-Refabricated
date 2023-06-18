package com.alex.mysticalagriculture.util;

import com.alex.mysticalagriculture.container.inventory.UpgradeItemStackHandler;
import com.alex.mysticalagriculture.items.MachineUpgradeItem;
import net.minecraft.world.item.ItemStack;

public interface IUpgradeableMachine {

    UpgradeItemStackHandler getUpgradeInventory();

    default MachineUpgradeTier getMachineTier() {
        return getUpgradeInventory().getUpgradeTier();
    }

    default ItemStack applyUpgrade(MachineUpgradeItem item) {
        var inventory = getUpgradeInventory();
        var current = inventory.getItem(0);

        inventory.setStackInSlot(0, new ItemStack(item));

        return current;
    }

    default boolean canApplyUpgrade(MachineUpgradeTier tier) {
        return getMachineTier() == null || getMachineTier().getValue() < tier.getValue();
    }
}
