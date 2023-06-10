package com.alex.mysticalagriculture.crafting.condition;

import com.alex.mysticalagriculture.registry.CropRegistry;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class CraftingConditionsImpl {

    // Providers

    public static ConditionJsonProvider cropEnabled(ResourceLocation id, ConditionJsonProvider value) {
        return new ConditionJsonProvider() {
            public void writeParameters(JsonObject object) {
                object.add("crop", value.toJson());
            }

            public ResourceLocation getConditionId() {
                return id;
            }
        };
    }

    public static ConditionJsonProvider cropHasMaterial(ResourceLocation id, ConditionJsonProvider value) {
        return new ConditionJsonProvider() {
            public void writeParameters(JsonObject object) {
                object.add("crop", value.toJson());
            }

            public ResourceLocation getConditionId() {
                return id;
            }
        };
    }


    // Condition implementations

    public static boolean cropEnabledMatch(JsonObject json) {
        var crop = CropRegistry.getInstance().getCropById(new ResourceLocation(GsonHelper.getAsString(json, "crop")));
        return crop != null && crop.isEnabled();
    }

    public static boolean cropHasMaterialMatch(JsonObject json) {
        var crop = CropRegistry.getInstance().getCropById(new ResourceLocation(GsonHelper.getAsString(json, "crop")));
        if (crop == null)
            return false;

        var material = crop.getCraftingMaterial();

        return material != null && !material.isEmpty();
    }
}
