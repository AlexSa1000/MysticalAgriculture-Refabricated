package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.crop.CropTierProvider;
import com.alex.mysticalagriculture.api.farmland.FarmlandConverter;
import com.alex.mysticalagriculture.api.farmland.IFarmlandConverter;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.cucumber.item.BaseItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public class EssenceItem extends BaseItem implements IFarmlandConverter, CropTierProvider {
    private final CropTier tier;

    public EssenceItem(CropTier tier, Function<Properties, Properties> properties) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!ModConfigs.ESSENCE_FARMLAND_CONVERSION.get())
            return InteractionResult.PASS;

        return FarmlandConverter.convert(this, context);
    }

    @Override
    public Block getConvertedFarmland() {
        return this.tier.getFarmland();
    }

    @Override
    public CropTier getTier() {
        return this.tier;
    }
}
