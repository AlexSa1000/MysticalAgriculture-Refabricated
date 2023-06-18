package com.alex.mysticalagriculture.util;

import com.alex.cucumber.helper.RecipeHelper;
import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import com.google.common.base.Stopwatch;
import io.github.fabricators_of_create.porting_lib.event.common.TagsUpdatedCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RecipeIngredientCache implements ServerLifecycleEvents.SyncDataPackContents, TagsUpdatedCallback {
    public static final RecipeIngredientCache INSTANCE = new RecipeIngredientCache();

    private final Map<net.minecraft.world.item.crafting.RecipeType<?>, Map<Item, List<Ingredient>>> caches;

    public static ResourceLocation RELOAD_INGREDIENT_CACHE = new ResourceLocation(MysticalAgriculture.MOD_ID, "reload_ingredient_cache");

    private RecipeIngredientCache() {
        this.caches = new HashMap<>();
    }

    @Override
    public void onSyncDataPackContents(ServerPlayer player, boolean joined) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeVarInt(this.caches.size());

        for (var entry : this.caches.entrySet()) {
            var type = BuiltInRegistries.RECIPE_TYPE.getKey(entry.getKey());
            var caches = entry.getValue();

            assert type != null;

            buffer.writeResourceLocation(type);
            buffer.writeVarInt(caches.size());

            for (var cache : caches.entrySet()) {
                var item = BuiltInRegistries.ITEM.getKey(cache.getKey());
                var ingredients = cache.getValue();

                assert item != null;

                buffer.writeResourceLocation(item);
                buffer.writeVarInt(ingredients.size());

                for (var ingredient : ingredients) {
                    ingredient.toNetwork(buffer);
                }
            }
        }

        // send the new caches to the client
        if (player != null) {
            //NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
            ServerPlayNetworking.send(player, RELOAD_INGREDIENT_CACHE, buffer);
        } else {
            //NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), message);
            //ServerPlayNetworking.send((ServerPlayer) player, EXPERIENCE_CAPSULE_PICKUP, buffer);
        }
    }

    @Override
    public void onTagsUpdated(RegistryAccess registries) {
        var stopwatch = Stopwatch.createStarted();

        this.caches.clear();

        cache(ModRecipeTypes.REPROCESSOR);
        cache(ModRecipeTypes.SOUL_EXTRACTION);

        MysticalAgriculture.LOGGER.info("Recipe ingredient caching done in {} ms", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    public void setCaches(Map<RecipeType<?>, Map<Item, List<Ingredient>>> caches) {
        this.caches.clear();
        this.caches.putAll(caches);
    }

    public boolean isValidInput(ItemStack stack, RecipeType<?> type) {
        var cache = this.caches.getOrDefault(type, Collections.emptyMap()).get(stack.getItem());
        return cache != null && cache.stream().anyMatch(i -> i.test(stack));
    }

    private static <C extends Container, T extends Recipe<C>> void cache(RecipeType<T> type) {
        INSTANCE.caches.put(type, new HashMap<>());

        for (var recipe : RecipeHelper.getRecipes(type).values()) {
            for (var ingredient : recipe.getIngredients()) {
                var items = new HashSet<>();
                for (var stack : ingredient.getItems()) {
                    var item = stack.getItem();
                    if (items.contains(item))
                        continue;

                    var cache = INSTANCE.caches.get(type).computeIfAbsent(item, i -> new ArrayList<>());

                    items.add(item);
                    cache.add(ingredient);
                }
            }
        }
    }
}