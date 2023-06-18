package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.api.crop.CropTextures;
import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.crop.CropType;
import com.alex.mysticalagriculture.api.lib.LazyIngredient;
import com.alex.mysticalagriculture.api.registry.ICropRegistry;
import com.alex.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.init.Blocks;
import com.alex.mysticalagriculture.init.Items;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CropBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ModCrops {
    private static final List<Crop> crops = new ArrayList<>();


    public static final Crop AIR = new Crop(new ResourceLocation(MOD_ID, "air"), CropTier.ELEMENTAL, CropType.RESOURCE, CropTextures.ELEMENTAL_CROP_TEXTURES,0xDAD64D, LazyIngredient.item("mysticalagriculture:air_agglomeratio"));
    public static final Crop EARTH = new Crop(new ResourceLocation(MOD_ID, "earth"), CropTier.ELEMENTAL, CropType.RESOURCE,CropTextures.ELEMENTAL_CROP_TEXTURES, 0x54DA4D, LazyIngredient.item("mysticalagriculture:earth_agglomeratio"));
    public static final Crop WATER = new Crop(new ResourceLocation(MOD_ID, "water"), CropTier.ELEMENTAL, CropType.RESOURCE,CropTextures.ELEMENTAL_CROP_TEXTURES, 0x4D7EDA, LazyIngredient.item("mysticalagriculture:water_agglomeratio"));
    public static final Crop FIRE = new Crop(new ResourceLocation(MOD_ID, "fire"), CropTier.ELEMENTAL, CropType.RESOURCE,CropTextures.ELEMENTAL_CROP_TEXTURES, 0xDA4D4D, LazyIngredient.item("mysticalagriculture:fire_agglomeratio"));

    public static final Crop INFERIUM = new Crop(new ResourceLocation(MOD_ID, "inferium"), CropTier.ONE, CropType.RESOURCE, LazyIngredient.EMPTY);
    public static final Crop STONE = new Crop(new ResourceLocation(MOD_ID, "stone"), CropTier.ONE, CropType.RESOURCE, LazyIngredient.item("minecraft:stone"));
    public static final Crop DIRT = new Crop(new ResourceLocation(MOD_ID, "dirt"), CropTier.ONE, CropType.RESOURCE, LazyIngredient.item("minecraft:dirt"));
    public static final Crop WOOD = new Crop(new ResourceLocation(MOD_ID, "wood"), CropTier.ONE, CropType.RESOURCE, LazyIngredient.tag("minecraft:logs"));
    public static final Crop ICE = new Crop(new ResourceLocation(MOD_ID, "ice"), CropTier.ONE, CropType.RESOURCE, LazyIngredient.item("minecraft:ice"));
    public static final Crop DEEPSLATE = new Crop(new ResourceLocation(MOD_ID, "deepslate"), CropTier.ONE, CropType.RESOURCE, LazyIngredient.item("minecraft:deepslate"));

    public static final Crop NATURE = new Crop(new ResourceLocation(MOD_ID, "nature"), CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:nature_agglomeratio"));
    public static final Crop DYE = new Crop(new ResourceLocation(MOD_ID, "dye"), CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:dye_agglomeratio"));
    public static final Crop NETHER = new Crop(new ResourceLocation(MOD_ID, "nether"), CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:nether_agglomeratio"));
    public static final Crop COAL = new Crop(new ResourceLocation(MOD_ID, "coal"), CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("minecraft:coal"));
    public static final Crop CORAL = new Crop(new ResourceLocation(MOD_ID, "coral"), CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:coral_agglomeratio"));
    public static final Crop HONEY = new Crop(new ResourceLocation(MOD_ID, "honey"), CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:honey_agglomeratio"));
    public static final Crop AMETHYST = new Crop(new ResourceLocation(MOD_ID, "amethyst"), CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("minecraft:amethyst_shard"));
    public static final Crop PIG = new Crop(new ResourceLocation(MOD_ID, "pig"), CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.PIG)));
    public static final Crop CHICKEN = new Crop(new ResourceLocation(MOD_ID, "chicken"), CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.CHICKEN)));
    public static final Crop COW = new Crop(new ResourceLocation(MOD_ID, "cow"), CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.COW)));
    public static final Crop SHEEP = new Crop(new ResourceLocation(MOD_ID, "sheep"), CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.SHEEP)));
    public static final Crop SQUID = new Crop(new ResourceLocation(MOD_ID, "squid"), CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.SQUID)));
    public static final Crop FISH = new Crop(new ResourceLocation(MOD_ID, "fish"), CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.FISH)));
    public static final Crop SLIME = new Crop(new ResourceLocation(MOD_ID, "slime"), CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.SLIME)));
    public static final Crop TURTLE = new Crop(new ResourceLocation(MOD_ID, "turtle"), CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.TURTLE)));

    public static final Crop IRON = new Crop(new ResourceLocation(MOD_ID, "iron"), CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("minecraft:iron_ingot"));
    public static final Crop COPPER = new Crop(new ResourceLocation(MOD_ID, "copper"), CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("minecraft:copper_ingot"));
    public static final Crop NETHER_QUARTZ = new Crop(new ResourceLocation(MOD_ID, "nether_quartz"), CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("minecraft:quartz"));
    public static final Crop GLOWSTONE = new Crop(new ResourceLocation(MOD_ID, "glowstone"), CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("minecraft:glowstone"));
    public static final Crop REDSTONE = new Crop(new ResourceLocation(MOD_ID, "redstone"), CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("minecraft:redstone"));
    public static final Crop OBSIDIAN = new Crop(new ResourceLocation(MOD_ID, "obsidian"), CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("minecraft:obsidian"));
    public static final Crop PRISMARINE = new Crop(new ResourceLocation(MOD_ID, "prismarine"), CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:prismarine_agglomeratio"));
    public static final Crop ZOMBIE = new Crop(new ResourceLocation(MOD_ID, "zombie"), CropTier.THREE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.ZOMBIE)));
    public static final Crop SKELETON = new Crop(new ResourceLocation(MOD_ID, "skeleton"), CropTier.THREE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.SKELETON)));
    public static final Crop CREEPER = new Crop(new ResourceLocation(MOD_ID, "creeper"), CropTier.THREE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.CREEPER)));
    public static final Crop SPIDER = new Crop(new ResourceLocation(MOD_ID, "spider"), CropTier.THREE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.SPIDER)));
    public static final Crop RABBIT = new Crop(new ResourceLocation(MOD_ID, "rabbit"), CropTier.THREE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.RABBIT)));

    public static final Crop GOLD = new Crop(new ResourceLocation(MOD_ID, "gold"), CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("minecraft:gold_ingot"));
    public static final Crop LAPIS_LAZULI = new Crop(new ResourceLocation(MOD_ID, "lapis_lazuli"), CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("minecraft:lapis_lazuli"));
    public static final Crop END = new Crop(new ResourceLocation(MOD_ID, "end"), CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:end_agglomeratio"));
    public static final Crop EXPERIENCE = new Crop(new ResourceLocation(MOD_ID, "experience"), CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:experience_capsule", ExperienceCapsuleUtils.makeTag(ExperienceCapsuleUtils.MAX_XP_POINTS)));
    public static final Crop BLAZE = new Crop(new ResourceLocation(MOD_ID, "blaze"), CropTier.FOUR, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.BLAZE)));
    public static final Crop GHAST = new Crop(new ResourceLocation(MOD_ID, "ghast"), CropTier.FOUR, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.GHAST)));
    public static final Crop ENDERMAN = new Crop(new ResourceLocation(MOD_ID, "enderman"), CropTier.FOUR, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.ENDERMAN)));

    public static final Crop DIAMOND = new Crop(new ResourceLocation(MOD_ID, "diamond"), CropTier.FIVE, CropType.RESOURCE, LazyIngredient.item("minecraft:diamond"));
    public static final Crop EMERALD = new Crop(new ResourceLocation(MOD_ID, "emerald"), CropTier.FIVE, CropType.RESOURCE, LazyIngredient.item("minecraft:emerald"));
    public static final Crop NETHERITE = new Crop(new ResourceLocation(MOD_ID, "netherite"), CropTier.FIVE, CropType.RESOURCE, LazyIngredient.item("minecraft:netherite_ingot"));
    public static final Crop WITHER_SKELETON = new Crop(new ResourceLocation(MOD_ID, "wither_skeleton"), CropTier.FIVE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.WITHER)));

    //Common
    public static final Crop SULFUR = new Crop(new ResourceLocation(MOD_ID, "sulfur"), CropTier.TWO, CropType.RESOURCE, CropTextures.DUST_CROP_TEXTURES, 0xFDDC4B, LazyIngredient.tag("c:sulfur_dusts"));
    public static final Crop ALUMINUM = new Crop(new ResourceLocation(MOD_ID, "aluminum"), CropTier.TWO, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xA4A6B1, LazyIngredient.tag("c:aluminum_ingots"));
    public static final Crop TIN = new Crop(new ResourceLocation(MOD_ID, "tin"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x9ABDD6, LazyIngredient.tag("c:tin_ingots"));
    public static final Crop BRONZE = new Crop(new ResourceLocation(MOD_ID, "bronze"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xC98C52, LazyIngredient.tag("c:bronze_ingots"));
    public static final Crop ZINC = new Crop(new ResourceLocation(MOD_ID, "zinc"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xE9EBE7, LazyIngredient.tag("c:zinc_ingots"));
    public static final Crop BRASS = new Crop(new ResourceLocation(MOD_ID, "brass"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xDAAA4C, LazyIngredient.tag("c:brass_ingots"));
    public static final Crop SILVER = new Crop(new ResourceLocation(MOD_ID, "silver"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xA9DBE5, LazyIngredient.tag("c:silver_ingots"));
    public static final Crop LEAD = new Crop(new ResourceLocation(MOD_ID, "lead"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x677193, LazyIngredient.tag("c:lead_ingots"));
    public static final Crop STEEL = new Crop(new ResourceLocation(MOD_ID, "steel"), CropTier.FOUR, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x737373, LazyIngredient.tag("c:steel_ingots"));
    public static final Crop NICKEL = new Crop(new ResourceLocation(MOD_ID, "nickel"), CropTier.FOUR, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xD8CC93, LazyIngredient.tag("c:nickel_ingots"));
    public static final Crop ELECTRUM = new Crop(new ResourceLocation(MOD_ID, "electrum"), CropTier.FOUR, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xD5BB4F, LazyIngredient.tag("c:electrum_ingots"));
    public static final Crop INVAR = new Crop(new ResourceLocation(MOD_ID, "invar"), CropTier.FOUR, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xADB7B2, LazyIngredient.tag("c:invar_ingots"));
    public static final Crop TUNGSTEN = new Crop(new ResourceLocation(MOD_ID, "tungsten"), CropTier.FOUR, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x616669, LazyIngredient.tag("c:tungsten_ingots"));
    public static final Crop TITANIUM = new Crop(new ResourceLocation(MOD_ID, "titanium"), CropTier.FOUR, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xD0D1DA, LazyIngredient.tag("c:titanium_ingots"));
    public static final Crop CHROMIUM = new Crop(new ResourceLocation(MOD_ID, "chromium"), CropTier.FOUR, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xCDB9BD, LazyIngredient.tag("c:chromium_ingots"));
    public static final Crop PLATINUM = new Crop(new ResourceLocation(MOD_ID, "platinum"), CropTier.FIVE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x6FEAEF, LazyIngredient.tag("c:platinum_ingots"));
    public static final Crop IRIDIUM = new Crop(new ResourceLocation(MOD_ID, "iridium"), CropTier.FIVE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xC7C5DC, LazyIngredient.tag("c:iridium_ingots"));

    //Gems
    public static final Crop RUBY = new Crop(new ResourceLocation(MOD_ID, "ruby"), CropTier.FOUR, CropType.RESOURCE, LazyIngredient.tag("c:rubies"));
    public static final Crop SAPPHIRE = new Crop(new ResourceLocation(MOD_ID, "sapphire"), CropTier.FOUR, CropType.RESOURCE, LazyIngredient.tag("c:sapphires"));

    //Industrial Revolution
    public static final Crop NIKOLITE = new Crop(new ResourceLocation(MOD_ID, "nikolite"), CropTier.THREE, CropType.RESOURCE, CropTextures.DUST_CROP_TEXTURES, 0x006D6B, LazyIngredient.item("indrev:nikolite_dust"));

    //Modern Industrialization
    public static final Crop ANTIMONY = new Crop(new ResourceLocation(MOD_ID, "antimony"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x81A9A1, LazyIngredient.item("modern_industrialization:antimony_ingot"));

    //AE2
    public static final Crop SKY_STONE = new Crop(new ResourceLocation(MOD_ID, "sky_stone"), CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("ae2:sky_stone_block"));
    public static final Crop CERTUS_QUARTZ = new Crop(new ResourceLocation(MOD_ID, "certus_quartz"), CropTier.THREE, CropType.RESOURCE, LazyIngredient.tag("ae2:all_certus_quartz"));
    public static final Crop FLUIX = new Crop(new ResourceLocation(MOD_ID, "fluix"), CropTier.FOUR, CropType.RESOURCE, LazyIngredient.tag("ae2:all_fluix"));

    //Tech Reborn
    public static final Crop RUBBER = new Crop(new ResourceLocation(MOD_ID, "rubber"), CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("techreborn:rubber"));
    public static final Crop PERIDOT = new Crop(new ResourceLocation(MOD_ID, "peridot"), CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("techreborn:peridot_gem"));

    //Mythic Metals
    public static final Crop ADAMANTITE = new Crop(new ResourceLocation(MOD_ID, "adamantite"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xBD0000, LazyIngredient.item("mythicmetals:adamantite_ingot"));
    public static final Crop AQUARIUM = new Crop(new ResourceLocation(MOD_ID, "aquarium"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x4096E4, LazyIngredient.item("mythicmetals:aquarium_ingot"));
    public static final Crop BANGLUM = new Crop(new ResourceLocation(MOD_ID, "banglum"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x985924, LazyIngredient.item("mythicmetals:banglum_ingot"));
    public static final Crop CARMOT = new Crop(new ResourceLocation(MOD_ID, "carmot"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xE63B73, LazyIngredient.item("mythicmetals:carmot_ingot"));
    public static final Crop CELESTIUM = new Crop(new ResourceLocation(MOD_ID, "celestium"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xECACA8, LazyIngredient.item("mythicmetals:celestium_ingot"));
    public static final Crop DURASTEEL = new Crop(new ResourceLocation(MOD_ID, "durasteel"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x373737, LazyIngredient.item("mythicmetals:durasteel_ingot"));
    public static final Crop HALLOWED = new Crop(new ResourceLocation(MOD_ID, "hallowed"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xE7E5A3, LazyIngredient.item("mythicmetals:hallowed_ingot"));
    public static final Crop KYBER = new Crop(new ResourceLocation(MOD_ID, "kyber"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xB476DA, LazyIngredient.item("mythicmetals:kyber_ingot"));
    public static final Crop MANGANESE = new Crop(new ResourceLocation(MOD_ID, "manganese"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xC99BB4, LazyIngredient.item("mythicmetals:manganese_ingot"));
    public static final Crop METALLURGIUM = new Crop(new ResourceLocation(MOD_ID, "metallurgium"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x5B1CBE, LazyIngredient.item("mythicmetals:metallurgium_ingot"));
    public static final Crop MIDAS_GOLD = new Crop(new ResourceLocation(MOD_ID, "midas_gold"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xFFE182, LazyIngredient.item("mythicmetals:midas_gold_ingot"));
    public static final Crop MYTHRIL = new Crop(new ResourceLocation(MOD_ID, "mythril"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x10959C, LazyIngredient.item("mythicmetals:mythril_ingot"));
    public static final Crop ORICHALCUM = new Crop(new ResourceLocation(MOD_ID, "orichalcum"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x62B468, LazyIngredient.item("mythicmetals:orichalcum_ingot"));
    public static final Crop OSMIUM = new Crop(new ResourceLocation(MOD_ID, "osmium"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x19223A, LazyIngredient.item("mythicmetals:osmium_ingot"));
    public static final Crop PALLADIUM = new Crop(new ResourceLocation(MOD_ID, "palladium"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0xDF801D, LazyIngredient.item("mythicmetals:palladium_ingot"));
    public static final Crop PROMETHEUM = new Crop(new ResourceLocation(MOD_ID, "prometheum"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x53976C, LazyIngredient.item("mythicmetals:prometheum_ingot"));
    public static final Crop QUADRILLUM = new Crop(new ResourceLocation(MOD_ID, "quadrillum"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x3E4747, LazyIngredient.item("mythicmetals:quadrillum_ingot"));
    public static final Crop RUNITE = new Crop(new ResourceLocation(MOD_ID, "runite"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x0088AA, LazyIngredient.item("mythicmetals:runite_ingot"));
    public static final Crop STORMYX = new Crop(new ResourceLocation(MOD_ID, "stormyx"), CropTier.THREE, CropType.RESOURCE, CropTextures.INGOT_CROP_TEXTURES, 0x6C2AAB, LazyIngredient.item("mythicmetals:stormyx_ingot"));
    /*
    public static List<Crop> getCrops() {
        return crops;
    }

    public static Crop getCropById(String id) {
        return crops.stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public static Crop register(Crop crop) {
        if (crop.isEnabled())
            crops.add(crop);
        return crop;
    }

    public static void registerItems() {
        crops.forEach(c -> {
            var idEssence = new ResourceLocation(MOD_ID, c.getId() + "_essence");
            var idSeeds = new ResourceLocation(MOD_ID, c.getId() + "_seeds");

            if (c.getId().equals("inferium")) {
                c.setEssenceItem(() -> Items.INFERIUM_ESSENCE);
                ITEMS.put(Items.INFERIUM_ESSENCE, idEssence);
                Registry.register(Registries.ITEM, idEssence, Items.INFERIUM_ESSENCE);
            } else {
                Item defaultEssence;
                if (c.getId().equals("netherite")) {
                    defaultEssence = new MysticalEssenceItem(c);
                } else {
                    defaultEssence = new MysticalEssenceItem(c);
                }
                c.setEssenceItem(() -> defaultEssence);
                ITEMS.put(defaultEssence, idEssence);
                Registry.register(Registries.ITEM, idEssence, defaultEssence);
            }

            AliasedBlockItem defaultSeeds;
            if (c.getId().equals("netherite")) {
                defaultSeeds = new MysticalSeedItem(c);
            } else {
                defaultSeeds = new MysticalSeedItem(c);
            }
            c.setSeedsItem(() -> defaultSeeds);
            ITEMS.put(defaultSeeds, idSeeds);
            Registry.register(Registries.ITEM, idSeeds, defaultSeeds);
        });
    }*/

    public static void onColors() {
        crops.forEach(crop -> {
            if (crop.getEssenceColor() > 0 && crop.getEssenceItem() != null)
                ColorProviderRegistry.ITEM.register((stack, tint) -> crop.getEssenceColor(), crop.getEssenceItem());
            if (crop.getSeedColor() > 0 && crop.getSeedsItem() != null)
                ColorProviderRegistry.ITEM.register((stack, tint) -> crop.getSeedColor(), crop.getSeedsItem());
            if (crop.getFlowerColor() > 0 && crop.getCropBlock() != null)
                ColorProviderRegistry.BLOCK.register((state, world, pos, tint) -> crop.getFlowerColor(), crop.getCropBlock());

            BlockRenderLayerMap.INSTANCE.putBlock(crop.getCropBlock(), RenderType.cutout());
        });
    }

    public static void onRegisterCrops(ICropRegistry registry) {
        INFERIUM.getTextures().setEssenceTexture(new ResourceLocation(MOD_ID, "item/inferium_essence"));
        INFERIUM.setCropBlock(() -> (CropBlock) Blocks.INFERIUM_CROP)
                .setEssenceItem(() -> Items.INFERIUM_ESSENCE);

        registry.register(AIR);
        registry.register(EARTH);
        registry.register(WATER);
        registry.register(FIRE);

        registry.register(INFERIUM);
        registry.register(STONE);
        registry.register(DIRT);
        registry.register(WOOD);
        registry.register(ICE);
        registry.register(DEEPSLATE);

        registry.register(NATURE);
        registry.register(DYE);
        registry.register(NETHER);
        registry.register(COAL);
        registry.register(CORAL);
        registry.register(HONEY);
        registry.register(AMETHYST);
        registry.register(PIG);
        registry.register(CHICKEN);
        registry.register(COW);
        registry.register(SHEEP);
        registry.register(SQUID);
        registry.register(FISH);
        registry.register(SLIME);
        registry.register(TURTLE);

        registry.register(IRON);
        registry.register(COPPER);
        registry.register(NETHER_QUARTZ);
        registry.register(GLOWSTONE);
        registry.register(REDSTONE);
        registry.register(OBSIDIAN);
        registry.register(PRISMARINE);
        registry.register(ZOMBIE);
        registry.register(SKELETON);
        registry.register(CREEPER);
        registry.register(SPIDER);
        registry.register(RABBIT);

        registry.register(GOLD);
        registry.register(LAPIS_LAZULI);
        registry.register(END);
        registry.register(EXPERIENCE);
        registry.register(BLAZE);
        registry.register(GHAST);
        registry.register(ENDERMAN);

        registry.register(DIAMOND);
        registry.register(EMERALD);
        registry.register(NETHERITE);
        registry.register(WITHER_SKELETON);

        registry.register(withRequiredMods(SULFUR,"indrev", "techreborn"));
        registry.register(withRequiredMods(ALUMINUM, "modern_industrialization", "techreborn"));
        registry.register(withRequiredMods(TIN, "indrev", "modern_industrialization", "mythicmetals", "techreborn", "astromine"));
        registry.register(withRequiredMods(BRONZE, "indrev", "modern_industrialization", "mythicmetals", "techreborn", "astromine"));
        registry.register(withRequiredMods(ZINC,  "mythicmetals", "techreborn"));
        registry.register(withRequiredMods(BRASS, "mythicmetals", "techreborn"));
        registry.register(withRequiredMods(SILVER, "indrev", "modern_industrialization", "mythicmetals", "techreborn", "astromine"));
        registry.register(withRequiredMods(LEAD, "indrev", "modern_industrialization", "techreborn", "astromine"));
        registry.register(withRequiredMods(STEEL, "indrev", "modern_industrialization", "mythicmetals", "techreborn", "astromine"));
        registry.register(withRequiredMods(NICKEL, "modern_industrialization", "techreborn"));
        registry.register(withRequiredMods(ELECTRUM, "indrev", "modern_industrialization", "mythicmetals", "techreborn", "astromine"));
        registry.register(withRequiredMods(INVAR, "modern_industrialization", "techreborn"));
        registry.register(withRequiredMods(TUNGSTEN, "indrev", "techreborn"));
        registry.register(withRequiredMods(TITANIUM, "modern_industrialization", "techreborn"));
        registry.register(withRequiredMods(CHROMIUM, "modern_industrialization", "techreborn"));
        registry.register(withRequiredMods(PLATINUM, "mythicmetals", "techreborn"));
        registry.register(withRequiredMods(NIKOLITE, "indrev"));
        registry.register(withRequiredMods(ANTIMONY, "modern_industrialization"));
        registry.register(withRequiredMods(SKY_STONE, "ae2"));
        registry.register(withRequiredMods(CERTUS_QUARTZ, "ae2"));
        registry.register(withRequiredMods(FLUIX, "ae2"));
        registry.register(withRequiredMods(RUBBER, "techreborn"));
        registry.register(withRequiredMods(RUBY, "techreborn"));
        registry.register(withRequiredMods(SAPPHIRE, "techreborn"));
        registry.register(withRequiredMods(PERIDOT, "techreborn"));
        registry.register(withRequiredMods(IRIDIUM, "techreborn"));
        registry.register(withRequiredMods(ADAMANTITE, "mythicmetals"));
        registry.register(withRequiredMods(AQUARIUM, "mythicmetals"));
        registry.register(withRequiredMods(BANGLUM, "mythicmetals"));
        registry.register(withRequiredMods(CARMOT, "mythicmetals"));
        registry.register(withRequiredMods(CELESTIUM, "mythicmetals"));
        registry.register(withRequiredMods(DURASTEEL, "mythicmetals"));
        registry.register(withRequiredMods(HALLOWED, "mythicmetals"));
        registry.register(withRequiredMods(KYBER, "mythicmetals"));
        registry.register(withRequiredMods(MANGANESE, "mythicmetals"));
        registry.register(withRequiredMods(METALLURGIUM, "mythicmetals"));
        registry.register(withRequiredMods(MIDAS_GOLD, "mythicmetals"));
        registry.register(withRequiredMods(MYTHRIL, "mythicmetals"));
        registry.register(withRequiredMods(ORICHALCUM, "mythicmetals"));
        registry.register(withRequiredMods(OSMIUM, "mythicmetals"));
        registry.register(withRequiredMods(PALLADIUM, "mythicmetals"));
        registry.register(withRequiredMods(PROMETHEUM, "mythicmetals"));
        registry.register(withRequiredMods(QUADRILLUM, "mythicmetals"));
        registry.register(withRequiredMods(RUNITE, "mythicmetals"));
        registry.register(withRequiredMods(STORMYX, "mythicmetals"));
    }

    private static Crop withRequiredMods(Crop crop, String... mods) {
        boolean enabled = Arrays.stream(mods).anyMatch(FabricLoader.getInstance()::isModLoaded);
        return crop.setEnabled(enabled);
        /*for (String mod : mods) {
            if (FabricLoader.getInstance().isModLoaded(mod))
                return crop.setEnabled(true);
        }
        return crop.setEnabled(false);*/
    }
}
