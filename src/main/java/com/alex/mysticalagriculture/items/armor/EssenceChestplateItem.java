package com.alex.mysticalagriculture.items.armor;

import com.alex.mysticalagriculture.api.tinkerer.AugmentType;
import com.alex.mysticalagriculture.api.tinkerer.Tinkerable;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.item.BaseArmorItem;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class EssenceChestplateItem extends BaseArmorItem implements Tinkerable {
    private static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };
    private static final EnumSet<AugmentType> TYPES = EnumSet.of(AugmentType.ARMOR, AugmentType.CHESTPLATE);
    private final int tinkerableTier;
    private final int slots;

    public EssenceChestplateItem(ArmorMaterial material, int tinkerableTier, int slots, Function<Settings, Settings> settings) {
        super(material, EquipmentSlot.CHEST, settings);
        this.tinkerableTier = tinkerableTier;
        this.slots = slots;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity && slot == 2) {
            AugmentUtils.getAugments(stack).forEach(a -> a.onArmorTick(stack, world, (PlayerEntity) entity));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(ModTooltips.getTooltipForTier(this.tinkerableTier));
        AugmentUtils.getAugments(stack).forEach(a -> tooltip.add(a.getDisplayName().formatted(Formatting.GRAY)));
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = HashMultimap.create();
        if (slot == this.getSlotType()) {
            ArmorMaterial material = this.getMaterial();

            modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(ARMOR_MODIFIERS[slot.getEntitySlotId()], "Armor modifier", material.getProtectionAmount(slot), EntityAttributeModifier.Operation.ADDITION));
            modifiers.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(ARMOR_MODIFIERS[slot.getEntitySlotId()], "Armor toughness", material.getToughness(), EntityAttributeModifier.Operation.ADDITION));

            if (material.getKnockbackResistance() > 0) {
                modifiers.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(ARMOR_MODIFIERS[slot.getEntitySlotId()], "Armor knockback resistance", material.getKnockbackResistance(), EntityAttributeModifier.Operation.ADDITION));
            }

//            AugmentUtils.getAugments(stack).forEach(a -> {
//                a.addArmorAttributeModifiers(modifiers, slot, stack);
//            });
        }

        return modifiers;
    }

    @Override
    public int getAugmentSlots() {
        return this.slots;
    }

    @Override
    public EnumSet<AugmentType> getAugmentTypes() {
        return TYPES;
    }

    @Override
    public int getTinkerableTier() {
        return this.tinkerableTier;
    }
}
