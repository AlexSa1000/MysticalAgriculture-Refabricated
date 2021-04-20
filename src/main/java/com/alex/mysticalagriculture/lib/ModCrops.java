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

    public static Crop getCropById(String id) {
        return crops.stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public static Crop register(Crop crop) {
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
        CropTier.TWO.setFarmland(() -> (FarmlandBlock) Blocks.INFERIUM_FARMLAND).setEssence(() -> Items.PRUDENTIUM_ESSENCE);
        CropTier.THREE.setFarmland(() -> (FarmlandBlock) Blocks.INFERIUM_FARMLAND).setEssence(() -> Items.TERTIUM_ESSENCE);
        CropTier.FOUR.setFarmland(() -> (FarmlandBlock) Blocks.INFERIUM_FARMLAND).setEssence(() -> Items.IMPERIUM_ESSENCE);
        CropTier.FIVE.setFarmland(() -> (FarmlandBlock) Blocks.INFERIUM_FARMLAND).setEssence(() -> Items.SUPREMIUM_ESSENCE);

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
}
