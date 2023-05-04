package com.alex.mysticalagriculture.crafting.condition;

import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class CraftingConditionsImpl {

    // Providers

    public static ConditionJsonProvider cropEnabled(Identifier id, ConditionJsonProvider value) {
        return new ConditionJsonProvider() {
            public void writeParameters(JsonObject object) {
                object.add("crop", value.toJson());
            }

            public Identifier getConditionId() {
                return id;
            }
        };
    }

    public static ConditionJsonProvider cropHasMaterial(Identifier id, ConditionJsonProvider value) {
        return new ConditionJsonProvider() {
            public void writeParameters(JsonObject object) {
                object.add("crop", value.toJson());
            }

            public Identifier getConditionId() {
                return id;
            }
        };
    }


    // Condition implementations

    public static boolean cropEnabledMatch(JsonObject json) {
        var crop = CropRegistry.getInstance().getCropById(new Identifier(JsonHelper.getString(json, "crop")));
        return crop != null && crop.isEnabled();
    }

    public static boolean cropHasMaterialMatch(JsonObject json) {
        var crop = CropRegistry.getInstance().getCropById(new Identifier(JsonHelper.getString(json, "crop")));
        if (crop == null)
            return false;

        var material = crop.getCraftingMaterial();

        return material != null && !material.isEmpty();
    }
}
