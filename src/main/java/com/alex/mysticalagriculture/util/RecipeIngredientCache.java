package com.alex.mysticalagriculture.util;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.zucchini.helper.RecipeHelper;
import com.google.common.base.Stopwatch;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RecipeIngredientCache implements SynchronousResourceReloader {
    public static final RecipeIngredientCache INSTANCE = new RecipeIngredientCache();

    private final Map<RecipeType<?>, Map<Item, List<Ingredient>>> caches;

    private RecipeIngredientCache() {
        this.caches = new HashMap<>();
    }

    public void setCaches(Map<RecipeType<?>, Map<Item, List<Ingredient>>> caches) {
        this.caches.clear();
        this.caches.putAll(caches);
    }
    @Override
    public void reload(ResourceManager manager) {
        var stopwatch = Stopwatch.createStarted();

        this.caches.clear();

        cache(RecipeTypes.REPROCESSOR);
        //cache(RecipeTypes.SOUL_EXTRACTION);

        MysticalAgriculture.LOGGER.info("Recipe ingredient caching done in {} ms", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    public boolean isValidInput(ItemStack stack, RecipeType<?> type) {
        var cache = this.caches.getOrDefault(type, Collections.emptyMap()).get(stack.getItem());
        return cache != null && cache.stream().anyMatch(i -> i.test(stack));
    }

    private static <C extends Inventory, T extends Recipe<C>> void cache(RecipeType<T> type) {
        INSTANCE.caches.put(type, new HashMap<>());

        for (var recipe : RecipeHelper.getRecipes(type).values()) {
            for (var ingredient : recipe.getIngredients()) {
                var items = new HashSet<>();
                for (var stack : ingredient.getMatchingStacks()) {
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
