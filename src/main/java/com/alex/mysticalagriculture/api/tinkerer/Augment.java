package com.alex.mysticalagriculture.api.tinkerer;

import com.alex.mysticalagriculture.api.lib.AbilityCache;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.util.Utils;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.EnumSet;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class Augment {
    private final Identifier id;
    private final int tier;
    private final EnumSet<AugmentType> types;
    private final int primaryColor;
    private final int secondaryColor;
    private boolean enabled;

    public Augment(Identifier id, int tier, EnumSet<AugmentType> types, int primaryColor, int secondaryColor) {
        this.id = id;
        this.tier = tier;
        this.types = types;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.enabled = true;
    }

    public Identifier getId() {
        return this.id;
    }

    public EnumSet<AugmentType> getAugmentTypes() {
        return this.types;
    }

    public int getTier() {
        return this.tier;
    }

    public Item getItem() {
        return Registry.ITEM.get(new Identifier(MOD_ID, this.getName() + "_augment"));
    }

    public int getPrimaryColor() {
        return this.primaryColor;
    }

    public int getSecondaryColor() {
        return this.secondaryColor;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean hasEffect() {
        return this.getTier() >= 5;
    }

    public boolean onItemUse(ItemUsageContext context) {
        return false;
    }

    public boolean onRightClick(ItemStack stack, World world, PlayerEntity player, Hand hand) {
        return false;
    }

    public boolean onRightClickEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        return false;
    }

    public boolean onHitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return false;
    }

    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {
        return false;
    }

    public void onInventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
    }

    public void addToolAttributeModifiers(Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
    }

    public void addArmorAttributeModifiers(Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
    }

    public String getNameWithSuffix(String suffix) {
        return String.format("%s_%s", this.getName(), suffix);
    }

    public void onPlayerTick(World world, PlayerEntity player, AbilityCache cache) {
    }

    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
    }

    public void onPlayerFall(World world, PlayerEntity player) {
    }

    public String getModId() {
        return this.getId().getNamespace();
    }

    public String getName() {
        return this.getId().getPath();
    }

    public MutableText getDisplayName() {
        return new TranslatableText(String.format("augment.%s.%s", this.getModId(), this.getName()));
    }

    public static ItemStack getEssenceForTinkerable(Tinkerable tinkerable, int min, int max) {
        switch (tinkerable.getTinkerableTier()) {
            case 1: return new ItemStack(Items.INFERIUM_ESSENCE, Utils.randInt(min, max));
            case 2: return new ItemStack(Items.PRUDENTIUM_ESSENCE, Utils.randInt(min, max));
            case 3: return new ItemStack(Items.TERTIUM_ESSENCE, Utils.randInt(min, max));
            case 4: return new ItemStack(Items.IMPERIUM_ESSENCE, Utils.randInt(min, max));
            case 5: return new ItemStack(Items.SUPREMIUM_ESSENCE, Utils.randInt(min, max));
            default: return ItemStack.EMPTY;
        }
    }
}
