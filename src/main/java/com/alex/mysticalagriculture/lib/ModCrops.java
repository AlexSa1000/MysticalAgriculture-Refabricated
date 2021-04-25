package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.crop.CropType;
import com.alex.mysticalagriculture.api.lib.LazyIngredient;
import com.alex.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.blocks.InferiumCropBlock;
import com.alex.mysticalagriculture.blocks.MysticalCropBlock;
import com.alex.mysticalagriculture.init.Blocks;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.items.MysticalEssenceItem;
import com.alex.mysticalagriculture.items.MysticalSeedItem;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static com.alex.mysticalagriculture.MysticalAgriculture.ITEM_GROUP;
import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ModCrops {
    private static final List<Crop> crops = new ArrayList<>();


    public static final Crop AIR = register(new Crop("air", CropTier.ELEMENTAL, CropType.RESOURCE,0xDAD64D, LazyIngredient.item("mysticalagriculture:air_agglomeratio")));
    public static final Crop EARTH = register(new Crop("earth", CropTier.ELEMENTAL, CropType.RESOURCE,0x54DA4D, LazyIngredient.item("mysticalagriculture:earth_agglomeratio")));
    public static final Crop WATER = register(new Crop("water", CropTier.ELEMENTAL, CropType.RESOURCE,0x4D7EDA, LazyIngredient.item("mysticalagriculture:water_agglomeratio")));
    public static final Crop FIRE = register(new Crop("fire", CropTier.ELEMENTAL, CropType.RESOURCE,0xDA4D4D, LazyIngredient.item("mysticalagriculture:fire_agglomeratio")));

    public static final Crop INFERIUM = register(new Crop("inferium", CropTier.ONE, CropType.RESOURCE, LazyIngredient.EMPTY));
    public static final Crop STONE = register(new Crop("stone", CropTier.ONE, CropType.RESOURCE,0x7F7F7F, LazyIngredient.item("minecraft:stone")));
    public static final Crop DIRT = register(new Crop("dirt", CropTier.ONE, CropType.RESOURCE, LazyIngredient.item("minecraft:dirt")));
    public static final Crop WOOD = register(new Crop("wood", CropTier.ONE, CropType.RESOURCE, LazyIngredient.item("minecraft:oak_log")));
    public static final Crop ICE = register(new Crop("ice", CropTier.ONE, CropType.RESOURCE, LazyIngredient.item("minecraft:ice")));

    public static final Crop NATURE = register(new Crop("nature", CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:nature_agglomeratio")));
    public static final Crop DYE = register(new Crop("dye", CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:dye_agglomeratio")));
    public static final Crop NETHER = register(new Crop("nether", CropTier.TWO, CropType.RESOURCE, 0x723232, LazyIngredient.item("mysticalagriculture:nether_agglomeratio")));
    public static final Crop COAL = register(new Crop("coal", CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("minecraft:coal")));
    public static final Crop CORAL = register(new Crop("coral", CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:coral_agglomeratio")));
    public static final Crop HONEY = register(new Crop("honey", CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:honey_agglomeratio")));
    public static final Crop PIG = register(new Crop("pig", CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.PIG))));
    public static final Crop CHICKEN = register(new Crop("chicken", CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.CHICKEN))));
    public static final Crop COW = register(new Crop("cow", CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.COW))));
    public static final Crop SHEEP = register(new Crop("sheep", CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.SHEEP))));
    public static final Crop SQUID = register(new Crop("squid", CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.SQUID))));
    public static final Crop FISH = register(new Crop("fish", CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.FISH))));
    public static final Crop SLIME = register(new Crop("slime", CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.SLIME))));
    public static final Crop TURTLE = register(new Crop("turtle", CropTier.TWO, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.TURTLE))));

    public static final Crop IRON = register(new Crop("iron", CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("minecraft:iron_ingot")));
    public static final Crop NETHER_QUARTZ = register(new Crop("nether_quartz", CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("minecraft:quartz")));
    public static final Crop GLOWSTONE = register(new Crop("glowstone", CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("minecraft:glowstone")));
    public static final Crop REDSTONE = register(new Crop("redstone", CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("minecraft:redstone")));
    public static final Crop OBSIDIAN = register(new Crop("obsidian", CropTier.THREE, CropType.RESOURCE,0x271E3D, LazyIngredient.item("minecraft:obsidian")));
    public static final Crop PRISMARINE = register(new Crop("prismarine", CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:prismarine_agglomeratio")));
    public static final Crop ZOMBIE = register(new Crop("zombie", CropTier.THREE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.ZOMBIE))));
    public static final Crop SKELETON = register(new Crop("skeleton", CropTier.THREE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.SKELETON))));
    public static final Crop CREEPER = register(new Crop("creeper", CropTier.THREE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.CREEPER))));
    public static final Crop SPIDER = register(new Crop("spider", CropTier.THREE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.SPIDER))));
    public static final Crop RABBIT = register(new Crop("rabbit", CropTier.THREE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.RABBIT))));

    public static final Crop GOLD = register(new Crop("gold", CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("minecraft:gold_ingot")));
    public static final Crop LAPIS_LAZULI = register(new Crop("lapis_lazuli", CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("minecraft:lapis_lazuli")));
    public static final Crop END = register(new Crop("end", CropTier.FOUR, CropType.RESOURCE,0xEEF6B4, LazyIngredient.item("mysticalagriculture:end_agglomeratio")));
    public static final Crop EXPERIENCE = register(new Crop("experience", CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("mysticalagriculture:experience_capsule", ExperienceCapsuleUtils.makeTag(ExperienceCapsuleUtils.MAX_XP_POINTS))));
    public static final Crop BLAZE = register(new Crop("blaze", CropTier.FOUR, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.BLAZE))));
    public static final Crop GHAST = register(new Crop("ghast", CropTier.FOUR, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.GHAST))));
    public static final Crop ENDERMAN = register(new Crop("enderman", CropTier.FOUR, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.ENDERMAN))));

    public static final Crop DIAMOND = register(new Crop("diamond", CropTier.FIVE, CropType.RESOURCE, LazyIngredient.item("minecraft:diamond")));
    public static final Crop EMERALD = register(new Crop("emerald", CropTier.FIVE, CropType.RESOURCE, LazyIngredient.item("minecraft:emerald")));
    public static final Crop NETHERITE = register(new Crop("netherite", CropTier.FIVE, CropType.RESOURCE, LazyIngredient.item("minecraft:netherite_ingot")));
    public static final Crop WITHER_SKELETON = register(new Crop("wither_skeleton", CropTier.FIVE, CropType.MOB, LazyIngredient.item("mysticalagriculture:soul_jar", MobSoulUtils.makeTag(ModMobSoulTypes.WITHER))));

    //Common
    public static final Crop SULFUR = register(withRequiredMods(new Crop("sulfur", CropTier.TWO, CropType.RESOURCE, 0xFDDC4B, LazyIngredient.tag("c:sulfur_dusts")), "indrev", "techreborn"));
    public static final Crop ALUMINUM = register(withRequiredMods(new Crop("aluminum", CropTier.TWO, CropType.RESOURCE, 0xA4A6B1, LazyIngredient.tag("c:aluminum_ingots")), "modern_industrialization", "techreborn"));
    public static final Crop COPPER = register(withRequiredMods(new Crop("copper", CropTier.TWO, CropType.RESOURCE, 0xF48702, LazyIngredient.tag("c:copper_ingots")), "indrev", "modern_industrialization", "mythicmetals", "techreborn"));
    public static final Crop TIN = register(withRequiredMods(new Crop("tin", CropTier.THREE, CropType.RESOURCE, 0x9ABDD6, LazyIngredient.tag("c:tin_ingots")), "indrev", "modern_industrialization", "mythicmetals", "techreborn"));
    public static final Crop BRONZE = register(withRequiredMods(new Crop("bronze", CropTier.THREE, CropType.RESOURCE, 0xC98C52, LazyIngredient.tag("c:bronze_ingots")), "indrev", "modern_industrialization", "mythicmetals", "techreborn"));
    public static final Crop ZINC = register(withRequiredMods(new Crop("zinc", CropTier.THREE, CropType.RESOURCE, 0xE9EBE7, LazyIngredient.tag("c:zinc_ingots")), "mythicmetals", "techreborn"));
    public static final Crop BRASS = register(withRequiredMods(new Crop("brass", CropTier.THREE, CropType.RESOURCE, 0xDAAA4C, LazyIngredient.tag("c:brass_ingots")), "mythicmetals", "techreborn"));
    public static final Crop SILVER = register(withRequiredMods(new Crop("silver", CropTier.THREE, CropType.RESOURCE, 0xA9DBE5, LazyIngredient.tag("c:silver_ingots")), "indrev", "modern_industrialization", "mythicmetals", "techreborn"));
    public static final Crop LEAD = register(withRequiredMods(new Crop("lead", CropTier.THREE, CropType.RESOURCE, 0x677193, LazyIngredient.tag("c:lead_ingots")), "indrev", "modern_industrialization", "techreborn"));
    public static final Crop STEEL = register(withRequiredMods(new Crop("steel", CropTier.FOUR, CropType.RESOURCE, 0x737373, LazyIngredient.tag("c:steel_ingots")), "indrev", "modern_industrialization", "mythicmetals", "techreborn"));
    public static final Crop NICKEL = register(withRequiredMods(new Crop("nickel", CropTier.FOUR, CropType.RESOURCE, 0xD8CC93, LazyIngredient.tag("c:nickel_ingots")), "modern_industrialization", "techreborn"));
    public static final Crop ELECTRUM = register(withRequiredMods(new Crop("electrum", CropTier.FOUR, CropType.RESOURCE, 0xD5BB4F, LazyIngredient.tag("c:electrum_ingots")), "indrev", "modern_industrialization", "mythicmetals", "techreborn"));
    public static final Crop INVAR = register(withRequiredMods(new Crop("invar", CropTier.FOUR, CropType.RESOURCE, 0xADB7B2, LazyIngredient.tag("c:invar_ingots")), "modern_industrialization", "techreborn"));
    public static final Crop TUNGSTEN = register(withRequiredMods(new Crop("tungsten", CropTier.FOUR, CropType.RESOURCE, 0x616669, LazyIngredient.tag("c:tungsten_ingots")), "indrev", "techreborn"));
    public static final Crop TITANIUM = register(withRequiredMods(new Crop("titanium", CropTier.FOUR, CropType.RESOURCE, 0xD0D1DA, LazyIngredient.tag("c:titanium_ingots")), "modern_industrialization", "techreborn"));
    public static final Crop CHROME = register(withRequiredMods(new Crop("chrome", CropTier.FOUR, CropType.RESOURCE, 0xCDB9BD, LazyIngredient.tag("c:chrome_ingots")), "modern_industrialization", "techreborn"));
    public static final Crop PLATINUM = register(withRequiredMods(new Crop("platinum", CropTier.FIVE, CropType.RESOURCE, 0x6FEAEF, LazyIngredient.tag("c:platinum_ingots")), "mythicmetals", "techreborn"));

    //Industrial Revolution
    public static final Crop NIKOLITE = register(withRequiredMods(new Crop("nikolite", CropTier.THREE, CropType.RESOURCE, 0x006D6B, LazyIngredient.item("indrev:nikolite_dust")), "indrev"));

    //Modern Industrialization
    public static final Crop ANTIMONY = register(withRequiredMods(new Crop("antimony", CropTier.THREE, CropType.RESOURCE, 0x81A9A1, LazyIngredient.item("modern_industrialization:antimony_ingot")), "modern_industrialization"));

    //AE2
    public static final Crop SKY_STONE = register(withRequiredMods(new Crop("sky_stone", CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("appliedenergistics2:sky_stone_block")), "appliedenergistics2"));
    public static final Crop CERTUS_QUARTZ = register(withRequiredMods(new Crop("certus_quartz", CropTier.THREE, CropType.RESOURCE, LazyIngredient.tag("appliedenergistics2:crystals/certus_quartz")), "appliedenergistics2"));
    public static final Crop FLUIX = register(withRequiredMods(new Crop("fluix", CropTier.FOUR, CropType.RESOURCE, LazyIngredient.tag("appliedenergistics2:crystals/fluix")), "appliedenergistics2"));

    //Tech Reborn
    public static final Crop RUBBER = register(withRequiredMods(new Crop("rubber", CropTier.TWO, CropType.RESOURCE, LazyIngredient.item("techreborn:rubber")), "techreborn"));
    public static final Crop RUBY = register(withRequiredMods(new Crop("ruby", CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("techreborn:ruby_gem")), "techreborn"));
    public static final Crop SAPPHIRE = register(withRequiredMods(new Crop("sapphire", CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("techreborn:sapphire_gem")), "techreborn"));
    public static final Crop PERIDOT = register(withRequiredMods(new Crop("peridot", CropTier.FOUR, CropType.RESOURCE, LazyIngredient.item("techreborn:peridot_gem")), "techreborn"));
    public static final Crop IRIDIUM = register(withRequiredMods(new Crop("iridium", CropTier.FIVE, CropType.RESOURCE, 0xC7C5DC, LazyIngredient.tag("c:iridium_ingots")), "techreborn"));


    public static Crop getCropById(String id) {
        return crops.stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public static Crop register(Crop crop) {
        if (crop.isEnabled())
            crops.add(crop);
        return crop;
    }

    public static void registerBlocks() {
        crops.forEach(c -> {
            CropBlock defaultCrop;
            if (c.getId().equals("inferium")) {
                defaultCrop = new InferiumCropBlock(c);
            } else {
                defaultCrop = new MysticalCropBlock(c);
            }
            c.setCrop(() -> defaultCrop);

            Registry.register(Registry.BLOCK, new Identifier(MOD_ID, c.getId() + "_crop"), defaultCrop);
        });
    }

    public static void registerItems() {
        crops.forEach(c -> {

            if (c.getId().equals("inferium")) {
                c.setEssence(() -> Items.INFERIUM_ESSENCE);
                Registry.register(Registry.ITEM, new Identifier(MOD_ID, c.getId() + "_essence"), Items.INFERIUM_ESSENCE);
            } else {
                Item defaultEssence = new MysticalEssenceItem(c, p -> p.group(ITEM_GROUP));
                c.setEssence(() -> defaultEssence);
                Registry.register(Registry.ITEM, new Identifier(MOD_ID, c.getId() + "_essence"), defaultEssence);
            }


            AliasedBlockItem defaultSeeds = new MysticalSeedItem(c, p -> p.group(ITEM_GROUP));
            c.setSeeds(() -> defaultSeeds);

            Registry.register(Registry.ITEM, new Identifier(MOD_ID, c.getId() + "_seeds"), defaultSeeds);
        });

        CropTier.ELEMENTAL.setFarmland(() -> (FarmlandBlock) Blocks.INFERIUM_FARMLAND).setEssence(() -> Items.INFERIUM_ESSENCE);
        CropTier.ONE.setFarmland(() -> (FarmlandBlock) Blocks.INFERIUM_FARMLAND).setEssence(() -> Items.INFERIUM_ESSENCE);
        CropTier.TWO.setFarmland(() -> (FarmlandBlock) Blocks.PRUDENTIUM_FARMLAND).setEssence(() -> Items.PRUDENTIUM_ESSENCE);
        CropTier.THREE.setFarmland(() -> (FarmlandBlock) Blocks.TERTIUM_FARMLAND).setEssence(() -> Items.TERTIUM_ESSENCE);
        CropTier.FOUR.setFarmland(() -> (FarmlandBlock) Blocks.IMPERIUM_FARMLAND).setEssence(() -> Items.IMPERIUM_ESSENCE);
        CropTier.FIVE.setFarmland(() -> (FarmlandBlock) Blocks.SUPREMIUM_FARMLAND).setEssence(() -> Items.SUPREMIUM_ESSENCE);

        CropType.RESOURCE.setCraftingSeed(Items.PROSPERITY_SEED_BASE);
        CropType.MOB.setCraftingSeed(Items.SOULIUM_SEED_BASE);
    }

    public static void onColors() {
        crops.forEach(crop -> {
            if (crop.getEssenceColor() > 0 && crop.getEssence() != null)
                ColorProviderRegistry.ITEM.register((stack, tint) -> crop.getEssenceColor(), crop.getEssence());
            if (crop.getSeedColor() > 0 && crop.getSeeds() != null)
                ColorProviderRegistry.ITEM.register((stack, tint) -> crop.getSeedColor(), crop.getSeeds());
            if (crop.getFlowerColor() > 0 && crop.getCrop() != null)
                ColorProviderRegistry.BLOCK.register((state, world, pos, tint) -> crop.getFlowerColor(), crop.getCrop());

            BlockRenderLayerMap.INSTANCE.putBlock(crop.getCrop(), RenderLayer.getCutout());
        });
    }

    private static Crop withRequiredMods(Crop crop, String... mods) {
        for (String mod : mods) {
            if (FabricLoader.getInstance().isModLoaded(mod))
                return crop.setEnabled(true);
        }
        return crop.setEnabled(false);
    }
}
