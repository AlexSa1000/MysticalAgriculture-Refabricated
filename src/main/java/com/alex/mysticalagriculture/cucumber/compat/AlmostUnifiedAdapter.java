package com.alex.mysticalagriculture.cucumber.compat;

import com.almostreliable.unified.api.AlmostUnifiedLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AlmostUnifiedAdapter {
    public static boolean isLoaded() {
        return FabricLoader.getInstance().isModLoaded("almostunified");
    }

    public static Item getPreferredItemForTag(String tagId) {
        if (isLoaded()) {
            TagKey<Item> tagKey = TagKey.of(Registry.ITEM.getKey(), new Identifier(tagId));
            return AlmostUnifiedAdapter.Adapter.getPreferredItemForTag(tagKey);
        } else {
            return null;
        }
    }

    private static class Adapter {
        private static Item getPreferredItemForTag(TagKey<Item> tag) {
            return AlmostUnifiedLookup.INSTANCE.getPreferredItemForTag(tag);
        }
    }
}
