package com.alex.mysticalagriculture.api.util;

import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.alex.mysticalagriculture.lib.ModMobSoulTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class MobSoulUtils {

    public static CompoundTag makeTag(MobSoulType type) {
        return makeTag(type, type.getSoulRequirement());
    }

    public static CompoundTag makeTag(MobSoulType type, double souls) {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Type", type.getId().toString());
        nbt.putDouble("Souls", Math.min(souls, type.getSoulRequirement()));
        return nbt;
    }

    public static ItemStack getSoulJar(MobSoulType type, double souls, Item item) {
        CompoundTag nbt = makeTag(type, souls);
        ItemStack stack = new ItemStack(item);
        stack.setTag(nbt);

        return stack;
    }

    public static ItemStack getFilledSoulJar(MobSoulType type, Item item) {
        CompoundTag nbt = makeTag(type);
        ItemStack stack = new ItemStack(item);
        stack.setTag(nbt);

        return stack;
    }

    public static MobSoulType getType(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("Type")) {
            String type = nbt.getString("Type");
            return ModMobSoulTypes.getMobSoulTypeById(new Identifier(type));
        }

        return null;
    }

    public static double getSouls(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("Souls"))
            return nbt.getDouble("Souls");

        return 0D;
    }

    public static boolean canAddTypeToJar(ItemStack stack, MobSoulType type) {
        MobSoulType containedType = getType(stack);
        return containedType == null || containedType == type;
    }

    public static double addSoulsToJar(ItemStack stack, MobSoulType type, double amount) {
        MobSoulType containedType = getType(stack);
        if (containedType != null && containedType != type)
            return amount;

        double requirement = type.getSoulRequirement();
        if (containedType == null) {
            CompoundTag nbt = makeTag(type, amount);
            stack.setTag(nbt);

            return Math.max(0, amount - requirement);
        } else {
            double souls = getSouls(stack);
            if (souls < requirement) {
                CompoundTag nbt = stack.getTag();

                if (nbt != null) {
                    double newSouls = Math.min(requirement, souls + amount);
                    nbt.putDouble("Souls", newSouls);

                    return Math.max(0, amount - (newSouls - souls));
                }
            }
        }

        return amount;
    }
}
