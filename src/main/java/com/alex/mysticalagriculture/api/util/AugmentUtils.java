package com.alex.mysticalagriculture.api.util;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.Tinkerable;
import com.alex.mysticalagriculture.lib.ModAugments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class AugmentUtils {

    public static void addAugment(ItemStack stack, Augment augment, int slot) {
        Item item = stack.getItem();
        if (item instanceof Tinkerable) {
            Tinkerable tinkerable = (Tinkerable) item;
            if (slot < tinkerable.getAugmentSlots() && tinkerable.getTinkerableTier() >= augment.getTier()) {
                CompoundTag nbt = stack.getTag();
                if (nbt == null) {
                    nbt = new CompoundTag();
                    stack.setTag(nbt);
                }

                nbt.putString("Augment-" + slot, augment.getId().toString());
            }
        }
    }

    public static void removeAugment(ItemStack stack, int slot) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null)
            return;

        Item item = stack.getItem();
        if (item instanceof Tinkerable) {
            Tinkerable tinkerable = (Tinkerable) item;
            if (slot < tinkerable.getAugmentSlots() && nbt.contains("Augment-" + slot)) {
                nbt.remove("Augment-" + slot);
            }
        }
    }

    public static Augment getAugment(ItemStack stack, int slot) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null)
            return null;

        Item item = stack.getItem();
        if (item instanceof Tinkerable) {
            Tinkerable tinkerable = (Tinkerable) item;
            if (slot < tinkerable.getAugmentSlots() && nbt.contains("Augment-" + slot)) {
                String name = nbt.getString("Augment-" + slot);
                return ModAugments.getAugmentById(new Identifier(name));
            }
        }

        return null;
    }

    public static List<Augment> getAugments(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        List<Augment> augments = new ArrayList<>();
        if (nbt == null)
            return augments;

        Item item = stack.getItem();
        if (item instanceof Tinkerable) {
            Tinkerable tinkerable = (Tinkerable) item;
            int slots = tinkerable.getAugmentSlots();
            for (int i = 0; i < slots; i++) {
                Augment augment = getAugment(stack, i);
                if (augment != null)
                    augments.add(augment);
            }
        }

        return augments;
    }

    public static List<Augment> getArmorAugments(PlayerEntity player) {
        DefaultedList<ItemStack> armor = player.inventory.armor;
        List<Augment> augments = new ArrayList<>();
        for (ItemStack stack : armor) {
            augments.addAll(getAugments(stack));
        }

        return augments;
    }

    public static Formatting getColorForTier(int tier) {
        switch (tier) {
            case 1: return CropTier.ONE.getTextColor();
            case 2: return CropTier.TWO.getTextColor();
            case 3: return CropTier.THREE.getTextColor();
            case 4: return CropTier.FOUR.getTextColor();
            case 5: return CropTier.FIVE.getTextColor();
            default: return Formatting.GRAY;
        }
    }

    public static Text getTooltipForTier(int tier) {
        return new LiteralText(String.valueOf(tier)).formatted(getColorForTier(tier));
    }
}
