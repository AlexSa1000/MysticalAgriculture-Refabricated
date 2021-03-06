package com.alex.mysticalagriculture.world;

import com.alex.mysticalagriculture.init.Blocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public final class ModWorldgenRegistration {

    public static void onCommonSetup() {
        int size, rate, height;
        OreFeatureConfig config;

        size = 64; //ModConfigs.SOULSTONE_SPAWN_SIZE.get();
        rate = 4; //ModConfigs.SOULSTONE_SPAWN_RATE.get();
        height = 128; //ModConfigs.SOULSTONE_SPAWN_HEIGHT.get();

        config = new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_NETHER, Blocks.SOULSTONE.getDefaultState(), size);
        ConfiguredFeature<?, ?> configuredSoulstoneFeature = ModWorldFeatures.SOULSTONE.configure(config)
                .rangeOf(height)
                .spreadHorizontally()
                .repeatRandomly(rate);

        RegistryKey<ConfiguredFeature<?, ?>> soulstoneNether = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier(MOD_ID, "soulstone"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, soulstoneNether.getValue(), configuredSoulstoneFeature);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.RAW_GENERATION, soulstoneNether);

        size = 8; //ModConfigs.PROSPERITY_SPAWN_SIZE.get();
        rate = 12; //ModConfigs.PROSPERITY_SPAWN_RATE.get();
        height = 50; //ModConfigs.PROSPERITY_SPAWN_HEIGHT.get();
        config = new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.PROSPERITY_ORE.getDefaultState(), size);
        ConfiguredFeature<?, ?> configuredProsperityOreFeature = Feature.ORE.configure(config)
                .rangeOf(height)
                .spreadHorizontally()
                .repeatRandomly(rate);

        RegistryKey<ConfiguredFeature<?, ?>> prosperityOreOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier(MOD_ID, "prosperity_ore"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, prosperityOreOverworld.getValue(), configuredProsperityOreFeature);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, prosperityOreOverworld);

        size = 8; //ModConfigs.INFERIUM_SPAWN_SIZE.get();
        rate = 16; //ModConfigs.INFERIUM_SPAWN_RATE.get();
        height = 50; //ModConfigs.INFERIUM_SPAWN_HEIGHT.get();
        config = new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.INFERIUM_ORE.getDefaultState(), size);
        ConfiguredFeature<?, ?> configuredInferiumOreFeature = Feature.ORE.configure(config)
                .rangeOf(height)
                .spreadHorizontally()
                .repeatRandomly(rate);

        RegistryKey<ConfiguredFeature<?, ?>> inferiumOreOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier(MOD_ID, "inferium_ore"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, inferiumOreOverworld.getValue(), configuredInferiumOreFeature);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, inferiumOreOverworld);
    }
}
