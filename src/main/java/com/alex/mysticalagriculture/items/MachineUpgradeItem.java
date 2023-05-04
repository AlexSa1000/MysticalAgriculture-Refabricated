package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.MachineUpgradeTier;
import com.alex.mysticalagriculture.util.UpgradeableMachine;
import com.alex.mysticalagriculture.zucchini.item.BaseItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.util.List;

public class MachineUpgradeItem extends BaseItem {
    private final MachineUpgradeTier tier;

    public MachineUpgradeItem(MachineUpgradeTier tier) {
        super();
        this.tier = tier;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        var pos = context.getBlockPos();
        var tile = world.getBlockEntity(pos);

        if (tile instanceof UpgradeableMachine machine && machine.canApplyUpgrade(this.tier)) {
            var stack = context.getStack();
            var remaining = machine.applyUpgrade(this);

            stack.decrement(1);

            if (!remaining.isEmpty()) {
                var item = new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), remaining.copy());

                world.spawnEntity(item);
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(ModTooltips.UPGRADE_SPEED.args(this.getStatText(this.tier.getOperationTimeMultiplier())).build());
            tooltip.add(ModTooltips.UPGRADE_FUEL_RATE.args(this.getStatText(this.tier.getFuelUsageMultiplier())).build());
            tooltip.add(ModTooltips.UPGRADE_FUEL_CAPACITY.args(this.getStatText(this.tier.getFuelCapacityMultiplier())).build());
            tooltip.add(ModTooltips.UPGRADE_AREA.args(this.getStatText(this.tier.getAddedRange())).build());
        } else {
            tooltip.add(ModTooltips.HOLD_SHIFT_FOR_INFO.build());
        }
    }

    public MachineUpgradeTier getTier() {
        return this.tier;
    }

    private Text getStatText(Object stat) {
        var number = NumberFormat.getInstance().format(stat);
        return Text.literal(number).formatted(this.tier.getTextColor());
    }
}
