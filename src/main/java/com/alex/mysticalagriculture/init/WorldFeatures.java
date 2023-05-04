package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.world.feature.SoulstoneFeature;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public final class WorldFeatures {
    public static final Map<Feature<OreFeatureConfig>, Identifier> FEATURES = new LinkedHashMap<>();

    public static final Feature<OreFeatureConfig> SOULSTONE = register(new SoulstoneFeature(OreFeatureConfig.CODEC), "soulstone");

    private static Feature<OreFeatureConfig> register(Feature<OreFeatureConfig> feature, String name) {
        Identifier id = new Identifier(MOD_ID, name);
        FEATURES.put(feature, id);
        return feature;
    }

    public static void registerFeatures() {
        FEATURES.forEach((feature, id) -> {
            Registry.register(Registries.FEATURE, id, feature);
        });
    }
}
