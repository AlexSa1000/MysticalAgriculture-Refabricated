package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.world.feature.SoulstoneFeature;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public final class ModWorldFeatures {
    public static final Map<Feature<OreConfiguration>, ResourceLocation> FEATURES = new LinkedHashMap<>();

    public static final Feature<OreConfiguration> SOULSTONE = register(new SoulstoneFeature(OreConfiguration.CODEC), "soulstone");

    private static Feature<OreConfiguration> register(Feature<OreConfiguration> feature, String name) {
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        FEATURES.put(feature, id);
        return feature;
    }

    public static void registerModFeatures() {
        FEATURES.forEach((feature, id) -> {
            Registry.register(Registry.FEATURE, id, feature);
        });
    }
}
