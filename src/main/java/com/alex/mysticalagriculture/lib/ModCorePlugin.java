package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.MysticalAgriculturePlugin;
import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.crop.CropType;
import com.alex.mysticalagriculture.api.lib.PluginConfig;
import com.alex.mysticalagriculture.api.registry.AugmentRegistry;
import com.alex.mysticalagriculture.api.registry.CropRegistry;
import com.alex.mysticalagriculture.api.registry.MobSoulTypeRegistry;
import com.alex.mysticalagriculture.init.Blocks;
import com.alex.mysticalagriculture.init.Items;

public class ModCorePlugin implements MysticalAgriculturePlugin {
    @Override
    public void configure(PluginConfig config) {
        config.setModId(MysticalAgriculture.MOD_ID);
        config.disableDynamicSeedCraftingRecipes();
        config.disableDynamicSeedInfusionRecipes();
        config.disableDynamicSeedReprocessingRecipes();
    }

    @Override
    public void onRegisterCrops(CropRegistry registry) {
        registry.registerTier(CropTier.ELEMENTAL);
        registry.registerTier(CropTier.ONE);
        registry.registerTier(CropTier.TWO);
        registry.registerTier(CropTier.THREE);
        registry.registerTier(CropTier.FOUR);
        registry.registerTier(CropTier.FIVE);

        registry.registerType(CropType.RESOURCE);
        registry.registerType(CropType.MOB);

        ModCrops.onRegisterCrops(registry);
    }

    @Override
    public void onPostRegisterCrops(CropRegistry registry) {
        CropTier.ELEMENTAL.setFarmland(() -> Blocks.INFERIUM_FARMLAND).setEssence(() -> Items.INFERIUM_ESSENCE);
        CropTier.ONE.setFarmland(() -> Blocks.INFERIUM_FARMLAND).setEssence(() -> Items.INFERIUM_ESSENCE);
        CropTier.TWO.setFarmland(() -> Blocks.PRUDENTIUM_FARMLAND).setEssence(() -> Items.PRUDENTIUM_ESSENCE);
        CropTier.THREE.setFarmland(() -> Blocks.TERTIUM_FARMLAND).setEssence(() -> Items.TERTIUM_ESSENCE);
        CropTier.FOUR.setFarmland(() -> Blocks.IMPERIUM_FARMLAND).setEssence(() -> Items.IMPERIUM_ESSENCE);
        CropTier.FIVE.setFarmland(() -> Blocks.SUPREMIUM_FARMLAND).setEssence(() -> Items.SUPREMIUM_ESSENCE);

        CropType.RESOURCE.setCraftingSeed(Items.PROSPERITY_SEED_BASE);
        CropType.MOB.setCraftingSeed(Items.SOULIUM_SEED_BASE);
    }

    @Override
    public void onRegisterMobSoulTypes(MobSoulTypeRegistry registry) {
        ModMobSoulTypes.onRegisterMobSoulTypes(registry);
    }

    @Override
    public void onRegisterAugments(AugmentRegistry registry) {
        ModAugments.onRegisterAugments(registry);
    }
}
