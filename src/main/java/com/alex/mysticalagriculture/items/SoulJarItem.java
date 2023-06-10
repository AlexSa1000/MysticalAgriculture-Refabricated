package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.cucumber.item.BaseItem;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

public class SoulJarItem extends BaseItem {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public SoulJarItem(Function<Properties, Properties> properties) {
        super(properties.compose(p -> p.stacksTo(1)));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowedIn(group)) {
            items.add(new ItemStack(this));

            MobSoulTypeRegistry.getInstance().getMobSoulTypes().forEach(type -> {
                if (type.isEnabled()) {
                    items.add(MobSoulUtils.getFilledSoulJar(type, this));
                }
            });
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        var type = MobSoulUtils.getType(stack);

        if (type != null) {
            var entityName = type.getEntityDisplayName();
            var souls = DECIMAL_FORMAT.format(MobSoulUtils.getSouls(stack));
            var requirement = DECIMAL_FORMAT.format(type.getSoulRequirement());

            tooltip.add(ModTooltips.SOUL_JAR.args(entityName, souls, requirement).build());

            if (flag.isAdvanced()) {
                tooltip.add(ModTooltips.MST_ID.args(type.getId()).color(ChatFormatting.DARK_GRAY).build());
            }
        }
    }

    public static ClampedItemPropertyFunction getFillPropertyGetter() {
        return new ClampedItemPropertyFunction() {
            @Override
            public float call(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                return this.unclampedCall(itemStack, clientLevel, livingEntity, i);
            }

            @Override
            public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
                var type = MobSoulUtils.getType(itemStack);

                if (type != null) {
                    double souls = MobSoulUtils.getSouls(itemStack);

                    if (souls > 0) {
                        return (int) ((souls / type.getSoulRequirement()) * 9);
                    }
                }

                return 0;
            }
        };
    }
}
