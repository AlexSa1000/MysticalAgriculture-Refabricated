package com.alex.mysticalagriculture.items.armor;

import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.mysticalagriculture.api.tinkering.ITinkerable;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.item.BaseArmorItem;
import com.alex.mysticalagriculture.cucumber.util.Utils;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

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
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && slot == 2) {
            AugmentUtils.getAugments(stack).forEach(a -> a.onArmorTick(stack, world, (PlayerEntity) entity));

            if (ModConfigs.AWAKENED_SUPREMIUM_SET_BONUS.get() && !world.isClient() && world.getTime() % 20L == 0 && hasAwakenedSupremiumSet(player)) {
                handleGrowthTicks(world, player);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(ModTooltips.getTooltipForTier(this.tinkerableTier));

        if (ModConfigs.AWAKENED_SUPREMIUM_SET_BONUS.get() && stack.isOf(Items.AWAKENED_SUPREMIUM_CHESTPLATE)) {
            tooltip.add(ModTooltips.SET_BONUS.args(ModTooltips.AWAKENED_SUPREMIUM_SET_BONUS.build()).build());
        }

        AugmentUtils.getAugments(stack).forEach(a -> tooltip.add(a.getDisplayName().formatted(Formatting.GRAY)));
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

    private static boolean hasAwakenedSupremiumSet(PlayerEntity player) {
        var helmet = player.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.AWAKENED_SUPREMIUM_HELMET);
        var chestplate = player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.AWAKENED_SUPREMIUM_CHESTPLATE);
        var leggings = player.getEquippedStack(EquipmentSlot.LEGS).isOf(Items.AWAKENED_SUPREMIUM_LEGGINGS);
        var boots = player.getEquippedStack(EquipmentSlot.FEET).isOf(Items.AWAKENED_SUPREMIUM_BOOTS);

        return helmet && chestplate && leggings && boots;
    }

    private static void handleGrowthTicks(World world, PlayerEntity player) {
        var pos = player.getBlockPos();
        int range = 5;

        BlockPos.stream(pos.add(-range, -range, -range), pos.add(range, range, range)).forEach(aoePos -> {
            if (Math.random() < 0.5)
                return;

            var state = world.getBlockState(aoePos);
            var plantBlock = state.getBlock();

            if (plantBlock instanceof StemBlock || plantBlock instanceof SugarCaneBlock) {

                state.randomTick((ServerWorld) world, aoePos, Utils.RANDOM);

                double d0 = aoePos.getX() + world.getRandom().nextFloat();
                double d1 = aoePos.getY();
                double d2 = aoePos.getZ() + world.getRandom().nextFloat();

                ((ServerWorld) world).spawnParticles(ParticleTypes.HAPPY_VILLAGER, d0, d1, d2, 1, 0, 0, 0, 0.1D);
            }
        });
    }
}
