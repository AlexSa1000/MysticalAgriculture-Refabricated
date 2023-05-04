package com.alex.mysticalagriculture.api;

import com.alex.mysticalagriculture.api.registry.AugmentRegistry;
import com.alex.mysticalagriculture.api.registry.CropRegistry;
import com.alex.mysticalagriculture.api.registry.MobSoulTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

public class MysticalAgricultureAPI {
    public static final String MOD_ID = "mysticalagriculture";
    public static final TagKey<Block> CROPS_TAG = BlockTags.of("mysticalagriculture:crops");
    public static final TagKey<Item> ESSENCES_TAG = ItemTags.of("mysticalagriculture:essences");
    public static final TagKey<Item> SEEDS_TAG = ItemTags.of("mysticalagriculture:seeds");

    private static CropRegistry cropRegistry;
    private static AugmentRegistry augmentRegistry;
    private static MobSoulTypeRegistry soulTypeRegistry;

    /**
     * The registry in which all crops, crop tiers, and crop types are stored
     * @return the crop registry
     */
    public static CropRegistry getCropRegistry() {
        return cropRegistry;
    }

    /**
     * The registry in which all augments are stored
     * @return the augment registry
     */
    public static AugmentRegistry getAugmentRegistry() {
        return augmentRegistry;
    }

    /**
     * The registry in which all mob soul types are stored
     * @return the mob soul type registry
     */
    public static MobSoulTypeRegistry getMobSoulTypeRegistry() {
        return soulTypeRegistry;
    }
}
