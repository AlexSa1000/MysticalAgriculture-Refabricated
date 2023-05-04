package com.alex.mysticalagriculture.api.farmland;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;

/**
 * Implement this on items that are used to convert vanilla farmland to essence farmland
 * Make sure to call {@link FarmlandConverter#convert} in your item's {@link Item#useOnBlock(ItemUsageContext)} (UseOnContext)}
 */
public interface IFarmlandConverter {
    /**
     * Gets the farmland block that this converter should convert farmland to
     * @return the converted farmland block
     */
    Block getConvertedFarmland();
}
