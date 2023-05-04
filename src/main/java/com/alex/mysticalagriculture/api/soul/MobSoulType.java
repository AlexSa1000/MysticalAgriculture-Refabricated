package com.alex.mysticalagriculture.api.soul;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.Set;

public class
MobSoulType {
    private final Identifier id;
    private final Set<Identifier> entityIds;
    private final double soulRequirement;
    private final int color;
    private String entityDisplayNameKey = null;
    private Text entityDisplayName = null;
    private boolean enabled;

    public MobSoulType(Identifier id, Identifier entityId, double soulRequirement, int color) {
        this.id = id;
        this.entityIds = Collections.singleton(entityId);
        this.soulRequirement = soulRequirement;
        this.color = color;
        this.enabled = true;
    }

    public MobSoulType(Identifier id, Set<Identifier> entityIds, String entityDisplayNameKey, double soulRequirement, int color) {
        this.id = id;
        this.entityIds = entityIds;
        this.soulRequirement = soulRequirement;
        this.entityDisplayNameKey = entityDisplayNameKey;
        this.color = color;
        this.enabled = true;
    }

    public Identifier getId() {
        return this.id;
    }

    public String getModId() {
        return this.getId().getNamespace();
    }

    public Set<Identifier> getEntityIds() {
        return this.entityIds;
    }

    public double getSoulRequirement() {
        return this.soulRequirement;
    }

    public int getColor() {
        return this.color;
    }

    public boolean isEntityApplicable(LivingEntity entity) {
        return this.entityIds.contains(Registries.ENTITY_TYPE.getId(entity.getType()));
    }

    public Text getEntityDisplayName() {
        if (this.entityDisplayName == null) {
            if (this.entityDisplayNameKey != null) {
                this.entityDisplayName = Text.translatable(String.format("mobSoulType.%s.%s", this.getModId(), this.entityDisplayNameKey));
            } else {
                var entityId = this.entityIds.stream().findFirst().orElse(null);

                if (entityId != null) {
                    var entity = Registries.ENTITY_TYPE.get(entityId);

                    if (entity != null) {
                        this.entityDisplayName = entity.getName();
                        return this.entityDisplayName;
                    }
                }

                this.entityDisplayName = Text.translatable("tooltip.mysticalagriculture.invalid_entity");
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
