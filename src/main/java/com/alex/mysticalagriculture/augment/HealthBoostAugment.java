package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentType;
import com.alex.mysticalagriculture.util.helper.ColorHelper;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

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
    public void addArmorAttributeModifiers(Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
        attributes.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(ATTRIBUTE_ID, "Armor modifier", 4 * this.amplifier, EntityAttributeModifier.Operation.ADDITION));
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 5, 1));
    }
}
