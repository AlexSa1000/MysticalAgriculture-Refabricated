package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.util.Localizable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class MysticalSeedItem extends AliasedBlockItem {
    private final Crop crop;

    public MysticalSeedItem(Crop crop, Function<Settings, Settings> settings) {
        super(crop.getCrop(), settings.apply(new Settings()));
        this.crop = crop;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Localizable.of("item.mysticalagriculture.mystical_seeds").args(this.crop.getDisplayName()).build();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Text tier = this.crop.getTier().getDisplayName();
        tooltip.add(ModTooltips.TIER.args(tier).build());
    }

    public Crop getCrop() {
        return this.crop;
    }
}
