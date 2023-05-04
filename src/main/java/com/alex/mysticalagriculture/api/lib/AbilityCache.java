package com.alex.mysticalagriculture.api.lib;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AbilityCache {
    private static final HashMap<String, Runnable> EMPTY_MAP = new HashMap<>();
    private final Map<String, Map<String, Runnable>> cache = new HashMap<>();


    public void add(Augment augment, PlayerEntity player, Runnable onRemove) {
        String key = getPlayerKey(player);
        this.cache.computeIfAbsent(augment.getId().toString(), s -> new HashMap<>()).put(key, onRemove);
    }

    public void remove(String augment, PlayerEntity player) {
        String key = getPlayerKey(player);
        this.cache.getOrDefault(augment, EMPTY_MAP).remove(key).run();
    }

    public boolean isCached(Augment augment, PlayerEntity player) {
        String key = getPlayerKey(player);
        return this.cache.getOrDefault(augment.getId().toString(), EMPTY_MAP).containsKey(key);
    }

    public Set<String> getCachedAbilities(PlayerEntity player) {
        String key = getPlayerKey(player);
        return this.cache.entrySet().stream().filter(e -> e.getValue().containsKey(key)).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    private static String getPlayerKey(PlayerEntity player) {
        return player.getGameProfile().getName() + ":" + player.getEntityWorld().isClient();
    }
}
