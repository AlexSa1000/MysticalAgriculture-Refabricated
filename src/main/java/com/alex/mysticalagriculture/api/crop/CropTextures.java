package com.alex.mysticalagriculture.api.crop;

import net.minecraft.util.Identifier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class CropTextures {
    public static final Identifier FLOWER_INGOT_BLANK = new Identifier(MOD_ID, "block/flower_ingot");
    public static final Identifier FLOWER_ROCK_BLANK = new Identifier(MOD_ID, "block/flower_rock");
    public static final Identifier FLOWER_DUST_BLANK = new Identifier(MOD_ID, "block/flower_dust");
    public static final Identifier FLOWER_FACE_BLANK = new Identifier(MOD_ID, "block/flower_face");

    public static final Identifier ESSENCE_INGOT_BLANK = new Identifier(MOD_ID, "item/essence_ingot");
    public static final Identifier ESSENCE_ROCK_BLANK = new Identifier(MOD_ID, "item/essence_rock");
    public static final Identifier ESSENCE_DUST_BLANK = new Identifier(MOD_ID, "item/essence_dust");
    public static final Identifier ESSENCE_GEM_BLANK = new Identifier(MOD_ID, "item/essence_gem");
    public static final Identifier ESSENCE_TALL_GEM_BLANK = new Identifier(MOD_ID, "item/essence_tall_gem");
    public static final Identifier ESSENCE_DIAMOND_BLANK = new Identifier(MOD_ID, "item/essence_diamond");
    public static final Identifier ESSENCE_QUARTZ_BLANK = new Identifier(MOD_ID, "item/essence_quartz");
    public static final Identifier ESSENCE_FLAME_BLANK = new Identifier(MOD_ID, "item/essence_flame");
    public static final Identifier ESSENCE_ROD_BLANK = new Identifier(MOD_ID, "item/essence_rod");

    public static final Identifier SEED_BLANK = new Identifier(MOD_ID, "item/mystical_seeds");

    public static final CropTextures INGOT_CROP_TEXTURES = new CropTextures(FLOWER_INGOT_BLANK, ESSENCE_INGOT_BLANK);
    public static final CropTextures ROCK_CROP_TEXTURES = new CropTextures(FLOWER_ROCK_BLANK, ESSENCE_ROCK_BLANK);
    public static final CropTextures DUST_CROP_TEXTURES = new CropTextures(FLOWER_DUST_BLANK, ESSENCE_DUST_BLANK);
    public static final CropTextures GEM_CROP_TEXTURES = new CropTextures(FLOWER_ROCK_BLANK, ESSENCE_GEM_BLANK);
    public static final CropTextures ELEMENTAL_CROP_TEXTURES = new CropTextures(FLOWER_INGOT_BLANK, ESSENCE_FLAME_BLANK);

    private Identifier flowerTexture;
    private Identifier essenceTexture;
    private Identifier seedTexture;

    public CropTextures() {
        this(null, null, null);
    }

    public CropTextures(Identifier flowerTexture, Identifier essenceTexture) {
        this(flowerTexture, essenceTexture, SEED_BLANK);
    }

    public CropTextures(Identifier flowerTexture, Identifier essenceTexture, Identifier seedTexture) {
        this.flowerTexture = flowerTexture;
        this.essenceTexture = essenceTexture;
        this.seedTexture = seedTexture;
    }

    public Identifier getFlowerTexture() {
        return this.flowerTexture;
    }

    public CropTextures setFlowerTexture(Identifier location) {
        this.flowerTexture = location;
        return this;
    }

    public Identifier getEssenceTexture() {
        return this.essenceTexture;
    }

    public CropTextures setEssenceTexture(Identifier location) {
        this.essenceTexture = location;
        return this;
    }

    public Identifier getSeedTexture() {
        return this.seedTexture;
    }

    public CropTextures setSeedTexture(Identifier location) {
        this.seedTexture = location;
        return this;
    }

    public CropTextures init(Identifier id) {
        var modid = id.getNamespace();
        var name = id.getPath();

        if (this.flowerTexture == null)
            this.flowerTexture = new Identifier(modid, "block/flower/" + name + "_flower");
        if (this.essenceTexture == null)
            this.essenceTexture = new Identifier(modid, "item/essence/" + name + "_essence");
        if (this.seedTexture == null)
            this.seedTexture = new Identifier(modid, "item/seeds/" + name + "_seeds");

        return this;
    }
}
