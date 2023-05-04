package com.alex.mysticalagriculture.zucchini.compat;

import com.almostreliable.unified.api.AlmostUnifiedLookup;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class AlmostUnifiedAdapter {
    public static boolean isLoaded() {
        return FabricLoader.getInstance().isModLoaded("almostunified");
    }

    public static Item getPreferredItemForTag(String tagId) {
        if (isLoaded()) {
            TagKey<Item> tagKey = TagKey.of(Registries.ITEM.getKey(), new Identifier(tagId));
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
