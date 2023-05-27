package com.alex.mysticalagriculture.cucumber.item;

import com.alex.mysticalagriculture.cucumber.iface.Enableable;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import java.util.function.Function;

public class BaseBlockItem extends BlockItem implements Enableable {

    public BaseBlockItem(Block block) {
        super(block, new Settings());
    }
    public BaseBlockItem(Block block, Function<Settings, Settings> properties) {
        super(block, properties.apply(new Settings()));
    }

    @Override
    public boolean isEnabled() {
        if (this.getBlock() instanceof Enableable)
            return ((Enableable) this.getBlock()).isEnabled();

        return true;
    }
}
