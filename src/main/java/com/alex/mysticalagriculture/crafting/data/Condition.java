package com.alex.mysticalagriculture.crafting.data;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.lib.ModCrops;

public class Condition {

    private String crop = "";

    public Condition(String crop) {
        this.crop = crop;
    }

    public boolean verify() {
        if (!crop.isEmpty()) {
            Crop crop = ModCrops.getCropById(this.crop);
            return crop != null && crop.isEnabled();
        }
        return false;
    }
}
