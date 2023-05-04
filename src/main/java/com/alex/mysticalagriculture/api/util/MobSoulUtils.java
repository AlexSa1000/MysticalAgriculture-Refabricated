package com.alex.mysticalagriculture.api.util;

import com.alex.mysticalagriculture.api.MysticalAgricultureAPI;
import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.alex.mysticalagriculture.lib.ModMobSoulTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class MobSoulUtils {

    public static NbtCompound makeTag(MobSoulType type) {
        return makeTag(type, type.getSoulRequirement());
    }

    public static NbtCompound makeTag(MobSoulType type, double souls) {
        var nbt = new NbtCompound();
        nbt.putString("Type", type.getId().toString());
        nbt.putDouble("Souls", Math.min(souls, type.getSoulRequirement()));
        return nbt;
    }

    public static ItemStack getSoulJar(MobSoulType type, double souls, Item item) {
        var nbt = makeTag(type, souls);
        var stack = new ItemStack(item);
        stack.setNbt(nbt);

        return stack;
    }

    public static ItemStack getFilledSoulJar(MobSoulType type, Item item) {
        var nbt = makeTag(type);
        var stack = new ItemStack(item);
        stack.setNbt(nbt);

        return stack;
    }

    public static MobSoulType getType(ItemStack stack) {
        var nbt = stack.getNbt();
        if (nbt != null && nbt.contains("Type")) {
            var type = nbt.getString("Type");
            return MysticalAgricultureAPI.getMobSoulTypeRegistry().getMobSoulTypeById(new Identifier(type));
        }

        return null;
    }

    public static double getSouls(ItemStack stack) {
        var nbt = stack.getNbt();
        if (nbt != null && nbt.contains("Souls"))
            return nbt.getDouble("Souls");

        return 0D;
    }

    public static boolean canAddTypeToJar(ItemStack stack, MobSoulType type) {
        var containedType = getType(stack);
        return containedType == null || containedType == type;
    }

    public static boolean isJarFull(ItemStack stack) {
        var type = getType(stack);
        return type != null && getSouls(stack) >= type.getSoulRequirement();
    }

    public static double addSoulsToJar(ItemStack stack, MobSoulType type, double amount) {
        var containedType = getType(stack);
        if (containedType != null && containedType != type)
            return amount;

        double requirement = type.getSoulRequirement();
        if (containedType == null) {
            var nbt = makeTag(type, amount);
            stack.setNbt(nbt);

            return Math.max(0, amount - requirement);
        } else {
            double souls = getSouls(stack);
            if (souls < requirement) {
                var nbt = stack.getNbt();

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
