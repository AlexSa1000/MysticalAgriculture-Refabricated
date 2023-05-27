package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.util.Activatable;
import com.alex.mysticalagriculture.cucumber.item.BaseItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class WandItem extends BaseItem {
    public WandItem() {
        super(p -> p.maxCount(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        var pos = context.getBlockPos();

        var tile = world.getBlockEntity(pos);
        if (tile instanceof Activatable activatable) {
            activatable.activate();

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
