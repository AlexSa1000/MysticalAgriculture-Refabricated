package com.alex.mysticalagriculture.items;

import com.alex.cucumber.item.BaseItem;
import com.alex.mysticalagriculture.util.IActivatable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

import java.util.function.Function;

public class WandItem extends BaseItem {
    public WandItem(Function<Properties, Properties> settings) {
        super(settings.compose(p -> p.stacksTo(1)));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();

        var tile = level.getBlockEntity(pos);
        if (tile instanceof IActivatable activatable) {
            activatable.activate();

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
