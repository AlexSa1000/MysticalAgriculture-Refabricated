package com.alex.mysticalagriculture.registry;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.registry.IAugmentRegistry;
import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.items.AugmentItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AugmentRegistry implements IAugmentRegistry {
    private static final AugmentRegistry INSTANCE = new AugmentRegistry();

    private final Map<ResourceLocation, Augment> augments = new LinkedHashMap<>();

    @Override
    public void register(Augment augment) {
        if (this.augments.values().stream().noneMatch(c -> c.getId().equals(augment.getId()))) {
            this.augments.put(augment.getId(), augment);
        } else {
            MysticalAgriculture.LOGGER.info("{} tried to register a duplicate augment with id {}, skipping", augment.getModId(), augment.getId());
        }
    }

    @Override
    public List<Augment> getAugments() {
        return List.copyOf(this.augments.values());
    }

    @Override
    public Augment getAugmentById(ResourceLocation id) {
        return this.augments.get(id);
    }

    public static AugmentRegistry getInstance() {
        return INSTANCE;
    }

    public void onRegisterItems() {
        PluginRegistry.getInstance().forEach((plugin, config) -> plugin.onRegisterAugments(this));

        this.augments.forEach((id, a) -> {
            var item = new AugmentItem(a);

            Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MysticalAgriculture.MOD_ID, a.getNameWithSuffix("augment")), item);
        });

        PluginRegistry.getInstance().forEach((plugin, config) -> plugin.onPostRegisterAugments(this));
    }
}
