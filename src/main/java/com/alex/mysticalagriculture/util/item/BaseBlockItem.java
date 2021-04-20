package com.alex.mysticalagriculture.util.item;

import com.alex.mysticalagriculture.util.iface.Enableable;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Function;

public class BaseBlockItem extends BlockItem implements Enableable {
    public BaseBlockItem(Block block, Function<Settings, Settings> properties) {
        super(block, properties.apply(new Settings()));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
        if (this.getBlock() instanceof Enableable) {
            Enableable enableable = (Enableable) this.getBlock();
            if (enableable.isEnabled())
                super.appendStacks(group, items);
        } else {
            super.appendStacks(group, items);
        }
    }

    @Override
    public boolean isEnabled() {
        if (this.getBlock() instanceof Enableable)
            return ((Enableable) this.getBlock()).isEnabled();

        return true;
    }
}
