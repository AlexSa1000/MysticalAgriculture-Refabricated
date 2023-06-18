package com.alex.mysticalagriculture;


import com.alex.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.cucumber.helper.ConfigHelper;
import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.blockentities.HarvesterBlockEntity;
import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.mysticalagriculture.blockentities.SoulExtractorBlockEntity;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.handler.MobDropHandler;
import com.alex.mysticalagriculture.init.*;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import com.alex.mysticalagriculture.registry.PluginRegistry;
import com.alex.mysticalagriculture.util.RecipeIngredientCache;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.reborn.energy.api.EnergyStorage;

public class MysticalAgriculture implements ModInitializer {
    public static final String MOD_ID = "mysticalagriculture";
    public static final String NAME = "MysticalAgriculture: Refabricated";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static final CreativeModeTab CREATIVE_MODE_TAB = FabricItemGroup.builder(new ResourceLocation(MysticalAgriculture.MOD_ID, "creative_mode_tab"))
            .title(Component.translatable("itemGroup.minecraft.mysticalagriculture"))
            .icon(() -> new ItemStack(ModItems.INFERIUM_ESSENCE))
            .displayItems((ModCreativeModeTabs.displayItems)).build();

    @Override
    public void onInitialize() {
        MobDropHandler.onLivingDrops();

        try {
            ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, ModConfigs.CLIENT);
            ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, ModConfigs.COMMON);

            initAPI();

            ConfigHelper.load(ModConfigs.COMMON, "mysticalagriculture-common.toml");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        PluginRegistry.getInstance().loadPlugins();

        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModBlockEntities.registerBlockEntities();
        ModRecipeTypes.registerRecipeTypes();
        ModRecipeSerializers.registerRecipeSerializers();
        ModContainerTypes.registerModContainerTypes();
        ModEnchantments.registerEnchantments();

        ModWorldFeatures.registerFeatures();
        ModBiomeModifiers.registerBiomeModifiers();

        EnergyStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> {
            if (entity instanceof HarvesterBlockEntity harvester) {
                return harvester.getEnergy();
            } else {
                return EnergyStorage.EMPTY;
            }
        }, ModBlocks.HARVESTER);

        EnergyStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> {
            if (entity instanceof SoulExtractorBlockEntity extractor) {
                return extractor.getEnergy();
            } else {
                return EnergyStorage.EMPTY;
            }
        }, ModBlocks.SOUL_EXTRACTOR);

        EnergyStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> {
            if (entity instanceof ReprocessorBlockEntity reprocessor) {
                return reprocessor.getEnergy();
            } else {
                return EnergyStorage.EMPTY;
            }
        }, ModBlocks.BASIC_REPROCESSOR, ModBlocks.INFERIUM_REPROCESSOR, ModBlocks.PRUDENTIUM_REPROCESSOR, ModBlocks.TERTIUM_REPROCESSOR, ModBlocks.IMPERIUM_REPROCESSOR, ModBlocks.SUPREMIUM_REPROCESSOR, ModBlocks.AWAKENED_SUPREMIUM_REPROCESSOR);

        ItemStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> {
            if (entity instanceof BaseInventoryBlockEntity inventory) {
                return InventoryStorage.of(inventory.getInventory(), direction);
            } else {
                return Storage.empty();
            }
        }, ModBlocks.AWAKENING_ALTAR, ModBlocks.AWAKENING_PEDESTAL, ModBlocks.ESSENCE_VESSEL, ModBlocks.HARVESTER, ModBlocks.INFUSION_ALTAR, ModBlocks.INFUSION_PEDESTAL);

        ItemStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> {
            if (entity instanceof ReprocessorBlockEntity reprocessor) {
                if (direction != null) {
                    if (direction == Direction.UP && reprocessor.inventoryCapabilities[0].isPresent()) {
                        return InventoryStorage.of(reprocessor.inventoryCapabilities[0].orElseGet(() -> null), direction);
                    } else if (direction == Direction.DOWN && reprocessor.inventoryCapabilities[1].isPresent()) {
                        return InventoryStorage.of(reprocessor.inventoryCapabilities[1].orElseGet(() -> null), direction);
                    } else if (reprocessor.inventoryCapabilities[2].isPresent()) {
                        return InventoryStorage.of(reprocessor.inventoryCapabilities[2].orElseGet(() -> null), direction);
                    }
                }
            }
            return Storage.empty();
        }, ModBlocks.BASIC_REPROCESSOR, ModBlocks.INFERIUM_REPROCESSOR, ModBlocks.PRUDENTIUM_REPROCESSOR, ModBlocks.TERTIUM_REPROCESSOR, ModBlocks.IMPERIUM_REPROCESSOR, ModBlocks.SUPREMIUM_REPROCESSOR, ModBlocks.AWAKENED_SUPREMIUM_REPROCESSOR);

        ItemStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> {
            if (entity instanceof SoulExtractorBlockEntity extractor) {
                if (direction != null) {
                    if (direction == Direction.UP && extractor.inventoryCapabilities[0].isPresent()) {
                        return InventoryStorage.of(extractor.inventoryCapabilities[0].orElseGet(() -> null), direction);
                    } else if (direction == Direction.DOWN && extractor.inventoryCapabilities[1].isPresent()) {
                        return InventoryStorage.of(extractor.inventoryCapabilities[1].orElseGet(() -> null), direction);
                    } else if (extractor.inventoryCapabilities[2].isPresent()) {
                        return InventoryStorage.of(extractor.inventoryCapabilities[2].orElseGet(() -> null), direction);
                    }
                }
            }
            return Storage.empty();
        }, ModBlocks.SOUL_EXTRACTOR);

        ItemStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> Storage.empty(), ModBlocks.TINKERING_TABLE);
    }

    private static void initAPI() throws NoSuchFieldException, IllegalAccessException {
        var api = MysticalAgricultureAPI.class;

        var cropRegistry = api.getDeclaredField("cropRegistry");
        var augmentRegistry = api.getDeclaredField("augmentRegistry");
        var soulTypeRegistry = api.getDeclaredField("soulTypeRegistry");

        cropRegistry.setAccessible(true);
        cropRegistry.set(null, CropRegistry.getInstance());
        augmentRegistry.setAccessible(true);
        augmentRegistry.set(null, AugmentRegistry.getInstance());
        soulTypeRegistry.setAccessible(true);
        soulTypeRegistry.set(null, MobSoulTypeRegistry.getInstance());
    }
}
