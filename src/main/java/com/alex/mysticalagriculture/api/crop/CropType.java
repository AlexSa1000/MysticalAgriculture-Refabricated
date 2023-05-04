package com.alex.mysticalagriculture.api.crop;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class CropType {
    public static final CropType RESOURCE = new CropType(new Identifier(MOD_ID, "resource"), new Identifier(MOD_ID, "block/mystical_resource_crop"));
    public static final CropType MOB = new CropType(new Identifier(MOD_ID, "mob"), new Identifier(MOD_ID, "block/mystical_mob_crop"));

    private final Identifier id;
    private final Identifier stemModel;
    private Item craftingSeed;

    public CropType(Identifier id, Identifier stemModel) {
        this.id = id;
        this.stemModel = stemModel;
    }

    public Identifier getId() {
        return id;
    }

    public String getName() {
        return this.id.getPath();
    }

    public String getModId() {
        return this.id.getNamespace();
    }

    public Identifier getStemModel() {
        return this.stemModel;
    }

    public Item getCraftingSeed() {
        return this.craftingSeed == null ? null : this.craftingSeed;
    }
    public void setCraftingSeed(Item item) {
        this.craftingSeed = item;
    }

}
