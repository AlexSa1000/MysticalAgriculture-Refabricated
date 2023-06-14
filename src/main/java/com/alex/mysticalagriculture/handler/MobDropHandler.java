package com.alex.mysticalagriculture.handler;

import com.alex.cucumber.util.Utils;
import com.alex.mysticalagriculture.api.tinkering.ITinkerable;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.init.ModEnchantments;
import com.alex.mysticalagriculture.init.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class MobDropHandler {
    public static void onLivingDrops() {
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
    }

    private static ItemStack getEssenceForTinkerable(ITinkerable tinkerable, int min, int max) {
        return switch (tinkerable.getTinkerableTier()) {
            case 1 -> new ItemStack(ModItems.INFERIUM_ESSENCE, Utils.randInt(min, max));
            case 2 -> new ItemStack(ModItems.PRUDENTIUM_ESSENCE, Utils.randInt(min, max));
            case 3 -> new ItemStack(ModItems.TERTIUM_ESSENCE, Utils.randInt(min, max));
            case 4 -> new ItemStack(ModItems.IMPERIUM_ESSENCE, Utils.randInt(min, max));
            case 5 -> new ItemStack(ModItems.SUPREMIUM_ESSENCE, Utils.randInt(min, max));
            default -> ItemStack.EMPTY;
        };
    }
}
