package com.alex.mysticalagriculture.api.util;

import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.Tinkerable;
import com.alex.mysticalagriculture.lib.ModAugments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
                NbtCompound nbt = stack.getNbt();
                if (nbt == null) {
                    nbt = new NbtCompound();
                    stack.setNbt(nbt);
                }

                nbt.putString("Augment-" + slot, augment.getId().toString());
            }
        }
    }

    public static void removeAugment(ItemStack stack, int slot) {
        NbtCompound nbt = stack.getNbt();
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
        NbtCompound nbt = stack.getNbt();
        if (nbt == null)
            return null;

        Item item = stack.getItem();
        if (item instanceof Tinkerable) {
            Tinkerable tinkerable = (Tinkerable) item;
            if (slot < tinkerable.getAugmentSlots() && nbt.contains("Augment-" + slot)) {
                String name = nbt.getString("Augment-" + slot);
                return MysticalAgricultureAPI.getAugmentRegistry().getAugmentById(new Identifier(name));
            }
        }

        return null;
    }

    public static List<Augment> getAugments(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
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
        DefaultedList<ItemStack> armor = player.getInventory().armor;
        List<Augment> augments = new ArrayList<>();
        for (ItemStack stack : armor) {
            augments.addAll(getAugments(stack));
        }

        return augments;
    }

    public static Formatting getColorForTier(int tier) {
        return switch (tier) {
            case 1 -> CropTier.ONE.getTextColor();
            case 2 -> CropTier.TWO.getTextColor();
            case 3 -> CropTier.THREE.getTextColor();
            case 4 -> CropTier.FOUR.getTextColor();
            case 5 -> CropTier.FIVE.getTextColor();
            default -> Formatting.GRAY;
        };
    }

    public static Text getTooltipForTier(int tier) {
        return Text.literal(String.valueOf(tier)).formatted(getColorForTier(tier));
    }
}
