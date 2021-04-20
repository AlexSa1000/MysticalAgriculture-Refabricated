package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.alex.mysticalagriculture.lib.ModMobSoulTypes;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.item.BaseItem;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class SoulJarItem extends BaseItem {
    public SoulJarItem(Function<Settings, Settings> properties) {
        super(properties.compose(p -> p.maxCount(1)));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
        if (this.isIn(group)) {
            items.add(new ItemStack(this));

            ModMobSoulTypes.getMobSoulTypes().forEach(type -> {
                if (type.isEnabled()) {
                    items.add(MobSoulUtils.getFilledSoulJar(type, this));
                }
            });
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        MobSoulType type = MobSoulUtils.getType(stack);
        if (type != null) {
            Text entityName = type.getEntityDisplayName();
            double souls = MobSoulUtils.getSouls(stack);
            tooltip.add(ModTooltips.SOUL_JAR.args(entityName, souls, type.getSoulRequirement()).build());

            if (context.isAdvanced()) {
                tooltip.add(ModTooltips.MST_ID.args(type.getId()).color(Formatting.DARK_GRAY).build());
            }
        }
    }

    public static ModelPredicateProvider getFillPropertyGetter() {
        return (stack, world, entity) -> {
            MobSoulType type = MobSoulUtils.getType(stack);
            if (type != null) {
                double souls = MobSoulUtils.getSouls(stack);
                if (souls > 0) {
                    return (int) ((souls / type.getSoulRequirement()) * 9);
                }
            }

            return 0;
        };
    }
}
