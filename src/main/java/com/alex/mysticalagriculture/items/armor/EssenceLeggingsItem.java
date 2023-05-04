package com.alex.mysticalagriculture.items.armor;

import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.mysticalagriculture.api.tinkering.Tinkerable;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.zucchini.item.BaseArmorItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
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

public class EssenceLeggingsItem extends BaseArmorItem implements Tinkerable {
    private static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };
    private static final EnumSet<AugmentType> TYPES = EnumSet.of(AugmentType.ARMOR, AugmentType.LEGGINGS);
    private final int tinkerableTier;
    private final int slots;

    public EssenceLeggingsItem(ArmorMaterial material, int tinkerableTier, int slots) {
        super(material, Type.LEGGINGS);
        this.tinkerableTier = tinkerableTier;
        this.slots = slots;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity && slot == 1) {
            AugmentUtils.getAugments(stack).forEach(a -> a.onArmorTick(stack, world, (PlayerEntity) entity));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(ModTooltips.getTooltipForTier(this.tinkerableTier));

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
}
