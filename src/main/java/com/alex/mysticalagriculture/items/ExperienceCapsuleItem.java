package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.item.BaseItem;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExperienceCapsuleItem extends BaseItem {
    public ExperienceCapsuleItem() {
        super(p -> p.maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int experience = ExperienceCapsuleUtils.getExperience(stack);
        tooltip.add(ModTooltips.EXPERIENCE_CAPSULE.args(experience, ExperienceCapsuleUtils.MAX_XP_POINTS).build());
    }

    public static ClampedModelPredicateProvider getFillPropertyGetter() {
        return new ClampedModelPredicateProvider() {
            @Override
            public float call(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i) {
                return this.unclampedCall(itemStack, clientWorld, livingEntity, i);
            }

            @Override
            public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
                int experience = ExperienceCapsuleUtils.getExperience(stack);

                if (experience > 0) {
                    double level = (double) experience / ExperienceCapsuleUtils.MAX_XP_POINTS;
                    return (int) (level * 10);
                }
                return 0;
            }
        };
    }
}
