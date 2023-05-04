package com.alex.mysticalagriculture;


import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.api.tinkering.Tinkerable;
import com.alex.mysticalagriculture.compat.columns.MysticalAgricultureColumnBlocks;
import com.alex.mysticalagriculture.init.*;
import com.alex.mysticalagriculture.lib.ModAugments;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.lib.ModMobSoulTypes;
import com.alex.mysticalagriculture.init.WorldFeatures;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import com.alex.mysticalagriculture.registry.PluginRegistry;
import com.alex.mysticalagriculture.zucchini.crafting.TagMapper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.alex.mysticalagriculture.api.tinkering.Augment.getEssenceForTinkerable;
import static com.alex.mysticalagriculture.init.CreativeModeTabs.displayItems;


public class MysticalAgriculture implements ModInitializer {
    public static final String MOD_ID = "mysticalagriculture";
    public static final String NAME = "MysticalAgriculture: Refabricated";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier(MysticalAgriculture.MOD_ID, "creative_mode_tab"))
            .displayName(Text.translatable("itemGroup.minecraft.mysticalagriculture"))
            .icon(() -> new ItemStack(Items.INFERIUM_ESSENCE))
            .entries((displayItems)).build();

    @Override
    public void onInitialize() {

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            //double inferiumDropChance = ModConfigs.INFERIUM_DROP_CHANCE.get();
            double inferiumDropChance = 0.2;
            if (killedEntity instanceof PathAwareEntity && Math.random() < inferiumDropChance) {
                world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), new ItemStack(Items.INFERIUM_ESSENCE)));
            }

            if (entity instanceof ServerPlayerEntity player) {
                var held = player.getMainHandStack();
                var item = held.getItem();

                if (item instanceof Tinkerable) {
                    Tinkerable tinkerable = (Tinkerable) item;

                    //boolean witherDropsEssence = ModConfigs.WITHER_DROPS_ESSENCE.get();
                    boolean witherDropsEssence = true;
                    if (witherDropsEssence && killedEntity instanceof WitherEntity) {
                        ItemStack stack = getEssenceForTinkerable(tinkerable, 1, 3);

                        if (!stack.isEmpty())
                         world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                    }

                    //boolean dragonDropsEssence = ModConfigs.DRAGON_DROPS_ESSENCE.get();
                    boolean dragonDropsEssence = true;
                    if (dragonDropsEssence && killedEntity instanceof EnderDragonEntity) {
                        ItemStack stack = getEssenceForTinkerable(tinkerable, 2, 4);

                        if (!stack.isEmpty())
                            world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                    }

                    var enlightenmentLevel = EnchantmentHelper.getLevel(Enchantments.MYSTICAL_ENLIGHTENMENT, held);

                    if (enlightenmentLevel > 0) {
                        boolean witherDropsCognizant = /*ModConfigs.WITHER_DROPS_COGNIZANT.get()*/true;

                        if (witherDropsCognizant && killedEntity instanceof WitherEntity) {
                            var stack = new ItemStack(Items.COGNIZANT_DUST, 4 + (enlightenmentLevel - 1));

                            world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                        }

                        boolean dragonDropsCognizant = /*ModConfigs.DRAGON_DROPS_COGNIZANT.get()*/true;

                        if (dragonDropsCognizant && killedEntity instanceof EnderDragonEntity) {
                            var stack = new ItemStack(Items.COGNIZANT_DUST, 4 + (enlightenmentLevel * 2));

                            world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                        }
                    }
            }
        }});

        try {
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

        /*if (FabricLoader.getInstance().isModLoaded("columns")) {
            MysticalAgricultureColumnBlocks.init();
        }*/
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
