package com.alex.mysticalagriculture.cucumber.util;

import net.minecraft.util.Formatting;

public class Tooltip extends Localizable {
    public Tooltip(String key) {
        super(key, Formatting.GRAY);
    }

    public Tooltip(String key, Formatting defaultColor) {
        super(key, defaultColor);
    }
}
