package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.cucumber.item.BaseItem;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

public class SoulJarItem extends BaseItem {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public SoulJarItem(Function<Settings, Settings> settings) {
        super(settings.compose(p -> p.maxCount(1)));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            stacks.add(new ItemStack(this));

            MobSoulTypeRegistry.getInstance().getMobSoulTypes().forEach(type -> {
                if (type.isEnabled()) {
                    stacks.add(MobSoulUtils.getFilledSoulJar(type, this));
                }
            });
        }
    }

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

    public static UnclampedModelPredicateProvider getFillPropertyGetter() {
        return new UnclampedModelPredicateProvider() {
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
