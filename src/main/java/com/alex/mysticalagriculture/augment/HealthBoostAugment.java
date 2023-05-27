package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.lib.AbilityCache;
import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.mysticalagriculture.cucumber.helper.ColorHelper;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.UUID;

public class HealthBoostAugment extends Augment {
    private static final UUID ATTRIBUTE_ID = UUID.fromString("e04addf9-0fe8-4498-b5a8-45e5201cd76d");
    private final int amplifier;

    public HealthBoostAugment(Identifier id, int tier, int amplifier) {
        super(id, tier, EnumSet.of(AugmentType.ARMOR), getColor(0xC6223B, tier), getColor(0x3B0402, tier));
        this.amplifier = amplifier;
    }

    @Override
    public void onPlayerTick(World world, PlayerEntity player, AbilityCache cache) {
        if (!cache.isCached(this, player)) {
            var health = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (health == null)
                return;

            int boost = 4 * this.amplifier;

            var modifier = health.getModifier(ATTRIBUTE_ID);
            if (modifier != null) {
                if (boost < modifier.getValue())
                    return;

                health.removeModifier(modifier);

                cache.getCachedAbilities(player).forEach(c -> {
                    var augment = AugmentRegistry.getInstance().getAugmentById(new Identifier(c));

                    if (augment instanceof HealthBoostAugment && cache.isCached(augment, player)) {
                        cache.removeQuietly(c, player);
                    }
                });
            }

            health.addPersistentModifier(new EntityAttributeModifier(ATTRIBUTE_ID, MysticalAgriculture.MOD_ID + ":health_boost_augment", boost, EntityAttributeModifier.Operation.ADDITION));

            cache.add(this, player, () -> {
                float max = player.getMaxHealth() - boost;

                if (player.getHealth() > max) {
                    player.setHealth(max);
                }

                health.removeModifier(ATTRIBUTE_ID);
            });
        }
    }

    @Override
    public void addArmorAttributeModifiers(Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
        attributes.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(ATTRIBUTE_ID, "Armor modifier", 4 * this.amplifier, EntityAttributeModifier.Operation.ADDITION));
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 5, 1));
    }
}
