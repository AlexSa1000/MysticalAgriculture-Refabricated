package com.alex.mysticalagriculture.items;

import com.alex.cucumber.util.Localizable;
import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.api.crop.ICropProvider;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MysticalSeedItem extends ItemNameBlockItem implements ICropProvider {
    private final Crop crop;

    public MysticalSeedItem(Crop crop) {
        super(crop.getCropBlock(), new Item.Properties());
        this.crop = crop;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Localizable.of("item.mysticalagriculture.mystical_seeds").args(this.crop.getDisplayName()).build();
    }

    @Override
    public Component getDescription() {
        return this.getName(ItemStack.EMPTY);
    }

    @Override
    public String getDescriptionId() {
        return Localizable.of("item.mysticalagriculture.mystical_seeds").args(this.crop.getDisplayName()).buildString();
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.crop.hasEffect(stack) || super.isFoil(stack);
    }


    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        var tier = this.crop.getTier().getDisplayName();
        tooltip.add(ModTooltips.TIER.args(tier).build());

        if (!this.crop.getModId().equals(MysticalAgriculture.MOD_ID)) {
            tooltip.add(ModTooltips.getAddedByTooltip(this.crop.getModId()));
        }

        if (flag.isAdvanced()) {
            tooltip.add(ModTooltips.CROP_ID.args(this.crop.getId()).color(ChatFormatting.DARK_GRAY).build());
        }
    }

    public Crop getCrop() {
        return this.crop;
    }
}
