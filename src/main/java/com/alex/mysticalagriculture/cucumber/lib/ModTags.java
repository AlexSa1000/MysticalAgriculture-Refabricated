package com.alex.mysticalagriculture.cucumber.lib;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ModTags {
    public static final TagKey<Block> MINEABLE_WITH_PAXEL = TagKey.of(Registry.BLOCK_KEY, new Identifier(MOD_ID, "mineable/paxel"));
    public static final TagKey<Block> MINEABLE_WITH_SICKLE = TagKey.of(Registry.BLOCK_KEY, new Identifier(MOD_ID, "mineable/sickle"));
}
