package com.alex.mysticalagriculture.api.soul;

import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collections;
import java.util.Set;

public class
MobSoulType {
    private final ResourceLocation id;
    private final Set<ResourceLocation> entityIds;
    private final double soulRequirement;
    private final int color;
    private String entityDisplayNameKey = null;
    private Component entityDisplayName = null;
    private boolean enabled;

    public MobSoulType(ResourceLocation id, ResourceLocation entityId, double soulRequirement, int color) {
        this.id = id;
        this.entityIds = Collections.singleton(entityId);
        this.soulRequirement = soulRequirement;
        this.color = color;
        this.enabled = true;
    }

    public MobSoulType(ResourceLocation id, Set<ResourceLocation> entityIds, String entityDisplayNameKey, double soulRequirement, int color) {
        this.id = id;
        this.entityIds = entityIds;
        this.soulRequirement = soulRequirement;
        this.entityDisplayNameKey = entityDisplayNameKey;
        this.color = color;
        this.enabled = true;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String getModId() {
        return this.getId().getNamespace();
    }

    public Set<ResourceLocation> getEntityIds() {
        return this.entityIds;
    }

    public double getSoulRequirement() {
        return this.soulRequirement;
    }

    public int getColor() {
        return this.color;
    }

    public boolean isEntityApplicable(LivingEntity entity) {
        return this.entityIds.contains(Registry.ENTITY_TYPE.getId(entity.getType()));
    }

    public Component getEntityDisplayName() {
        if (this.entityDisplayName == null) {
            if (this.entityDisplayNameKey != null) {
                this.entityDisplayName = Component.translatable(String.format("mobSoulType.%s.%s", this.getModId(), this.entityDisplayNameKey));
            } else {
                var entityId = this.entityIds.stream().findFirst().orElse(null);

                if (entityId != null) {
                    var entity = Registry.ENTITY_TYPE.get(entityId);

                    if (entity != null) {
                        this.entityDisplayName = entity.getDescription();
                        return this.entityDisplayName;
                    }
                }

                this.entityDisplayName = Component.translatable("tooltip.mysticalagriculture.invalid_entity");
            }

        }

        return this.entityDisplayName;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
    public MobSoulType setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
