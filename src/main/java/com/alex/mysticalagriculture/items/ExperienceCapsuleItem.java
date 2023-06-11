package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.cucumber.item.BaseItem;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class ExperienceCapsuleItem extends BaseItem {
    public ExperienceCapsuleItem(Function<Properties, Properties> properties) {
        super(properties.compose(p -> p.stacksTo(1)));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        int experience = ExperienceCapsuleUtils.getExperience(stack);
        tooltip.add(ModTooltips.EXPERIENCE_CAPSULE.args(experience, ExperienceCapsuleUtils.MAX_XP_POINTS).build());
    }

    @Environment(value=EnvType.CLIENT)
    public static ClampedItemPropertyFunction getFillPropertyGetter() {
        return new ClampedItemPropertyFunction() {
            @Override
            public float call(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                return this.unclampedCall(itemStack, clientLevel, livingEntity, i);
            }

            @Override
            public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                int experience = ExperienceCapsuleUtils.getExperience(itemStack);

                if (experience > 0) {
                    double level = (double) experience / ExperienceCapsuleUtils.MAX_XP_POINTS;
                    return (int) (level * 10);
                }

                return 0;
            }
        };
    }
}
