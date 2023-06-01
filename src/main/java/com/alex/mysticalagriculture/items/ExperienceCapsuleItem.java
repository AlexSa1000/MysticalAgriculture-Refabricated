package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.alex.mysticalagriculture.cucumber.item.BaseItem;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class ExperienceCapsuleItem extends BaseItem {
    public ExperienceCapsuleItem(Function<Settings, Settings> settings) {
        super(settings.compose(p -> p.maxCount(1)));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int experience = ExperienceCapsuleUtils.getExperience(stack);
        tooltip.add(ModTooltips.EXPERIENCE_CAPSULE.args(experience, ExperienceCapsuleUtils.MAX_XP_POINTS).build());
    }

    public static UnclampedModelPredicateProvider getFillPropertyGetter() {
        return new UnclampedModelPredicateProvider() {
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
