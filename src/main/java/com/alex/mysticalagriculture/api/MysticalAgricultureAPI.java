package com.alex.mysticalagriculture.api;

import com.alex.mysticalagriculture.api.registry.AugmentRegistry;
import com.alex.mysticalagriculture.api.registry.CropRegistry;
import com.alex.mysticalagriculture.api.registry.MobSoulTypeRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MysticalAgricultureAPI {
    public static final String MOD_ID = "mysticalagriculture";
    public static final TagKey<Block> CROPS_TAG = BlockTags.create("mysticalagriculture:crops");
    public static final TagKey<Item> ESSENCES_TAG = ItemTags.bind("mysticalagriculture:essences");
    public static final TagKey<Item> SEEDS_TAG = ItemTags.bind("mysticalagriculture:seeds");

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
