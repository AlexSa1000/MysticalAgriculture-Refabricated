package com.alex.mysticalagriculture.cucumber.item.tool;

import com.alex.mysticalagriculture.cucumber.iface.Enableable;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Function;

public class BaseFishingRodItem  extends FishingRodItem {
    public BaseFishingRodItem(Function<Settings, Settings> settings) {
        super(settings.apply(new Settings()));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this instanceof Enableable enableable) {
            if (enableable.isEnabled())
                super.appendStacks(group, stacks);
        } else {
            super.appendStacks(group, stacks);
        }
    }

    public static UnclampedModelPredicateProvider getCastPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            if (entity == null)
                return 0.0F;

            boolean flag = entity.getMainHandStack() == stack;
            boolean flag1 = entity.getOffHandStack() == stack;
            if (entity.getMainHandStack().getItem() instanceof FishingRodItem) {
                flag1 = false;
            }

            return (flag || flag1) && entity instanceof PlayerEntity player && player.fishHook != null ? 1.0F : 0.0F;
        };
    }
}
