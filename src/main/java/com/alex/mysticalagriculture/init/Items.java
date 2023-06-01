package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.cucumber.item.BaseItem;
import com.alex.mysticalagriculture.cucumber.item.tool.BaseScytheItem;
import com.alex.mysticalagriculture.cucumber.item.tool.BaseSickleItem;
import com.alex.mysticalagriculture.items.*;
import com.alex.mysticalagriculture.items.armor.EssenceBootsItem;
import com.alex.mysticalagriculture.items.armor.EssenceChestplateItem;
import com.alex.mysticalagriculture.items.armor.EssenceHelmetItem;
import com.alex.mysticalagriculture.items.armor.EssenceLeggingsItem;
import com.alex.mysticalagriculture.items.tool.*;
import com.alex.mysticalagriculture.lib.ModArmorMaterial;
import com.alex.mysticalagriculture.lib.ModToolMaterial;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.util.MachineUpgradeTier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.alex.mysticalagriculture.MysticalAgriculture.ITEM_GROUP;
import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class Items {
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    public static final Map<Identifier, Item> GEAR_ITEMS = new LinkedHashMap<>();

    public static final Item PROSPERITY_SHARD = register("prosperity_shard");
    public static final Item INFERIUM_ESSENCE = register(new EssenceItem(CropTier.ONE, p -> p.group(ITEM_GROUP)), "inferium_essence");
    public static final Item PRUDENTIUM_ESSENCE  = register(new EssenceItem(CropTier.TWO, p -> p.group(ITEM_GROUP)), "prudentium_essence");
    public static final Item TERTIUM_ESSENCE  = register(new EssenceItem(CropTier.THREE, p -> p.group(ITEM_GROUP)), "tertium_essence");
    public static final Item IMPERIUM_ESSENCE  = register(new EssenceItem(CropTier.FOUR, p -> p.group(ITEM_GROUP)), "imperium_essence");
    public static final Item SUPREMIUM_ESSENCE  = register(new EssenceItem(CropTier.FIVE, p -> p.group(ITEM_GROUP)), "supremium_essence");
    public static final Item AWAKENED_SUPREMIUM_ESSENCE = register("awakened_supremium_essence");
    public static final Item PROSPERITY_INGOT = register("prosperity_ingot");
    public static final Item INFERIUM_INGOT = register("inferium_ingot");
    public static final Item PRUDENTIUM_INGOT = register("prudentium_ingot");
    public static final Item TERTIUM_INGOT = register("tertium_ingot");
    public static final Item IMPERIUM_INGOT = register("imperium_ingot");
    public static final Item SUPREMIUM_INGOT = register("supremium_ingot");
    public static final Item AWAKENED_SUPREMIUM_INGOT = register("awakened_supremium_ingot");
    public static final Item SOULIUM_INGOT = register("soulium_ingot");
    public static final Item PROSPERITY_NUGGET = register("prosperity_nugget");
    public static final Item INFERIUM_NUGGET = register("inferium_nugget");
    public static final Item PRUDENTIUM_NUGGET = register("prudentium_nugget");
    public static final Item TERTIUM_NUGGET = register("tertium_nugget");
    public static final Item IMPERIUM_NUGGET = register("imperium_nugget");
    public static final Item SUPREMIUM_NUGGET = register("supremium_nugget");
    public static final Item AWAKENED_SUPREMIUM_NUGGET = register("awakened_supremium_nugget");
    public static final Item SOULIUM_NUGGET = register("soulium_nugget");
    public static final Item PROSPERITY_GEMSTONE = register("prosperity_gemstone");
    public static final Item INFERIUM_GEMSTONE = register("inferium_gemstone");
    public static final Item PRUDENTIUM_GEMSTONE = register("prudentium_gemstone");
    public static final Item TERTIUM_GEMSTONE = register("tertium_gemstone");
    public static final Item IMPERIUM_GEMSTONE = register("imperium_gemstone");
    public static final Item SUPREMIUM_GEMSTONE = register("supremium_gemstone");
    public static final Item AWAKENED_SUPREMIUM_GEMSTONE = register("awakened_supremium_gemstone");
    public static final Item SOULIUM_GEMSTONE = register("soulium_gemstone");
    public static final Item PROSPERITY_SEED_BASE = register("prosperity_seed_base");
    public static final Item SOULIUM_SEED_BASE = register("soulium_seed_base");
    public static final Item SOULIUM_DUST = register("soulium_dust");
    public static final Item SOUL_DUST = register("soul_dust");
    public static final Item COGNIZANT_DUST = register("cognizant_dust");
    public static final Item SOULIUM_DAGGER = register(new SouliumDaggerItem(ModToolMaterial.SOULIUM, SouliumDaggerItem.DaggerType.BASIC, p -> p.group(ITEM_GROUP)), "soulium_dagger");
    public static final Item PASSIVE_SOULIUM_DAGGER = register(new SouliumDaggerItem(ModToolMaterial.SOULIUM, SouliumDaggerItem.DaggerType.PASSIVE, p -> p.group(ITEM_GROUP)), "passive_soulium_dagger");
    public static final Item HOSTILE_SOULIUM_DAGGER = register(new SouliumDaggerItem(ModToolMaterial.SOULIUM, SouliumDaggerItem.DaggerType.HOSTILE, p -> p.group(ITEM_GROUP)), "hostile_soulium_dagger");
    public static final Item CREATIVE_SOULIUM_DAGGER = register(new SouliumDaggerItem(ModToolMaterial.SOULIUM, SouliumDaggerItem.DaggerType.CREATIVE, p -> p.group(ITEM_GROUP)), "creative_soulium_dagger");
    public static final Item INFUSION_CRYSTAL = register(new InfusionCrystalItem(p -> p.group(ITEM_GROUP)), "infusion_crystal");
    public static final Item MASTER_INFUSION_CRYSTAL = register(new MasterInfusionCrystalItem(p -> p.group(ITEM_GROUP)), "master_infusion_crystal");
    public static final Item FERTILIZED_ESSENCE = register(new FertilizedEssenceItem(new Item.Settings().group(ITEM_GROUP)), "fertilized_essence");
    public static final Item MYSTICAL_FERTILIZER = register(new MysticalFertilizerItem(p -> p.group(ITEM_GROUP)), "mystical_fertilizer");
    public static final Item AIR_AGGLOMERATIO = register("air_agglomeratio");
    public static final Item EARTH_AGGLOMERATIO = register("earth_agglomeratio");
    public static final Item WATER_AGGLOMERATIO = register("water_agglomeratio");
    public static final Item FIRE_AGGLOMERATIO = register("fire_agglomeratio");
    public static final Item NATURE_AGGLOMERATIO = register("nature_agglomeratio");
    public static final Item DYE_AGGLOMERATIO = register("dye_agglomeratio");
    public static final Item NETHER_AGGLOMERATIO = register("nether_agglomeratio");
    public static final Item CORAL_AGGLOMERATIO = register("coral_agglomeratio");
    public static final Item HONEY_AGGLOMERATIO = register("honey_agglomeratio");
    public static final Item PRISMARINE_AGGLOMERATIO = register("prismarine_agglomeratio");
    public static final Item END_AGGLOMERATIO = register("end_agglomeratio");
    public static final Item EXPERIENCE_DROPLET = register(new ExperienceDropletItem(p -> p.group(ITEM_GROUP)), "experience_droplet");
    public static final Item WAND = register(new WandItem(p -> p.group(ITEM_GROUP)), "wand");
    public static final Item BLANK_SKULL = register("blank_skull");
    public static final Item BLANK_RECORD = register("blank_record");
    public static final Item UNATTUNED_AUGMENT = register("unattuned_augment");
    public static final Item SOUL_JAR = register(new SoulJarItem(p -> p.group(ITEM_GROUP)), "soul_jar");
    public static final Item EXPERIENCE_CAPSULE = register(new ExperienceCapsuleItem(p -> p.group(ITEM_GROUP)), "experience_capsule");
    public static final Item WATERING_CAN = register(new WateringCanItem(3, 0.25, p -> p.group(ITEM_GROUP)), "watering_can");
    public static final Item DIAMOND_SICKLE = register(new BaseSickleItem(ToolMaterials.DIAMOND, 4.0F, -3.0F, 3, p -> p.group(ITEM_GROUP)), "diamond_sickle");
    public static final Item DIAMOND_SCYTHE = register(new BaseScytheItem(ToolMaterials.DIAMOND, 4, -2.8F, 3, p -> p.group(ITEM_GROUP)), "diamond_scythe");
    public static final Item UPGRADE_BASE = register("upgrade_base");
    public static final Item INFERIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.INFERIUM, p -> p.group(ITEM_GROUP)), "inferium_upgrade");
    public static final Item PRUDENTIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.PRUDENTIUM, p -> p.group(ITEM_GROUP)), "prudentium_upgrade");
    public static final Item TERTIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.TERTIUM, p -> p.group(ITEM_GROUP)), "tertium_upgrade");
    public static final Item IMPERIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.IMPERIUM, p -> p.group(ITEM_GROUP)), "imperium_upgrade");
    public static final Item SUPREMIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.SUPREMIUM, p -> p.group(ITEM_GROUP)), "supremium_upgrade");
    public static final Item AWAKENED_SUPREMIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.AWAKENED_SUPREMIUM, p -> p.group(ITEM_GROUP)), "awakened_supremium_upgrade");

    public static final Item INFERIUM_SWORD = registerGear(new EssenceSwordItem(ModToolMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_sword");
    public static final Item INFERIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModToolMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_pickaxe");
    public static final Item INFERIUM_SHOVEL = registerGear(new EssenceShovelItem(ModToolMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_shovel");
    public static final Item INFERIUM_AXE = registerGear(new EssenceAxeItem(ModToolMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_axe");
    public static final Item INFERIUM_HOE = registerGear(new EssenceHoeItem(ModToolMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_hoe");
    public static final Item INFERIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(3, 0.25, CropTier.ONE.getTextColor(), p -> p.group(ITEM_GROUP)), "inferium_watering_can");
    public static final Item INFERIUM_BOW = registerGear(new EssenceBowItem(ModToolMaterial.INFERIUM, 1, 1, 1.1F, p -> p.group(ITEM_GROUP)), "inferium_bow");
    public static final Item INFERIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModToolMaterial.INFERIUM, 1, 1, 1.1F, p -> p.group(ITEM_GROUP)), "inferium_crossbow");
    public static final Item INFERIUM_SHEARS = registerGear(new EssenceShearsItem(ModToolMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_shears");
    public static final Item INFERIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModToolMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_fishing_rod");
    public static final Item INFERIUM_SICKLE = registerGear(new EssenceSickleItem(ModToolMaterial.INFERIUM, 3, CropTier.ONE.getTextColor(), 1, 1, p -> p.group(ITEM_GROUP)), "inferium_sickle");
    public static final Item INFERIUM_SCYTHE = registerGear(new EssenceScytheItem(ModToolMaterial.INFERIUM, 3, CropTier.ONE.getTextColor(), 1, 1, p -> p.group(ITEM_GROUP)), "inferium_scythe");

    public static final Item PRUDENTIUM_SWORD = registerGear(new EssenceSwordItem(ModToolMaterial.PRUDENTIUM, 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_sword");
    public static final Item PRUDENTIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModToolMaterial.PRUDENTIUM, 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_pickaxe");
    public static final Item PRUDENTIUM_SHOVEL = registerGear(new EssenceShovelItem(ModToolMaterial.PRUDENTIUM, 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_shovel");
    public static final Item PRUDENTIUM_AXE = registerGear(new EssenceAxeItem(ModToolMaterial.PRUDENTIUM, 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_axe");
    public static final Item PRUDENTIUM_HOE = registerGear(new EssenceHoeItem(ModToolMaterial.PRUDENTIUM, 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_hoe");
    public static final Item PRUDENTIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(5, 0.30, CropTier.TWO.getTextColor(), p -> p.group(ITEM_GROUP)), "prudentium_watering_can");
    public static final Item PRUDENTIUM_BOW = registerGear(new EssenceBowItem(ModToolMaterial.PRUDENTIUM, 2, 1, 1.2F, p -> p.group(ITEM_GROUP)), "prudentium_bow");
    public static final Item PRUDENTIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModToolMaterial.PRUDENTIUM, 2, 1, 1.2F, p -> p.group(ITEM_GROUP)), "prudentium_crossbow");
    public static final Item PRUDENTIUM_SHEARS = registerGear(new EssenceShearsItem(ModToolMaterial.PRUDENTIUM, 2, 2, p -> p.group(ITEM_GROUP)), "prudentium_shears");
    public static final Item PRUDENTIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModToolMaterial.PRUDENTIUM, 2, 2, p -> p.group(ITEM_GROUP)), "prudentium_fishing_rod");
    public static final Item PRUDENTIUM_SICKLE = registerGear(new EssenceSickleItem(ModToolMaterial.PRUDENTIUM, 4, CropTier.TWO.getTextColor(), 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_sickle");
    public static final Item PRUDENTIUM_SCYTHE = registerGear(new EssenceScytheItem(ModToolMaterial.PRUDENTIUM, 4, CropTier.TWO.getTextColor(), 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_scythe");

    public static final Item TERTIUM_SWORD = registerGear(new EssenceSwordItem(ModToolMaterial.TERTIUM, 3, 1, p -> p.group(ITEM_GROUP)), "tertium_sword");
    public static final Item TERTIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModToolMaterial.TERTIUM, 3, 1, p -> p.group(ITEM_GROUP)), "tertium_pickaxe");
    public static final Item TERTIUM_SHOVEL = registerGear(new EssenceShovelItem(ModToolMaterial.TERTIUM, 3, 1, p -> p.group(ITEM_GROUP)), "tertium_shovel");
    public static final Item TERTIUM_AXE = registerGear(new EssenceAxeItem(ModToolMaterial.TERTIUM, 3, 1, p -> p.group(ITEM_GROUP)), "tertium_axe");
    public static final Item TERTIUM_HOE = registerGear(new EssenceHoeItem(ModToolMaterial.TERTIUM, 3, 1, p -> p.group(ITEM_GROUP)), "tertium_hoe");
    public static final Item TERTIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(7, 0.35, CropTier.THREE.getTextColor(), p -> p.group(ITEM_GROUP)), "tertium_watering_can");
    public static final Item TERTIUM_BOW = registerGear(new EssenceBowItem(ModToolMaterial.TERTIUM, 3, 1, 1.35F, p -> p.group(ITEM_GROUP)), "tertium_bow");
    public static final Item TERTIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModToolMaterial.TERTIUM, 3, 1, 1.35F, p -> p.group(ITEM_GROUP)), "tertium_crossbow");
    public static final Item TERTIUM_SHEARS = registerGear(new EssenceShearsItem(ModToolMaterial.TERTIUM, 3, 3, p -> p.group(ITEM_GROUP)), "tertium_shears");
    public static final Item TERTIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModToolMaterial.TERTIUM, 3, 3, p -> p.group(ITEM_GROUP)), "tertium_fishing_rod");
    public static final Item TERTIUM_SICKLE = registerGear(new EssenceSickleItem(ModToolMaterial.TERTIUM, 5, CropTier.THREE.getTextColor(), 3, 1, p -> p.group(ITEM_GROUP)), "tertium_sickle");
    public static final Item TERTIUM_SCYTHE = registerGear(new EssenceScytheItem(ModToolMaterial.TERTIUM, 5, CropTier.THREE.getTextColor(), 3, 1, p -> p.group(ITEM_GROUP)), "tertium_scythe");

    public static final Item IMPERIUM_SWORD = registerGear(new EssenceSwordItem(ModToolMaterial.IMPERIUM, 4, 1, p -> p.group(ITEM_GROUP)), "imperium_sword");
    public static final Item IMPERIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModToolMaterial.IMPERIUM, 4, 1, p -> p.group(ITEM_GROUP)), "imperium_pickaxe");
    public static final Item IMPERIUM_SHOVEL = registerGear(new EssenceShovelItem(ModToolMaterial.IMPERIUM, 4, 1, p -> p.group(ITEM_GROUP)), "imperium_shovel");
    public static final Item IMPERIUM_AXE = registerGear(new EssenceAxeItem(ModToolMaterial.IMPERIUM, 4, 1, p -> p.group(ITEM_GROUP)), "imperium_axe");
    public static final Item IMPERIUM_HOE = registerGear(new EssenceHoeItem(ModToolMaterial.IMPERIUM, 4, 1, p -> p.group(ITEM_GROUP)), "imperium_hoe");
    public static final Item IMPERIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(9, 0.40, CropTier.FOUR.getTextColor(), p -> p.group(ITEM_GROUP)), "imperium_watering_can");
    public static final Item IMPERIUM_BOW = registerGear(new EssenceBowItem(ModToolMaterial.IMPERIUM, 4, 1, 1.55F, p -> p.group(ITEM_GROUP)), "imperium_bow");
    public static final Item IMPERIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModToolMaterial.IMPERIUM, 4, 1, 1.55F, p -> p.group(ITEM_GROUP)), "imperium_crossbow");
    public static final Item IMPERIUM_SHEARS = registerGear(new EssenceShearsItem(ModToolMaterial.IMPERIUM, 4, 4, p -> p.group(ITEM_GROUP)), "imperium_shears");
    public static final Item IMPERIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModToolMaterial.IMPERIUM, 4, 4, p -> p.group(ITEM_GROUP)), "imperium_fishing_rod");
    public static final Item IMPERIUM_SICKLE = registerGear(new EssenceSickleItem(ModToolMaterial.IMPERIUM, 6, CropTier.FOUR.getTextColor(), 4, 1, p -> p.group(ITEM_GROUP)), "imperium_sickle");
    public static final Item IMPERIUM_SCYTHE = registerGear(new EssenceScytheItem(ModToolMaterial.IMPERIUM, 6, CropTier.FOUR.getTextColor(), 4, 1, p -> p.group(ITEM_GROUP)), "imperium_scythe");

    public static final Item SUPREMIUM_SWORD = registerGear(new EssenceSwordItem(ModToolMaterial.SUPREMIUM, 5, 1, p -> p.group(ITEM_GROUP)), "supremium_sword");
    public static final Item SUPREMIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModToolMaterial.SUPREMIUM, 5, 1, p -> p.group(ITEM_GROUP)), "supremium_pickaxe");
    public static final Item SUPREMIUM_SHOVEL = registerGear(new EssenceShovelItem(ModToolMaterial.SUPREMIUM, 5, 1, p -> p.group(ITEM_GROUP)), "supremium_shovel");
    public static final Item SUPREMIUM_AXE = registerGear(new EssenceAxeItem(ModToolMaterial.SUPREMIUM, 5, 1, p -> p.group(ITEM_GROUP)), "supremium_axe");
    public static final Item SUPREMIUM_HOE = registerGear(new EssenceHoeItem(ModToolMaterial.SUPREMIUM, 5, 1, p -> p.group(ITEM_GROUP)), "supremium_hoe");
    public static final Item SUPREMIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(11, 0.45, CropTier.FIVE.getTextColor(), p -> p.group(ITEM_GROUP)), "supremium_watering_can");
    public static final Item SUPREMIUM_BOW = registerGear(new EssenceBowItem(ModToolMaterial.SUPREMIUM, 5, 1, 1.80F, p -> p.group(ITEM_GROUP)), "supremium_bow");
    public static final Item SUPREMIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModToolMaterial.SUPREMIUM, 5, 1, 1.80F, p -> p.group(ITEM_GROUP)), "supremium_crossbow");
    public static final Item SUPREMIUM_SHEARS = registerGear(new EssenceShearsItem(ModToolMaterial.SUPREMIUM, 5, 5, p -> p.group(ITEM_GROUP)), "supremium_shears");
    public static final Item SUPREMIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModToolMaterial.SUPREMIUM, 5, 5, p -> p.group(ITEM_GROUP)), "supremium_fishing_rod");
    public static final Item SUPREMIUM_SICKLE = registerGear(new EssenceSickleItem(ModToolMaterial.SUPREMIUM, 7, CropTier.FIVE.getTextColor(), 5, 1, p -> p.group(ITEM_GROUP)), "supremium_sickle");
    public static final Item SUPREMIUM_SCYTHE = registerGear(new EssenceScytheItem(ModToolMaterial.SUPREMIUM, 7, CropTier.FIVE.getTextColor(), 5, 1, p -> p.group(ITEM_GROUP)), "supremium_scythe");

    public static final Item AWAKENED_SUPREMIUM_SWORD = registerGear(new EssenceSwordItem(ModToolMaterial.AWAKENED_SUPREMIUM, 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_sword");
    public static final Item AWAKENED_SUPREMIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModToolMaterial.AWAKENED_SUPREMIUM, 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_pickaxe");
    public static final Item AWAKENED_SUPREMIUM_SHOVEL = registerGear(new EssenceShovelItem(ModToolMaterial.AWAKENED_SUPREMIUM, 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_shovel");
    public static final Item AWAKENED_SUPREMIUM_AXE = registerGear(new EssenceAxeItem(ModToolMaterial.AWAKENED_SUPREMIUM, 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_axe");
    public static final Item AWAKENED_SUPREMIUM_HOE = registerGear(new EssenceHoeItem(ModToolMaterial.AWAKENED_SUPREMIUM, 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_hoe");
    public static final Item AWAKENED_SUPREMIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(13, 0.50, CropTier.FIVE.getTextColor(), p -> p.group(ITEM_GROUP)), "awakened_supremium_watering_can");
    public static final Item AWAKENED_SUPREMIUM_BOW = registerGear(new EssenceBowItem(ModToolMaterial.AWAKENED_SUPREMIUM, 5, 2, 2.10F, p -> p.group(ITEM_GROUP)), "awakened_supremium_bow");
    public static final Item AWAKENED_SUPREMIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModToolMaterial.AWAKENED_SUPREMIUM,  5, 2, 2.10F, p -> p.group(ITEM_GROUP)), "awakened_supremium_crossbow");
    public static final Item AWAKENED_SUPREMIUM_SHEARS = registerGear(new EssenceShearsItem(ModToolMaterial.AWAKENED_SUPREMIUM, 5, 5, p -> p.group(ITEM_GROUP)), "awakened_supremium_shears");
    public static final Item AWAKENED_SUPREMIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModToolMaterial.AWAKENED_SUPREMIUM, 5, 5, p -> p.group(ITEM_GROUP)), "awakened_supremium_fishing_rod");
    public static final Item AWAKENED_SUPREMIUM_SICKLE = registerGear(new EssenceSickleItem(ModToolMaterial.AWAKENED_SUPREMIUM, 8, CropTier.FIVE.getTextColor(), 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_sickle");
    public static final Item AWAKENED_SUPREMIUM_SCYTHE = registerGear(new EssenceScytheItem(ModToolMaterial.AWAKENED_SUPREMIUM, 8, CropTier.FIVE.getTextColor(), 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_scythe");

    public static final Item INFERIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_helmet");
    public static final Item INFERIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_chestplate");
    public static final Item INFERIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_leggings");
    public static final Item INFERIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.INFERIUM, 1, 1, p -> p.group(ITEM_GROUP)), "inferium_boots");
   
    public static final Item PRUDENTIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.PRUDENTIUM, 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_helmet");
    public static final Item PRUDENTIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.PRUDENTIUM, 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_chestplate");
    public static final Item PRUDENTIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.PRUDENTIUM, 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_leggings");
    public static final Item PRUDENTIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.PRUDENTIUM, 2, 1, p -> p.group(ITEM_GROUP)), "prudentium_boots");

    public static final Item TERTIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.TERTIUM, 3, 1, p -> p.group(ITEM_GROUP)), "tertium_helmet");
    public static final Item TERTIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.TERTIUM, 3, 1, p -> p.group(ITEM_GROUP)), "tertium_chestplate");
    public static final Item TERTIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.TERTIUM, 3, 1, p -> p.group(ITEM_GROUP)), "tertium_leggings");
    public static final Item TERTIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.TERTIUM, 3, 1, p -> p.group(ITEM_GROUP)), "tertium_boots");

    public static final Item IMPERIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.IMPERIUM, 4, 1, p -> p.group(ITEM_GROUP)), "imperium_helmet");
    public static final Item IMPERIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.IMPERIUM, 4, 1, p -> p.group(ITEM_GROUP)), "imperium_chestplate");
    public static final Item IMPERIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.IMPERIUM, 4, 1, p -> p.group(ITEM_GROUP)), "imperium_leggings");
    public static final Item IMPERIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.IMPERIUM, 4, 1, p -> p.group(ITEM_GROUP)), "imperium_boots");

    public static final Item SUPREMIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.SUPREMIUM, 5, 1, p -> p.group(ITEM_GROUP)), "supremium_helmet");
    public static final Item SUPREMIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.SUPREMIUM, 5, 1, p -> p.group(ITEM_GROUP)), "supremium_chestplate");
    public static final Item SUPREMIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.SUPREMIUM, 5, 1, p -> p.group(ITEM_GROUP)), "supremium_leggings");
    public static final Item SUPREMIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.SUPREMIUM, 5, 1, p -> p.group(ITEM_GROUP)), "supremium_boots");

    public static final Item AWAKENED_SUPREMIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_helmet");
    public static final Item AWAKENED_SUPREMIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_chestplate");
    public static final Item AWAKENED_SUPREMIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_leggings");
    public static final Item AWAKENED_SUPREMIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2, p -> p.group(ITEM_GROUP)), "awakened_supremium_boots");

    public static void registerItems() {
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, item));

        CropRegistry.getInstance().onRegisterItems();

        GEAR_ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, item));

        AugmentRegistry.getInstance().onRegisterItems();
    }

    private static Item register(String name) {
        return register(new BaseItem(p -> p.group(ITEM_GROUP)), name);
    }

    private static Item register(Item item, String name) {
        Identifier id = new Identifier(MOD_ID, name);
        ITEMS.put(id, item);
        return item;
    }

    private static Item registerGear(Item item, String name) {
        Identifier id = new Identifier(MOD_ID, name);
        GEAR_ITEMS.put(id, item);
        return item;
    }
}
