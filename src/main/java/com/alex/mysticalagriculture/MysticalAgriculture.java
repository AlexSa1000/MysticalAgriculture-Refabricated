package com.alex.mysticalagriculture;

import com.alex.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.cucumber.helper.ConfigHelper;
import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.api.tinkering.ITinkerable;
import com.alex.mysticalagriculture.blockentities.HarvesterBlockEntity;
import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.mysticalagriculture.blockentities.SoulExtractorBlockEntity;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.handler.ExperienceCapsuleHandler;
import com.alex.mysticalagriculture.init.*;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import com.alex.mysticalagriculture.registry.PluginRegistry;
import com.alex.mysticalagriculture.util.RecipeIngredientCache;
import io.github.fabricators_of_create.porting_lib.event.common.PlayerEvents;
import io.github.fabricators_of_create.porting_lib.event.common.TagsUpdatedCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.reborn.energy.api.EnergyStorage;

import static com.alex.mysticalagriculture.api.tinkering.Augment.getEssenceForTinkerable;


public class MysticalAgriculture implements ModInitializer {
    public static final String MOD_ID = "mysticalagriculture";
    public static final String NAME = "MysticalAgriculture: Refabricated";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static final CreativeModeTab CREATIVE_TAB = FabricItemGroupBuilder.build(new ResourceLocation(MOD_ID), () -> new ItemStack(ModItems.INFERIUM_ESSENCE));

    @Override
    public void onInitialize() {

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            double inferiumDropChance = ModConfigs.INFERIUM_DROP_CHANCE.get();
            if (killedEntity instanceof PathfinderMob && Math.random() < inferiumDropChance) {
                world.addFreshEntityWithPassengers(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), new ItemStack(ModItems.INFERIUM_ESSENCE)));
            }

            if (entity instanceof ServerPlayer player) {
                var held = player.getMainHandItem();
                var item = held.getItem();

                if (item instanceof ITinkerable tinkerable) {

                    boolean witherDropsEssence = ModConfigs.WITHER_DROPS_ESSENCE.get();

                    if (witherDropsEssence && killedEntity instanceof WitherBoss) {
                        ItemStack stack = getEssenceForTinkerable(tinkerable, 1, 3);

                        if (!stack.isEmpty())
                         world.addFreshEntityWithPassengers(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                    }

                    boolean dragonDropsEssence = ModConfigs.DRAGON_DROPS_ESSENCE.get();

                    if (dragonDropsEssence && killedEntity instanceof EnderDragon) {
                        ItemStack stack = getEssenceForTinkerable(tinkerable, 2, 4);

                        if (!stack.isEmpty())
                            world.addFreshEntityWithPassengers(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                    }

                    var enlightenmentLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MYSTICAL_ENLIGHTENMENT, held);

                    if (enlightenmentLevel > 0) {
                        boolean witherDropsCognizant = ModConfigs.WITHER_DROPS_COGNIZANT.get();

                        if (witherDropsCognizant && killedEntity instanceof WitherBoss) {
                            var stack = new ItemStack(ModItems.COGNIZANT_DUST, 4 + (enlightenmentLevel - 1));

                            world.addFreshEntityWithPassengers(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                        }

                        boolean dragonDropsCognizant = ModConfigs.DRAGON_DROPS_COGNIZANT.get();

                        if (dragonDropsCognizant && killedEntity instanceof EnderDragon) {
                            var stack = new ItemStack(ModItems.COGNIZANT_DUST, 4 + (enlightenmentLevel * 2));

                            world.addFreshEntityWithPassengers(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                        }
                    }
            }
        }});

        try {
            ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.CLIENT, ModConfigs.CLIENT);
            ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.COMMON, ModConfigs.COMMON);

            initAPI();

            ConfigHelper.load(ModConfigs.COMMON, "mysticalagriculture-common.toml");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        PluginRegistry.getInstance().loadPlugins();

        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModBlockEntities.registerModBlockEntities();
        ModRecipeTypes.registerModRecipeTypes();
        ModRecipeSerializers.registerModRecipeSerializers();
        ModContainerTypes.registerModContainerTypes();
        ModEnchantments.registerModEnchantments();

        ModWorldFeatures.registerModFeatures();
        ModBiomeModifiers.registerModBiomeModifiers();

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(RecipeIngredientCache.INSTANCE::onSyncDataPackContents);
        TagsUpdatedCallback.EVENT.register(RecipeIngredientCache.INSTANCE::onTagsUpdated);
        PlayerEvents.PICKUP_XP.register(ExperienceCapsuleHandler::onPlayerPickupXp);

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
