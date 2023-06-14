package com.alex.mysticalagriculture.items.armor;

import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.mysticalagriculture.api.tinkering.ITinkerable;
import com.alex.mysticalagriculture.api.tinkering.Tinkerable;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.cucumber.item.BaseArmorItem;
import com.alex.cucumber.util.Utils;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.init.ModItems;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.SugarCaneBlock;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class EssenceChestplateItem extends BaseArmorItem implements ITinkerable {
    public static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };
    private static final EnumSet<AugmentType> TYPES = EnumSet.of(AugmentType.ARMOR, AugmentType.CHESTPLATE);
    private final int tinkerableTier;
    private final int slots;

    public EssenceChestplateItem(ArmorMaterial material, int tinkerableTier, int slots) {
        super(material, Type.CHESTPLATE);
        this.tinkerableTier = tinkerableTier;
        this.slots = slots;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (entity instanceof Player player && slot == 2) {
            AugmentUtils.getAugments(stack).forEach(a -> a.onArmorTick(stack, level, player));

            if (ModConfigs.AWAKENED_SUPREMIUM_SET_BONUS.get() && !level.isClientSide() && level.getGameTime() % 20L == 0 && hasAwakenedSupremiumSet(player)) {
                handleGrowthTicks(level, player);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(ModTooltips.getTooltipForTier(this.tinkerableTier));

        if (ModConfigs.AWAKENED_SUPREMIUM_SET_BONUS.get() && stack.is(ModItems.AWAKENED_SUPREMIUM_CHESTPLATE)) {
            tooltip.add(ModTooltips.SET_BONUS.args(ModTooltips.AWAKENED_SUPREMIUM_SET_BONUS.build()).build());
        }

        AugmentUtils.getAugments(stack).forEach(a -> {
            tooltip.add(a.getDisplayName().withStyle(ChatFormatting.GRAY));
        });
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();

        if (slot == this.type.getSlot()) {
            var material = this.getMaterial();

            modifiers.put(Attributes.ARMOR, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", material.getDefenseForType(this.type), AttributeModifier.Operation.ADDITION));
            modifiers.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor toughness", material.getToughness(), AttributeModifier.Operation.ADDITION));

            if (material.getKnockbackResistance() > 0) {
                modifiers.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor knockback resistance", material.getKnockbackResistance(), AttributeModifier.Operation.ADDITION));
            }

            AugmentUtils.getAugments(stack).forEach(a -> {
                a.addArmorAttributeModifiers(modifiers, slot, stack);
            });
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

    private static boolean hasAwakenedSupremiumSet(Player player) {
        var helmet = player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.AWAKENED_SUPREMIUM_HELMET);
        var chestplate = player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.AWAKENED_SUPREMIUM_CHESTPLATE);
        var leggings = player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.AWAKENED_SUPREMIUM_LEGGINGS);
        var boots = player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.AWAKENED_SUPREMIUM_BOOTS);

        return helmet && chestplate && leggings && boots;
    }

    private static void handleGrowthTicks(Level level, Player player) {
        var pos = player.getOnPos();
        int range = 5;

        BlockPos.betweenClosedStream(pos.offset(-range, -range, -range), pos.offset(range, range, range)).forEach(aoePos -> {
            if (Math.random() < 0.5)
                return;

            var state = level.getBlockState(aoePos);
            var plantBlock = state.getBlock();

            if (plantBlock instanceof StemBlock || plantBlock instanceof SugarCaneBlock) {

                state.randomTick((ServerLevel) level, aoePos, Utils.RANDOM);

                double d0 = aoePos.getX() + level.getRandom().nextFloat();
                double d1 = aoePos.getY();
                double d2 = aoePos.getZ() + level.getRandom().nextFloat();

                ((ServerLevel) level).sendParticles(ParticleTypes.HAPPY_VILLAGER, d0, d1, d2, 1, 0, 0, 0, 0.1D);
            }
        });
    }
}