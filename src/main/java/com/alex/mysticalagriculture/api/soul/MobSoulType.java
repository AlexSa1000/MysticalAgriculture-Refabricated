package com.alex.mysticalagriculture.api.soul;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.Set;

public class MobSoulType {
    private final Identifier id;
    private final Set<Identifier> entityIds;
    private final double soulRequirement;
    private final int color;
    private String entityDisplayNameKey = null;
    private Text entityDisplayName = null;
    private final boolean enabled;

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

    public int getColor() {
        return this.color;
    }

    public double getSoulRequirement() {
        return this.soulRequirement;
    }

    public Text getEntityDisplayName() {
        if (this.entityDisplayName == null) {
            if (this.entityDisplayNameKey != null) {
                this.entityDisplayName = new TranslatableText(this.entityDisplayNameKey);
            } else {
                Identifier entityId = this.entityIds.stream().findFirst().orElse(null);
                if (entityId != null) {
                    EntityType<?> entity = Registry.ENTITY_TYPE.get(entityId);
                    this.entityDisplayName = entity.getName();
                    return this.entityDisplayName;
                }

                this.entityDisplayName = new TranslatableText("tooltip.mysticalagriculture.invalid_entity");
            }

        }

        return this.entityDisplayName;
    }

    public boolean isEntityApplicable(LivingEntity entity) {
        return this.entityIds.contains(Registry.ENTITY_TYPE.getId(entity.getType()));
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
