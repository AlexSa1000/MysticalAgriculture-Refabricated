package com.alex.mysticalagriculture.api.tinkerer;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public enum AugmentType {
    TOOL("tool"),
    WEAPON("weapon"),
    ARMOR("armor"),
    SWORD("sword"),
    PICKAXE("pickaxe"),
    SHOVEL("shovel"),
    AXE("axe"),
    HOE("hoe"),
    HELMET("helmet"),
    CHESTPLATE("chestplate"),
    LEGGINGS("leggings"),
    BOOTS("boots");

    private final String name;

    AugmentType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Text getDisplayName() {
        return new TranslatableText("augmentType.mysticalagriculture." + this.name);
    }
}

