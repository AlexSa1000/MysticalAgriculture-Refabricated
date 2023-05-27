package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.items.armor.EssenceBootsItem;
import com.alex.mysticalagriculture.items.armor.EssenceChestplateItem;
import com.alex.mysticalagriculture.items.armor.EssenceHelmetItem;
import com.alex.mysticalagriculture.items.armor.EssenceLeggingsItem;
import com.alex.mysticalagriculture.items.tool.*;
import com.alex.mysticalagriculture.cucumber.item.tool.*;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.alex.mysticalagriculture.items.armor.EssenceChestplateItem.ARMOR_MODIFIERS;
import static net.minecraft.item.Item.ATTACK_DAMAGE_MODIFIER_ID;
import static net.minecraft.item.Item.ATTACK_SPEED_MODIFIER_ID;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract Item getItem();

    @Shadow private boolean empty;

    @Inject(method = "getAttributeModifiers(Lnet/minecraft/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;", at = @At(value = "HEAD"), cancellable = true)
    private void injected(EquipmentSlot slot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir) {

        if (((ItemStack) ((Object) this)).getItem() instanceof EssenceHelmetItem || ((ItemStack) ((Object) this)).getItem() instanceof EssenceChestplateItem || ((ItemStack) ((Object) this)).getItem() instanceof EssenceLeggingsItem || ((ItemStack) ((Object) this)).getItem() instanceof EssenceBootsItem) {
            Multimap<EntityAttribute, EntityAttributeModifier> modifiers = HashMultimap.create();
            var item = (ArmorItem) this.getItem();
            if (slot == item.type.getEquipmentSlot()) {
                var material = item.getMaterial();

                modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(ARMOR_MODIFIERS[slot.getEntitySlotId()], "Armor modifier", material.getProtection(item.type), EntityAttributeModifier.Operation.ADDITION));
                modifiers.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(ARMOR_MODIFIERS[slot.getEntitySlotId()], "Armor toughness", material.getToughness(), EntityAttributeModifier.Operation.ADDITION));

                if (material.getKnockbackResistance() > 0) {
                    modifiers.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(ARMOR_MODIFIERS[slot.getEntitySlotId()], "Armor knockback resistance", material.getKnockbackResistance(), EntityAttributeModifier.Operation.ADDITION));
                }

                AugmentUtils.getAugments((ItemStack) ((Object) this)).forEach(a -> {
                    a.addArmorAttributeModifiers(modifiers);
                });
            }
            cir.setReturnValue(modifiers);
        }

        if (((ItemStack) ((Object) this)).getItem() instanceof EssenceAxeItem || ((ItemStack) ((Object) this)).getItem() instanceof EssenceHoeItem || ((ItemStack) ((Object) this)).getItem() instanceof EssencePickaxeItem || ((ItemStack) ((Object) this)).getItem() instanceof EssenceShovelItem || ((ItemStack) ((Object) this)).getItem() instanceof EssenceSwordItem) {
            Multimap<EntityAttribute, EntityAttributeModifier> modifiers = HashMultimap.create();
            if (slot == EquipmentSlot.MAINHAND) {
                if (((ItemStack) ((Object) this)).getItem() instanceof EssenceAxeItem) {
                    modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", ((BaseAxeItem) ((ItemStack) ((Object) this)).getItem()).getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
                    modifiers.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", ((BaseAxeItem) ((ItemStack) ((Object) this)).getItem()).getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION));
                } else if (((ItemStack) ((Object) this)).getItem() instanceof EssenceHoeItem) {
                    modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", ((BaseHoeItem) ((ItemStack) ((Object) this)).getItem()).getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
                    modifiers.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", ((BaseHoeItem) ((ItemStack) ((Object) this)).getItem()).getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION));
                } else if (((ItemStack) ((Object) this)).getItem() instanceof EssencePickaxeItem) {
                    modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", ((BasePickaxeItem) ((ItemStack) ((Object) this)).getItem()).getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
                    modifiers.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", ((BasePickaxeItem) ((ItemStack) ((Object) this)).getItem()).getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION));
                } else if (((ItemStack) ((Object) this)).getItem() instanceof EssenceShovelItem) {
                    modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", ((BaseShovelItem) ((ItemStack) ((Object) this)).getItem()).getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
                    modifiers.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", ((BaseShovelItem) ((ItemStack) ((Object) this)).getItem()).getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION));
                } else if (((ItemStack) ((Object) this)).getItem() instanceof EssenceSwordItem) {
                    modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", ((BaseSwordItem) ((ItemStack) ((Object) this)).getItem()).getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
                    modifiers.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", ((BaseSwordItem) ((ItemStack) ((Object) this)).getItem()).getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION));
                }
                AugmentUtils.getAugments((ItemStack) ((Object) this)).forEach(a -> {
                    a.addToolAttributeModifiers(modifiers);
                });
            }
            cir.setReturnValue(modifiers);
        }
    }
}



