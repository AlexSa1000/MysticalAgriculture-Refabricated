package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.config.ModConfigs;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class BiomeModifiers {

    public static ResourceKey<PlacedFeature> INFERIUM_ORE_KEY;
    public static ResourceKey<PlacedFeature> PROSPERITY_ORE_KEY;
    public static ResourceKey<PlacedFeature> SOULSTONE_KEY;

    public static void registerBiomeModifiers() {
        INFERIUM_ORE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(MOD_ID,"inferium_ore"));
        PROSPERITY_ORE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(MOD_ID,"prosperity_ore"));
        SOULSTONE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(MOD_ID,"soulstone"));

        if (ModConfigs.GENERATE_INFERIUM.get())
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, INFERIUM_ORE_KEY);
        if (ModConfigs.GENERATE_PROSPERITY.get())
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, PROSPERITY_ORE_KEY);
        if (ModConfigs.GENERATE_SOULSTONE.get())
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Decoration.RAW_GENERATION, SOULSTONE_KEY);
    }
}
