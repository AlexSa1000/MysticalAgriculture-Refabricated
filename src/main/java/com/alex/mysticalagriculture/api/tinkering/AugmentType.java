package com.alex.mysticalagriculture.api.tinkering;

import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public enum AugmentType {
    TOOL("tool"),
    WEAPON("weapon"),
    ARMOR("armor"),
    STAFF("staff"),
    SWORD("sword"),
    PICKAXE("pickaxe"),
    SHOVEL("shovel"),
    AXE("axe"),
    HOE("hoe"),
    BOW("bow"),
    CROSSBOW("crossbow"),
    SHEARS("shears"),
    FISHING_ROD("fishing_rod"),
    SICKLE("sickle"),
    SCYTHE("scythe"),
    HELMET("helmet"),
    CHESTPLATE("chestplate"),
    LEGGINGS("leggings"),
    BOOTS("boots");

    private static final Map<String, AugmentType> LOOKUP = new HashMap<>();
    private final String name;

    static {
        for (var value : values()) {
            LOOKUP.put(value.name, value);
        }
    }

    AugmentType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Text getDisplayName() {
        return Text.translatable("augmentType.mysticalagriculture." + this.name);
    }

    public static AugmentType fromName(String name) {
        return LOOKUP.get(name);
    }
}

