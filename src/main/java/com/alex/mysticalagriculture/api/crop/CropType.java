package com.alex.mysticalagriculture.api.crop;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class CropType {
    public static final CropType RESOURCE = new CropType("resource", new Identifier(MOD_ID, "block/mystical_resource_crop"));
    public static final CropType MOB = new CropType("mob", new Identifier(MOD_ID, "block/mystical_mob_crop"));

    private final String name;
    private final Identifier stemModel;
    private Item craftingSeed;

    public CropType(String name, Identifier stemModel) {
        this.name = name;
        this.stemModel = stemModel;
    }

    public String getName() {
        return this.name;
    }

    public Item getCraftingSeed() {
        return this.craftingSeed == null ? null : this.craftingSeed;
    }

    public void setCraftingSeed(Item item) {
        this.craftingSeed = item;
    }
}
