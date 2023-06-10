package com.alex.mysticalagriculture.api.crop;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class CropType {
    public static final CropType RESOURCE = new CropType(new ResourceLocation(MOD_ID, "resource"), new ResourceLocation(MOD_ID, "block/mystical_resource_crop"));
    public static final CropType MOB = new CropType(new ResourceLocation(MOD_ID, "mob"), new ResourceLocation(MOD_ID, "block/mystical_mob_crop"));

    private final ResourceLocation id;
    private final ResourceLocation stemModel;
    private Item craftingSeed;

    public CropType(ResourceLocation id, ResourceLocation stemModel) {
        this.id = id;
        this.stemModel = stemModel;
    }

    public ResourceLocation getId() {
        return id;
    }

    public String getName() {
        return this.id.getPath();
    }

    public String getModId() {
        return this.id.getNamespace();
    }

    public ResourceLocation getStemModel() {
        return this.stemModel;
    }

    public Item getCraftingSeed() {
        return this.craftingSeed == null ? null : this.craftingSeed;
    }
    public void setCraftingSeed(Item item) {
        this.craftingSeed = item;
    }

}
