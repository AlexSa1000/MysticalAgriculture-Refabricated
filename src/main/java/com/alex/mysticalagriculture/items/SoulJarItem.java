package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.zucchini.item.BaseItem;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class SoulJarItem extends BaseItem {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public SoulJarItem() {
        super(p -> p.maxCount(1));
    }

    /*@Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
        if (this.isIn(group)) {
            items.add(new ItemStack(this));

            ModMobSoulTypes.getMobSoulTypes().forEach(type -> {
                if (type.isEnabled()) {
                    items.add(MobSoulUtils.getFilledSoulJar(type, this));
                }
            });
        }
    }*/

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        var type = MobSoulUtils.getType(stack);

        if (type != null) {
            var entityName = type.getEntityDisplayName();
            var souls = DECIMAL_FORMAT.format(MobSoulUtils.getSouls(stack));
            var requirement = DECIMAL_FORMAT.format(type.getSoulRequirement());

            tooltip.add(ModTooltips.SOUL_JAR.args(entityName, souls, requirement).build());

            if (context.isAdvanced()) {
                tooltip.add(ModTooltips.MST_ID.args(type.getId()).color(Formatting.DARK_GRAY).build());
            }
        }
    }

    public static ClampedModelPredicateProvider getFillPropertyGetter() {
        return new ClampedModelPredicateProvider() {
            @Override
            public float call(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i) {
                return this.unclampedCall(itemStack, clientWorld, livingEntity, i);
            }

            @Override
            public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
                var type = MobSoulUtils.getType(stack);

                if (type != null) {
                    double souls = MobSoulUtils.getSouls(stack);

                    if (souls > 0) {
                        return (int) ((souls / type.getSoulRequirement()) * 9);
                    }
                }

                return 0;
            }
        };
    }
}
