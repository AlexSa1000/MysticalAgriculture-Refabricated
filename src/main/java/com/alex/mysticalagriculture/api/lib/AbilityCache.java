package com.alex.mysticalagriculture.api.lib;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AbilityCache {
    private static final HashMap<String, Runnable> EMPTY_MAP = new HashMap<>();
    private final Map<String, Map<String, Runnable>> cache = new HashMap<>();


    public void add(Augment augment, Player player, Runnable onRemove) {
        String key = getPlayerKey(player);
        this.cache.computeIfAbsent(augment.getId().toString(), s -> new HashMap<>()).put(key, onRemove);
    }

    public void remove(String augment, Player player) {
        String key = getPlayerKey(player);
        this.cache.getOrDefault(augment, EMPTY_MAP).remove(key).run();
    }

    public void removeQuietly(String augment, Player player) {
        String key = getPlayerKey(player);
        this.cache.getOrDefault(augment, EMPTY_MAP).remove(key);
    }

    public boolean isCached(Augment augment, Player player) {
        String key = getPlayerKey(player);
        return this.cache.getOrDefault(augment.getId().toString(), EMPTY_MAP).containsKey(key);
    }

    public Set<String> getCachedAbilities(Player player) {
        String key = getPlayerKey(player);
        return this.cache.entrySet().stream().filter(e -> e.getValue().containsKey(key)).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    private static String getPlayerKey(Player player) {
        return player.getGameProfile().getName() + ":" + player.getCommandSenderWorld().isClientSide();
    }
}
