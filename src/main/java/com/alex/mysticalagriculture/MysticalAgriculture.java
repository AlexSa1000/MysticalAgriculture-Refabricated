package com.alex.mysticalagriculture;


import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.api.tinkering.Tinkerable;
import com.alex.mysticalagriculture.blockentities.HarvesterBlockEntity;
import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.mysticalagriculture.blockentities.SoulExtractorBlockEntity;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.cucumber.crafting.TagMapper;
import com.alex.mysticalagriculture.cucumber.helper.ConfigHelper;
import com.alex.mysticalagriculture.cucumber.item.tool.BaseShearsItem;
import com.alex.mysticalagriculture.init.*;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import com.alex.mysticalagriculture.registry.PluginRegistry;
import com.alex.mysticalagriculture.util.RecipeIngredientCache;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.reborn.energy.api.EnergyStorage;

import static com.alex.mysticalagriculture.api.tinkering.Augment.getEssenceForTinkerable;


public class MysticalAgriculture implements ModInitializer {
    public static final String MOD_ID = "mysticalagriculture";
    public static final String NAME = "MysticalAgriculture: Refabricated";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID), () -> new ItemStack(Items.INFERIUM_ESSENCE));

    @Override
    public void onInitialize() {

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            double inferiumDropChance = ModConfigs.INFERIUM_DROP_CHANCE.get();
            if (killedEntity instanceof PathAwareEntity && Math.random() < inferiumDropChance) {
                world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), new ItemStack(Items.INFERIUM_ESSENCE)));
            }

            if (entity instanceof ServerPlayerEntity player) {
                var held = player.getMainHandStack();
                var item = held.getItem();

                if (item instanceof Tinkerable) {
                    Tinkerable tinkerable = (Tinkerable) item;

                    boolean witherDropsEssence = ModConfigs.WITHER_DROPS_ESSENCE.get();

                    if (witherDropsEssence && killedEntity instanceof WitherEntity) {
                        ItemStack stack = getEssenceForTinkerable(tinkerable, 1, 3);

                        if (!stack.isEmpty())
                         world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                    }

                    boolean dragonDropsEssence = ModConfigs.DRAGON_DROPS_ESSENCE.get();

                    if (dragonDropsEssence && killedEntity instanceof EnderDragonEntity) {
                        ItemStack stack = getEssenceForTinkerable(tinkerable, 2, 4);

                        if (!stack.isEmpty())
                            world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                    }

                    var enlightenmentLevel = EnchantmentHelper.getLevel(Enchantments.MYSTICAL_ENLIGHTENMENT, held);

                    if (enlightenmentLevel > 0) {
                        boolean witherDropsCognizant = ModConfigs.WITHER_DROPS_COGNIZANT.get();

                        if (witherDropsCognizant && killedEntity instanceof WitherEntity) {
                            var stack = new ItemStack(Items.COGNIZANT_DUST, 4 + (enlightenmentLevel - 1));

                            world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                        }

                        boolean dragonDropsCognizant = ModConfigs.DRAGON_DROPS_COGNIZANT.get();

                        if (dragonDropsCognizant && killedEntity instanceof EnderDragonEntity) {
                            var stack = new ItemStack(Items.COGNIZANT_DUST, 4 + (enlightenmentLevel * 2));

                            world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                        }
                    }
            }
        }});

        try {
            ConfigHelper.load(ModConfigs.COMMON, "mysticalagriculture-common.toml");
            //ConfigHelper.load(ModConfigs.CLIENT, "mysticalagriculture-client.toml");

            initAPI();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        PluginRegistry.getInstance().loadPlugins();

        Blocks.registerBlocks();
        Items.registerItems();
        BlockEntities.registerBlockEntities();
        RecipeTypes.registerRecipeTypes();
        RecipeSerializers.registerRecipeSerializers();
        ScreenHandlerTypes.registerScreenHandlerTypes();
        Enchantments.registerEnchantments();

        WorldFeatures.registerFeatures();
        BiomeModifiers.registerBiomeModifiers();

        TagMapper.reloadTagMappings();

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(RecipeIngredientCache.INSTANCE);

        EnergyStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> {
            if (entity instanceof HarvesterBlockEntity harvester) {
                return harvester.getEnergy();
            } else {
                return EnergyStorage.EMPTY;
            }
        }, Blocks.HARVESTER);

        EnergyStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> {
            if (entity instanceof SoulExtractorBlockEntity extractor) {
                return extractor.getEnergy();
            } else {
                return EnergyStorage.EMPTY;
            }
        }, Blocks.SOUL_EXTRACTOR);

        EnergyStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> {
            if (entity instanceof ReprocessorBlockEntity reprocessor) {
                return reprocessor.getEnergy();
            } else {
                return EnergyStorage.EMPTY;
            }
        }, Blocks.BASIC_REPROCESSOR, Blocks.INFERIUM_REPROCESSOR, Blocks.PRUDENTIUM_REPROCESSOR, Blocks.TERTIUM_REPROCESSOR, Blocks.IMPERIUM_REPROCESSOR, Blocks.SUPREMIUM_REPROCESSOR, Blocks.AWAKENED_SUPREMIUM_REPROCESSOR);

        ItemStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> {
            if (entity instanceof BaseInventoryBlockEntity inventory) {
                return InventoryStorage.of(inventory.getInventory(), direction);
            } else {
                return Storage.empty();
            }
        }, Blocks.AWAKENING_ALTAR, Blocks.AWAKENING_PEDESTAL, Blocks.ESSENCE_VESSEL, Blocks.HARVESTER, Blocks.INFUSION_ALTAR, Blocks.INFUSION_PEDESTAL);

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
                }, Blocks.BASIC_REPROCESSOR, Blocks.INFERIUM_REPROCESSOR, Blocks.PRUDENTIUM_REPROCESSOR, Blocks.TERTIUM_REPROCESSOR, Blocks.IMPERIUM_REPROCESSOR, Blocks.SUPREMIUM_REPROCESSOR, Blocks.AWAKENED_SUPREMIUM_REPROCESSOR);

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
        }, Blocks.SOUL_EXTRACTOR);

        ItemStorage.SIDED.registerForBlocks((world, pos, state, entity, direction) -> Storage.empty(), Blocks.TINKERING_TABLE);
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
