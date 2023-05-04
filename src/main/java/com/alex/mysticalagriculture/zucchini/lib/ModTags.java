package com.alex.mysticalagriculture.zucchini.lib;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ModTags {
    public static final TagKey<Block> MINEABLE_WITH_PAXEL = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "mineable/paxel"));
    public static final TagKey<Block> MINEABLE_WITH_SICKLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "mineable/sickle"));
}
