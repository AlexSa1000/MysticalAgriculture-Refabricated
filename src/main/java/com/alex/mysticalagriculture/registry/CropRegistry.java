package com.alex.mysticalagriculture.registry;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.crop.CropType;
import com.alex.mysticalagriculture.api.lib.PluginConfig;
import com.alex.mysticalagriculture.api.registry.ICropRegistry;
import com.alex.mysticalagriculture.blocks.MysticalCropBlock;
import com.alex.mysticalagriculture.items.MysticalEssenceItem;
import com.alex.mysticalagriculture.items.MysticalSeedItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CropRegistry implements ICropRegistry {
    private static final CropRegistry INSTANCE = new CropRegistry();

    private Map<ResourceLocation, Crop> crops = new LinkedHashMap<>();
    private Map<ResourceLocation, CropTier> tiers = new LinkedHashMap<>();
    private Map<ResourceLocation, CropType> types = new LinkedHashMap<>();
    private boolean allowRegistration = false;
    private PluginConfig currentPluginConfig = null;

    @Override
    public void register(Crop crop) {
        if (this.allowRegistration & crop.isEnabled()) {
            if (this.crops.values().stream().noneMatch(c -> c.getName().equals(crop.getName()))) {
                this.crops.put(crop.getId(), crop);

                this.loadRecipeConfig(crop);
            } else {
                MysticalAgriculture.LOGGER.info("{} tried to register a duplicate crop with name {}, skipping", crop.getModId(), crop.getName());
            }
        } else {
            MysticalAgriculture.LOGGER.error("{} tried to register crop {} outside of onRegisterCrops, skipping", crop.getModId(), crop.getName());
        }
    }

    @Override
    public void registerTier(CropTier tier) {
        if (!this.tiers.containsKey(tier.getId())) {
            this.tiers.put(tier.getId(), tier);
        } else {
            MysticalAgriculture.LOGGER.info("{} tried to register a duplicate crop tier with id {}, skipping", tier.getModId(), tier.getId());
        }
    }

    @Override
    public void registerType(CropType type) {
        if (!this.types.containsKey(type.getId())) {
            this.types.put(type.getId(), type);
        } else {
            MysticalAgriculture.LOGGER.info("{} tried to register a duplicate crop type with id {}, skipping", type.getModId(), type.getId());
        }
    }

    @Override
    public List<Crop> getCrops() {
        return List.copyOf(this.crops.values());
    }

    @Override
    public Crop getCropById(ResourceLocation id) {
        return this.crops.get(id);
    }

    @Override
    public Crop getCropByName(String name) {
        return this.crops.values().stream().filter(c -> name.equals(c.getName())).findFirst().orElse(null);
    }

    @Override
    public List<CropTier> getTiers() {
        return List.copyOf(this.tiers.values());
    }

    @Override
    public CropTier getTierById(ResourceLocation id) {
        return this.tiers.get(id);
    }

    @Override
    public List<CropType> getTypes() {
        return List.copyOf(this.types.values());
    }

    @Override
    public CropType getTypeById(ResourceLocation id) {
        return this.types.get(id);
    }

    public static CropRegistry getInstance() {
        return INSTANCE;
    }

    public void setAllowRegistration(boolean allowed) {
        this.allowRegistration = allowed;
    }

    public void onRegisterBlocks() {
        PluginRegistry.getInstance().forEach((plugin, config) -> {
            this.currentPluginConfig = config;

            plugin.onRegisterCrops(this);
        });

        var crops = this.crops.values();

        crops.stream().filter(Crop::shouldRegisterCropBlock).forEach(c -> {
            var crop = c.getCropBlock();
            if (crop == null) {
                var defaultCrop = new MysticalCropBlock(c);
                crop = defaultCrop;
                c.setCropBlock(() -> defaultCrop, true);
            }

            var id = new ResourceLocation(MysticalAgriculture.MOD_ID, c.getNameWithSuffix("crop"));

            Registry.register(BuiltInRegistries.BLOCK, id, crop);
        });
    }

    public void onRegisterItems() {
        var crops = this.crops.values();

        crops.stream().filter(Crop::shouldRegisterEssenceItem).forEach(c -> {
            var essence = c.getEssenceItem();
            if (essence == null) {
                var defaultEssence = new MysticalEssenceItem(c);
                essence = defaultEssence;
                c.setEssenceItem(() -> defaultEssence, true);
            }

            var id = new ResourceLocation(MysticalAgriculture.MOD_ID, c.getNameWithSuffix("essence"));

            Registry.register(BuiltInRegistries.ITEM, id, essence);
        });

        crops.stream().filter(Crop::shouldRegisterSeedsItem).forEach(c -> {
            var seeds = c.getSeedsItem();
            if (seeds == null) {
                var defaultSeeds = new MysticalSeedItem(c);
                seeds = defaultSeeds;
                c.setSeedsItem(() -> defaultSeeds, true);
            }

            var id = new ResourceLocation(MysticalAgriculture.MOD_ID, c.getNameWithSuffix("seeds"));

            Registry.register(BuiltInRegistries.ITEM, id, seeds.asItem());
        });

        PluginRegistry.getInstance().forEach((plugin, config) -> plugin.onPostRegisterCrops(this));

        this.currentPluginConfig = null;
    }

    private void loadRecipeConfig(Crop crop) {
        var recipes = crop.getRecipeConfig();
        var config = this.currentPluginConfig;

        recipes.setSeedCraftingRecipeEnabled(
                recipes.isSeedCraftingRecipeEnabled() && config.isDynamicSeedCraftingRecipesEnabled()
        );
        recipes.setSeedInfusionRecipeEnabled(
                recipes.isSeedInfusionRecipeEnabled() && config.isDynamicSeedInfusionRecipesEnabled()
        );
        recipes.setSeedReprocessorRecipeEnabled(
                recipes.isSeedReprocessorRecipeEnabled() && config.isDynamicSeedReprocessorRecipesEnabled()
        );
    }

}