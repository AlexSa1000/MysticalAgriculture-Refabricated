package com.alex.mysticalagriculture.util;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.cucumber.helper.RecipeHelper;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import com.google.common.base.Stopwatch;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RecipeIngredientCache implements SimpleSynchronousResourceReloadListener {
    public static final RecipeIngredientCache INSTANCE = new RecipeIngredientCache();

    private final Map<net.minecraft.world.item.crafting.RecipeType<?>, Map<Item, List<Ingredient>>> caches;

    private RecipeIngredientCache() {
        this.caches = new HashMap<>();
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        var stopwatch = Stopwatch.createStarted();

        this.caches.clear();

        cache(ModRecipeTypes.REPROCESSOR);
        cache(ModRecipeTypes.SOUL_EXTRACTION);

        MysticalAgriculture.LOGGER.info("Recipe ingredient caching done in {} ms", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    public void setCaches(Map<net.minecraft.world.item.crafting.RecipeType<?>, Map<Item, List<Ingredient>>> caches) {
        this.caches.clear();
        this.caches.putAll(caches);
    }

    public boolean isValidInput(ItemStack stack, net.minecraft.world.item.crafting.RecipeType<?> type) {
        var cache = this.caches.getOrDefault(type, Collections.emptyMap()).get(stack.getItem());
        return cache != null && cache.stream().anyMatch(i -> i.test(stack));
    }

    private static <C extends Container, T extends Recipe<C>> void cache(net.minecraft.world.item.crafting.RecipeType<T> type) {
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

    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation("mysticalagriculture", "recipe_cache");
    }
}