package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.crop.CropTierProvider;
import com.alex.mysticalagriculture.util.item.BaseItem;
import com.alex.mysticalagriculture.api.farmland.FarmlandConverter;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

import java.util.function.Function;

public class EssenceItem extends BaseItem implements FarmlandConverter, CropTierProvider {
    private final CropTier tier;

    public EssenceItem(CropTier tier, Function<Settings, Settings> properties) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return this.convert(context);
    }

    @Override
    public FarmlandBlock getConvertedFarmland() {
        return this.tier.getFarmland();
    }

    @Override
    public CropTier getTier() {
        return this.tier;
    }
}
