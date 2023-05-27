package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.util.Localizable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MysticalSeedItem extends AliasedBlockItem implements CropProvider {
    private final Crop crop;

    public MysticalSeedItem(Crop crop) {
        super(crop.getCropBlock(), new Settings());
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

        if (!this.crop.getModId().equals(MysticalAgriculture.MOD_ID)) {
            tooltip.add(ModTooltips.getAddedByTooltip(this.crop.getModId()));
        }

        if (context.isAdvanced()) {
            tooltip.add(ModTooltips.CROP_ID.args(this.crop.getId()).color(Formatting.DARK_GRAY).build());
        }
    }

    public Crop getCrop() {
        return this.crop;
    }
}
