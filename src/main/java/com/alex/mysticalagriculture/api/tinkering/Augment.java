package com.alex.mysticalagriculture.api.tinkering;

import com.alex.cucumber.forge.event.entity.living.LivingFallEvent;
import com.alex.cucumber.util.Utils;
import com.alex.mysticalagriculture.api.lib.AbilityCache;
import com.alex.mysticalagriculture.init.ModItems;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class Augment {
    private final ResourceLocation id;
    private final int tier;
    private final EnumSet<AugmentType> types;
    private final int primaryColor;
    private final int secondaryColor;
    private boolean enabled;

    public Augment(ResourceLocation id, int tier, EnumSet<AugmentType> types, int primaryColor, int secondaryColor) {
        this.id = id;
        this.tier = tier;
        this.types = types;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.enabled = true;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public EnumSet<AugmentType> getAugmentTypes() {
        return this.types;
    }

    public int getTier() {
        return this.tier;
    }

    public Item getItem() {
        return Registry.ITEM.get(new ResourceLocation(MOD_ID, this.getName() + "_augment"));
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

    public boolean onItemUse(UseOnContext context) {
        return false;
    }

    public boolean onRightClick(ItemStack stack, Level world, Player player, InteractionHand hand) {
        return false;
    }

    public boolean onRightClickEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        return false;
    }

    public boolean onHitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return false;
    }

    public boolean onBlockDestroyed(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {
        return false;
    }

    public void onInventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) { }

    public void addToolAttributeModifiers(Multimap<Attribute, AttributeModifier> attributes, EquipmentSlot slot, ItemStack stack) { }

    public void addArmorAttributeModifiers(Multimap<Attribute, AttributeModifier> attributes, EquipmentSlot slot, ItemStack stack) { }

    public String getNameWithSuffix(String suffix) {
        return String.format("%s_%s", this.getName(), suffix);
    }

    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        return false;
    }

    public void onPlayerTick(Level level, Player player, AbilityCache cache) {
    }

    public void onArmorTick(ItemStack stack, Level level, Player player) {
    }

    public void onPlayerFall(Level level, Player player) {
    }

    public void onPlayerFall(Level level, Player player, LivingFallEvent event) { }


    public String getModId() {
        return this.getId().getNamespace();
    }

    public String getName() {
        return this.getId().getPath();
    }

    public MutableComponent getDisplayName() {
        return Component.translatable(String.format("augment.%s.%s", this.getModId(), this.getName()));
    }

    public static ItemStack getEssenceForTinkerable(ITinkerable tinkerable, int min, int max) {
        switch (tinkerable.getTinkerableTier()) {
            case 1: return new ItemStack(ModItems.INFERIUM_ESSENCE, Utils.randInt(min, max));
            case 2: return new ItemStack(ModItems.PRUDENTIUM_ESSENCE, Utils.randInt(min, max));
            case 3: return new ItemStack(ModItems.TERTIUM_ESSENCE, Utils.randInt(min, max));
            case 4: return new ItemStack(ModItems.IMPERIUM_ESSENCE, Utils.randInt(min, max));
            case 5: return new ItemStack(ModItems.SUPREMIUM_ESSENCE, Utils.randInt(min, max));
            default: return ItemStack.EMPTY;
        }
    }
}
