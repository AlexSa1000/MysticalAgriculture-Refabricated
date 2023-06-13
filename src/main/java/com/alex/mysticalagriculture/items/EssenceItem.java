package com.alex.mysticalagriculture.items;

import com.alex.cucumber.item.BaseItem;
import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.crop.ICropTierProvider;
import com.alex.mysticalagriculture.api.farmland.IFarmlandConverter;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.cucumber.item.BaseItem;
import com.alex.mysticalagriculture.api.farmland.FarmlandConverter;
import net.minecraft.block.Block;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class EssenceItem extends BaseItem implements IFarmlandConverter, ICropTierProvider {
    private final CropTier tier;

    public EssenceItem(CropTier tier) {
        super();
        this.tier = tier;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!ModConfigs.ESSENCE_FARMLAND_CONVERSION)
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
