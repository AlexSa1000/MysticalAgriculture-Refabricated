package com.alex.mysticalagriculture.registry;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.crop.CropType;
import com.alex.mysticalagriculture.api.lib.PluginConfig;
import com.alex.mysticalagriculture.blocks.MysticalCropBlock;
import com.alex.mysticalagriculture.items.MysticalEssenceItem;
import com.alex.mysticalagriculture.items.MysticalSeedItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CropRegistry implements com.alex.mysticalagriculture.api.registry.CropRegistry {
    private static final CropRegistry INSTANCE = new CropRegistry();

    private Map<Identifier, Crop> crops = new LinkedHashMap<>();
    private Map<Identifier, CropTier> tiers = new LinkedHashMap<>();
    private Map<Identifier, CropType> types = new LinkedHashMap<>();
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
    public Crop getCropById(Identifier id) {
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
    public CropTier getTierById(Identifier id) {
        return this.tiers.get(id);
    }

    @Override
    public List<CropType> getTypes() {
        return List.copyOf(this.types.values());
    }

    @Override
    public CropType getTypeById(Identifier id) {
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

            var id = new Identifier(MysticalAgriculture.MOD_ID, c.getNameWithSuffix("crop"));

            Registry.register(Registry.BLOCK, id, crop);
        });
        /*this.crops.forEach(c -> {
            CropBlock defaultCrop;
            if (c.getId().equals("inferium")) {
                defaultCrop = new InferiumCropBlock(c);
            } else {
                defaultCrop = new MysticalCropBlock(c);
            }
            c.setCropBlock(() -> defaultCrop);

            Registry.register(Registries.BLOCK, new Identifier(MOD_ID, c.getId() + "_crop"), defaultCrop);
        });*/
    }

    public void onRegisterItems() {
        /*crops.forEach(c -> {
            var idEssence = new Identifier(MOD_ID, c.getId() + "_essence");
            var idSeeds = new Identifier(MOD_ID, c.getId() + "_seeds");

            if (c.getId().equals("inferium")) {
                c.setEssenceItem(() -> Items.INFERIUM_ESSENCE);
                ITEMS.put(Items.INFERIUM_ESSENCE, idEssence);
                Registry.register(Registries.ITEM, idEssence, Items.INFERIUM_ESSENCE);
            } else {
                Item defaultEssence;
                if (c.getId().equals("netherite")) {
                    defaultEssence = new MysticalEssenceItem(c);
                } else {
                    defaultEssence = new MysticalEssenceItem(c);
                }
                c.setEssenceItem(() -> defaultEssence);
                ITEMS.put(defaultEssence, idEssence);
                Registry.register(Registries.ITEM, idEssence, defaultEssence);
            }

            AliasedBlockItem defaultSeeds;
            if (c.getId().equals("netherite")) {
                defaultSeeds = new MysticalSeedItem(c);
            } else {
                defaultSeeds = new MysticalSeedItem(c);
            }
            c.setSeedsItem(() -> defaultSeeds);
            ITEMS.put(defaultSeeds, idSeeds);
            Registry.register(Registries.ITEM, idSeeds, defaultSeeds);
        });

        CropTier.ELEMENTAL.setFarmland(() -> (FarmlandBlock) Blocks.INFERIUM_FARMLAND).setEssence(() -> Items.INFERIUM_ESSENCE);
        CropTier.ONE.setFarmland(() -> (FarmlandBlock) Blocks.INFERIUM_FARMLAND).setEssence(() -> Items.INFERIUM_ESSENCE);
        CropTier.TWO.setFarmland(() -> (FarmlandBlock) Blocks.PRUDENTIUM_FARMLAND).setEssence(() -> Items.PRUDENTIUM_ESSENCE);
        CropTier.THREE.setFarmland(() -> (FarmlandBlock) Blocks.TERTIUM_FARMLAND).setEssence(() -> Items.TERTIUM_ESSENCE);
        CropTier.FOUR.setFarmland(() -> (FarmlandBlock) Blocks.IMPERIUM_FARMLAND).setEssence(() -> Items.IMPERIUM_ESSENCE);
        CropTier.FIVE.setFarmland(() -> (FarmlandBlock) Blocks.SUPREMIUM_FARMLAND).setEssence(() -> Items.SUPREMIUM_ESSENCE);

        CropType.RESOURCE.setCraftingSeed(Items.PROSPERITY_SEED_BASE);
        CropType.MOB.setCraftingSeed(Items.SOULIUM_SEED_BASE);*/
        var crops = this.crops.values();

        crops.stream().filter(Crop::shouldRegisterEssenceItem).forEach(c -> {
            var essence = c.getEssenceItem();
            if (essence == null) {
                var defaultEssence = new MysticalEssenceItem(c, p -> p.group(MysticalAgriculture.ITEM_GROUP));
                essence = defaultEssence;
                c.setEssenceItem(() -> defaultEssence, true);
            }

            var id = new Identifier(MysticalAgriculture.MOD_ID, c.getNameWithSuffix("essence"));

            Registry.register(Registry.ITEM, id, essence);
        });

        crops.stream().filter(Crop::shouldRegisterSeedsItem).forEach(c -> {
            var seeds = c.getSeedsItem();
            if (seeds == null) {
                var defaultSeeds = new MysticalSeedItem(c, p -> p.group(MysticalAgriculture.ITEM_GROUP));
                seeds = defaultSeeds;
                c.setSeedsItem(() -> defaultSeeds, true);
            }

            var id = new Identifier(MysticalAgriculture.MOD_ID, c.getNameWithSuffix("seeds"));

            Registry.register(Registry.ITEM, id, seeds.asItem());
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
