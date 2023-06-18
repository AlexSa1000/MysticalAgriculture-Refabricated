package com.alex.mysticalagriculture.registry;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.registry.IMobSoulTypeRegistry;
import com.alex.mysticalagriculture.api.soul.MobSoulType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;
import java.util.stream.Collectors;

public class MobSoulTypeRegistry implements IMobSoulTypeRegistry {
    private static final MobSoulTypeRegistry INSTANCE = new MobSoulTypeRegistry();

    private final Map<ResourceLocation, MobSoulType> mobSoulTypes = new LinkedHashMap<>();
    private final Set<ResourceLocation> usedEntityIds = new HashSet<>();

    @Override
    public void register(MobSoulType mobSoulType) {
        if (this.mobSoulTypes.values().stream().noneMatch(m -> m.getId().equals(mobSoulType.getId()))) {
            var duplicates = mobSoulType.getEntityIds().stream().filter(this.usedEntityIds::contains).collect(Collectors.toSet());

            if (duplicates.isEmpty()) {
                this.mobSoulTypes.put(mobSoulType.getId(), mobSoulType);
                this.usedEntityIds.addAll(mobSoulType.getEntityIds());
            } else {
                MysticalAgriculture.LOGGER.info("{} tried to register a mob soul type for entity ids {}, but they already have one registered, skipping", mobSoulType.getModId(), duplicates);
            }
        } else {
            MysticalAgriculture.LOGGER.info("{} tried to register a duplicate mob soul type with id {}, skipping", mobSoulType.getModId(), mobSoulType.getId());
        }
    }

    @Override
    public List<MobSoulType> getMobSoulTypes() {
        return List.copyOf(this.mobSoulTypes.values());
    }

    @Override
    public MobSoulType getMobSoulTypeById(ResourceLocation id) {
        return this.mobSoulTypes.get(id);
    }

    @Override
    public MobSoulType getMobSoulTypeByEntity(LivingEntity entity) {
        return this.mobSoulTypes.values().stream().filter(t -> t.isEntityApplicable(entity)).findFirst().orElse(null);
    }

    @Override
    public Set<ResourceLocation> getUsedEntityIds() {
        return Collections.unmodifiableSet(this.usedEntityIds);
    }

    @Override
    public boolean addEntityTo(MobSoulType type, ResourceLocation entity) {
        if (!this.usedEntityIds.contains(entity)) {
            this.usedEntityIds.add(entity);

            type.getEntityIds().add(entity);

            return true;
        }

        return false;
    }

    @Override
    public boolean removeEntityFrom(MobSoulType type, ResourceLocation entity) {
        if (type.getEntityIds().contains(entity)) {
            type.getEntityIds().remove(entity);

            this.usedEntityIds.remove(entity);

            return true;
        }

        return false;
    }

    public static MobSoulTypeRegistry getInstance() {
        return INSTANCE;
    }

}
