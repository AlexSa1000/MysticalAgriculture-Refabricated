package com.alex.mysticalagriculture.registry;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.MysticalAgriculturePlugin;
import com.alex.mysticalagriculture.api.lib.PluginConfig;
import com.alex.mysticalagriculture.lib.ModCorePlugin;
import net.fabricmc.loader.api.FabricLoader;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class PluginRegistry {
    private static final PluginRegistry INSTANCE = new PluginRegistry();
    private final Map<MysticalAgriculturePlugin, PluginConfig> plugins = new LinkedHashMap<>();

    public void loadPlugins() {
        this.plugins.put(new ModCorePlugin(), new PluginConfig());
        MysticalAgriculture.LOGGER.info("Registered plugin: {}", ModCorePlugin.class.getName());


        /*FabricLoader.getInstance().getAllMods().forEach(data -> {
            data. .forEach(annotation -> {
                if (annotation.annotationType().getClassName().equals(MysticalAgriculturePlugin.class.getName())) {
                    try {
                        Class<?> clazz = Class.forName(annotation.memberName());
                        if (IMysticalAgriculturePlugin.class.isAssignableFrom(clazz)) {
                            IMysticalAgriculturePlugin plugin = (IMysticalAgriculturePlugin) clazz.newInstance();
                            this.plugins.put(plugin, new PluginConfig());
                            MysticalAgriculture.LOGGER.info("Registered plugin: {}", annotation.memberName());
                        }
                    } catch (Exception e) {
                        MysticalAgriculture.LOGGER.error("Error loading plugin: {}", annotation.memberName(), e);
                    }
                }
            });
        });*/

        this.forEach((plugin, config) -> {
            plugin.configure(config);
            plugin.onRegisterMobSoulTypes(MobSoulTypeRegistry.getInstance());
        });

        this.forEach((plugin, config) -> {
            plugin.onPostRegisterMobSoulTypes(MobSoulTypeRegistry.getInstance());
        });
    }

    public void forEach(BiConsumer<MysticalAgriculturePlugin, PluginConfig> action) {
        this.plugins.forEach(action);
    }

    public static PluginRegistry getInstance() {
        return INSTANCE;
    }
}
