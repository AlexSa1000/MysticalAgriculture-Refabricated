package com.alex.mysticalagriculture;


import com.alex.mysticalagriculture.api.tinkerer.Tinkerable;
import com.alex.mysticalagriculture.compat.columns.MysticalAgricultureColumnBlocks;
import com.alex.mysticalagriculture.init.*;
import com.alex.mysticalagriculture.lib.ModAugments;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.lib.ModMobSoulTypes;
import com.alex.mysticalagriculture.util.crafting.TagMapper;
import com.alex.mysticalagriculture.world.ModWorldFeatures;
import com.alex.mysticalagriculture.world.ModWorldgenRegistration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static com.alex.mysticalagriculture.api.tinkerer.Augment.getEssenceForTinkerable;


public class MysticalAgriculture implements ModInitializer {
    public static final String MOD_ID = "mysticalagriculture";
    public static final String NAME = "MysticalAgriculture: Refabricated";
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID),
            () -> new ItemStack(Items.INFERIUM_ESSENCE));
    
    @Override
    public void onInitialize() {

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if(killedEntity instanceof PathAwareEntity && Math.random() < 0.2) {
                world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), new ItemStack(Items.INFERIUM_ESSENCE)));
            }

            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                Item item = player.getMainHandStack().getItem();

                if (item instanceof Tinkerable) {
                    Tinkerable tinkerable = (Tinkerable) item;

                    if (killedEntity instanceof WitherEntity) {
                        ItemStack stack = getEssenceForTinkerable(tinkerable, 1, 3);

                        if (!stack.isEmpty())
                         world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                    }

                    if (killedEntity instanceof EnderDragonEntity) {
                        ItemStack stack = getEssenceForTinkerable(tinkerable, 2, 4);

                        if (!stack.isEmpty())
                            world.spawnEntity(new ItemEntity(world, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), stack));
                }
            }
        }});

        Blocks.registerBlocks();
        Items.registerItems();
        BlockEntities.registerBlockEntities();
        RecipeTypes.registerRecipeTypes();
        RecipeSerializers.registerRecipeSerializers();

        ModAugments.register();
        ModAugments.registerItems();

        ModCrops.registerBlocks();
        ModCrops.registerItems();

        ModMobSoulTypes.register();

        ModWorldFeatures.registerFeatures();
        ModWorldgenRegistration.onCommonSetup();

        TagMapper.reloadTagMappings();

        if (FabricLoader.getInstance().isModLoaded("columns")) {
            MysticalAgricultureColumnBlocks.init();
        }
    }
}
