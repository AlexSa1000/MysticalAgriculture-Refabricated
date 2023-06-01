package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.config.ModConfigs;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class BiomeModifiers {

    public static RegistryKey<PlacedFeature> INFERIUM_ORE_KEY;
    public static RegistryKey<PlacedFeature> PROSPERITY_ORE_KEY;
    public static RegistryKey<PlacedFeature> SOULSTONE_KEY;

    public static void registerBiomeModifiers() {
        INFERIUM_ORE_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MOD_ID,"inferium_ore"));
        PROSPERITY_ORE_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MOD_ID,"prosperity_ore"));
        SOULSTONE_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MOD_ID,"soulstone"));

        if (ModConfigs.GENERATE_INFERIUM.get())
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, INFERIUM_ORE_KEY);
        if (ModConfigs.GENERATE_PROSPERITY.get())
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, PROSPERITY_ORE_KEY);
        if (ModConfigs.GENERATE_SOULSTONE.get())
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.RAW_GENERATION, SOULSTONE_KEY);
    }
}
