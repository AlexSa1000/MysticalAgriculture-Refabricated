package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.crop.CropTierProvider;
import com.alex.mysticalagriculture.api.farmland.IFarmlandConverter;
import com.alex.mysticalagriculture.zucchini.item.BaseItem;
import com.alex.mysticalagriculture.api.farmland.FarmlandConverter;
import net.minecraft.block.Block;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class EssenceItem extends BaseItem implements IFarmlandConverter, CropTierProvider {
    private final CropTier tier;

    public EssenceItem(CropTier tier) {
        super();
        this.tier = tier;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!/*ModConfigs.ESSENCE_FARMLAND_CONVERSION.get()*/true)
            return ActionResult.PASS;

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
