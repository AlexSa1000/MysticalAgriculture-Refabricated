package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.mysticalagriculture.zucchini.helper.ColorHelper;
import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;

import java.util.EnumSet;
import java.util.UUID;

public class StrengthAugment extends Augment {
    private static final UUID ATTRIBUTE_ID = UUID.fromString("527f7039-70c4-45e5-bdb7-b8721642cee5");
    private final int amplifier;

    public StrengthAugment(Identifier id, int tier, int amplifier) {
        super(id, tier, EnumSet.of(AugmentType.SWORD), getColor(0xFFFD90, tier), getColor(0xCC8E27, tier));
        this.amplifier = amplifier;
    }

    @Override
    public void addToolAttributeModifiers(Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
        attributes.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTRIBUTE_ID, "Tool modifier", 5 * this.amplifier, EntityAttributeModifier.Operation.ADDITION));
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 5, 1));
    }
}
