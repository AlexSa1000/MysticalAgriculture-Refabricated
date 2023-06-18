package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.IAugmentProvider;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.cucumber.iface.Enableable;
import com.alex.cucumber.item.BaseItem;
import com.alex.cucumber.lib.Colors;
import com.alex.cucumber.util.Localizable;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AugmentItem extends BaseItem implements IAugmentProvider, Enableable {
    private final Augment augment;

    public AugmentItem(Augment augment, Function<Properties, Properties> properties) {
        super(properties);
        this.augment = augment;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Localizable.of("item.mysticalagriculture.augment").args(this.augment.getDisplayName()).build();
    }

    @Override
    public Component getDescription() {
        return this.getName(ItemStack.EMPTY);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.augment.hasEffect();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(ModTooltips.getTooltipForTier(this.augment.getTier()));
        tooltip.add(Component.literal(Colors.GRAY + this.augment.getAugmentTypes()
                .stream()
                .map(AugmentType::getDisplayName)
                .map(Component::getString)
                .collect(Collectors.joining(", "))
        ));

        if (flag.isAdvanced()) {
            tooltip.add(ModTooltips.AUGMENT_ID.args(this.augment.getId()).color(ChatFormatting.DARK_GRAY).build());
        }
    }

    @Override
    public Augment getAugment() {
        return this.augment;
    }

    @Override
    public boolean isEnabled() {
        return this.augment.isEnabled();
    }
}
