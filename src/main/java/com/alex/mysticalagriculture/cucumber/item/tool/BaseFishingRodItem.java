package com.alex.mysticalagriculture.cucumber.item.tool;

import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;

import java.util.function.Function;

public class BaseFishingRodItem  extends FishingRodItem {
    public BaseFishingRodItem(Function<Settings, Settings> settings) {
        super(settings.apply(new Settings()));
    }

    public static ModelPredicateProvider getCastPropertyGetter() {
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
