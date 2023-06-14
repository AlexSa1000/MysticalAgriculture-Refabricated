package com.alex.mysticalagriculture.init;

import com.alex.cucumber.item.BaseItem;
import com.alex.cucumber.item.tool.BaseScytheItem;
import com.alex.cucumber.item.tool.BaseSickleItem;
import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.items.*;
import com.alex.mysticalagriculture.items.armor.EssenceBootsItem;
import com.alex.mysticalagriculture.items.armor.EssenceChestplateItem;
import com.alex.mysticalagriculture.items.armor.EssenceHelmetItem;
import com.alex.mysticalagriculture.items.armor.EssenceLeggingsItem;
import com.alex.mysticalagriculture.items.tool.*;
import com.alex.mysticalagriculture.lib.ModArmorMaterial;
import com.alex.mysticalagriculture.lib.ModItemTier;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.util.MachineUpgradeTier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ModItems {
    public static final Map<ResourceLocation, Item> ITEMS = new LinkedHashMap<>();
    public static final Map<ResourceLocation, Item> GEAR_ITEMS = new LinkedHashMap<>();

    public static final Item PROSPERITY_SHARD = register("prosperity_shard");
    public static final Item INFERIUM_ESSENCE = register(new EssenceItem(CropTier.ONE), "inferium_essence");
    public static final Item PRUDENTIUM_ESSENCE  = register(new EssenceItem(CropTier.TWO), "prudentium_essence");
    public static final Item TERTIUM_ESSENCE  = register(new EssenceItem(CropTier.THREE), "tertium_essence");
    public static final Item IMPERIUM_ESSENCE  = register(new EssenceItem(CropTier.FOUR), "imperium_essence");
    public static final Item SUPREMIUM_ESSENCE  = register(new EssenceItem(CropTier.FIVE), "supremium_essence");
    public static final Item AWAKENED_SUPREMIUM_ESSENCE = register("awakened_supremium_essence");
    public static final Item SOULIUM_DUST = register("soulium_dust");
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
    public static final Item SOUL_DUST = register("soul_dust");
    public static final Item COGNIZANT_DUST = register("cognizant_dust");
    public static final Item SOULIUM_DAGGER = register(new SouliumDaggerItem(ModItemTier.SOULIUM, SouliumDaggerItem.DaggerType.BASIC), "soulium_dagger");
    public static final Item PASSIVE_SOULIUM_DAGGER = register(new SouliumDaggerItem(ModItemTier.SOULIUM, SouliumDaggerItem.DaggerType.PASSIVE), "passive_soulium_dagger");
    public static final Item HOSTILE_SOULIUM_DAGGER = register(new SouliumDaggerItem(ModItemTier.SOULIUM, SouliumDaggerItem.DaggerType.HOSTILE), "hostile_soulium_dagger");
    public static final Item CREATIVE_SOULIUM_DAGGER = register(new SouliumDaggerItem(ModItemTier.SOULIUM, SouliumDaggerItem.DaggerType.CREATIVE), "creative_soulium_dagger");
    public static final Item INFUSION_CRYSTAL = register(new InfusionCrystalItem(), "infusion_crystal");
    public static final Item MASTER_INFUSION_CRYSTAL = register(new MasterInfusionCrystalItem(), "master_infusion_crystal");
    public static final Item FERTILIZED_ESSENCE = register(new FertilizedEssenceItem(), "fertilized_essence");
    public static final Item MYSTICAL_FERTILIZER = register(new MysticalFertilizerItem(), "mystical_fertilizer");
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
    public static final Item EXPERIENCE_DROPLET = register(new ExperienceDropletItem(), "experience_droplet");
    public static final Item WAND = register(new WandItem(), "wand");
    public static final Item BLANK_SKULL = register("blank_skull");
    public static final Item BLANK_RECORD = register("blank_record");
    public static final Item UNATTUNED_AUGMENT = register("unattuned_augment");
    public static final Item SOUL_JAR = register(new SoulJarItem(), "soul_jar");
    public static final Item EXPERIENCE_CAPSULE = register(new ExperienceCapsuleItem(), "experience_capsule");
    public static final Item WATERING_CAN = register(new WateringCanItem(3, 0.25), "watering_can");
    public static final Item DIAMOND_SICKLE = register(new BaseSickleItem(Tiers.DIAMOND, 3), "diamond_sickle");
    public static final Item DIAMOND_SCYTHE = register(new BaseScytheItem(Tiers.DIAMOND, 3), "diamond_scythe");
    public static final Item UPGRADE_BASE = register("upgrade_base");
    public static final Item INFERIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.INFERIUM), "inferium_upgrade");
    public static final Item PRUDENTIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.PRUDENTIUM), "prudentium_upgrade");
    public static final Item TERTIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.TERTIUM), "tertium_upgrade");
    public static final Item IMPERIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.IMPERIUM), "imperium_upgrade");
    public static final Item SUPREMIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.SUPREMIUM), "supremium_upgrade");
    public static final Item AWAKENED_SUPREMIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.AWAKENED_SUPREMIUM), "awakened_supremium_upgrade");

    public static final Item INFERIUM_SWORD = registerGear(new EssenceSwordItem(ModItemTier.INFERIUM, 1, 1), "inferium_sword");
    public static final Item INFERIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModItemTier.INFERIUM, 1, 1), "inferium_pickaxe");
    public static final Item INFERIUM_SHOVEL = registerGear(new EssenceShovelItem(ModItemTier.INFERIUM, 1, 1), "inferium_shovel");
    public static final Item INFERIUM_AXE = registerGear(new EssenceAxeItem(ModItemTier.INFERIUM, 1, 1), "inferium_axe");
    public static final Item INFERIUM_HOE = registerGear(new EssenceHoeItem(ModItemTier.INFERIUM, 1, 1), "inferium_hoe");
    public static final Item INFERIUM_STAFF = registerGear(new EssenceStaffItem(1, 1), "inferium_staff");
    public static final Item INFERIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(3, 0.25, CropTier.ONE.getTextColor()), "inferium_watering_can");
    public static final Item INFERIUM_BOW = registerGear(new EssenceBowItem(ModItemTier.INFERIUM, 1, 1, 1.1F), "inferium_bow");
    public static final Item INFERIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModItemTier.INFERIUM, 1, 1, 1.1F), "inferium_crossbow");
    public static final Item INFERIUM_SHEARS = registerGear(new EssenceShearsItem(ModItemTier.INFERIUM, 1, 1), "inferium_shears");
    public static final Item INFERIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModItemTier.INFERIUM, 1, 1), "inferium_fishing_rod");
    public static final Item INFERIUM_SICKLE = registerGear(new EssenceSickleItem(ModItemTier.INFERIUM, 3, CropTier.ONE.getTextColor(), 1, 1), "inferium_sickle");
    public static final Item INFERIUM_SCYTHE = registerGear(new EssenceScytheItem(ModItemTier.INFERIUM, 3, CropTier.ONE.getTextColor(), 1, 1), "inferium_scythe");

    public static final Item PRUDENTIUM_SWORD = registerGear(new EssenceSwordItem(ModItemTier.PRUDENTIUM, 2, 1), "prudentium_sword");
    public static final Item PRUDENTIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModItemTier.PRUDENTIUM, 2, 1), "prudentium_pickaxe");
    public static final Item PRUDENTIUM_SHOVEL = registerGear(new EssenceShovelItem(ModItemTier.PRUDENTIUM, 2, 1), "prudentium_shovel");
    public static final Item PRUDENTIUM_AXE = registerGear(new EssenceAxeItem(ModItemTier.PRUDENTIUM, 2, 1), "prudentium_axe");
    public static final Item PRUDENTIUM_HOE = registerGear(new EssenceHoeItem(ModItemTier.PRUDENTIUM, 2, 1), "prudentium_hoe");
    public static final Item PRUDENTIUM_STAFF = registerGear(new EssenceStaffItem(2, 1), "prudentium_staff");
    public static final Item PRUDENTIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(5, 0.30, CropTier.TWO.getTextColor()), "prudentium_watering_can");
    public static final Item PRUDENTIUM_BOW = registerGear(new EssenceBowItem(ModItemTier.PRUDENTIUM, 2, 1, 1.2F), "prudentium_bow");
    public static final Item PRUDENTIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModItemTier.PRUDENTIUM, 2, 1, 1.2F), "prudentium_crossbow");
    public static final Item PRUDENTIUM_SHEARS = registerGear(new EssenceShearsItem(ModItemTier.PRUDENTIUM, 2, 2), "prudentium_shears");
    public static final Item PRUDENTIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModItemTier.PRUDENTIUM, 2, 2), "prudentium_fishing_rod");
    public static final Item PRUDENTIUM_SICKLE = registerGear(new EssenceSickleItem(ModItemTier.PRUDENTIUM, 4, CropTier.TWO.getTextColor(), 2, 1), "prudentium_sickle");
    public static final Item PRUDENTIUM_SCYTHE = registerGear(new EssenceScytheItem(ModItemTier.PRUDENTIUM, 4, CropTier.TWO.getTextColor(), 2, 1), "prudentium_scythe");

    public static final Item TERTIUM_SWORD = registerGear(new EssenceSwordItem(ModItemTier.TERTIUM, 3, 1), "tertium_sword");
    public static final Item TERTIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModItemTier.TERTIUM, 3, 1), "tertium_pickaxe");
    public static final Item TERTIUM_SHOVEL = registerGear(new EssenceShovelItem(ModItemTier.TERTIUM, 3, 1), "tertium_shovel");
    public static final Item TERTIUM_AXE = registerGear(new EssenceAxeItem(ModItemTier.TERTIUM, 3, 1), "tertium_axe");
    public static final Item TERTIUM_HOE = registerGear(new EssenceHoeItem(ModItemTier.TERTIUM, 3, 1), "tertium_hoe");
    public static final Item TERTIUM_STAFF = registerGear(new EssenceStaffItem(3, 1), "tertium_staff");
    public static final Item TERTIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(7, 0.35, CropTier.THREE.getTextColor()), "tertium_watering_can");
    public static final Item TERTIUM_BOW = registerGear(new EssenceBowItem(ModItemTier.TERTIUM, 3, 1, 1.35F), "tertium_bow");
    public static final Item TERTIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModItemTier.TERTIUM, 3, 1, 1.35F), "tertium_crossbow");
    public static final Item TERTIUM_SHEARS = registerGear(new EssenceShearsItem(ModItemTier.TERTIUM, 3, 3), "tertium_shears");
    public static final Item TERTIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModItemTier.TERTIUM, 3, 3), "tertium_fishing_rod");
    public static final Item TERTIUM_SICKLE = registerGear(new EssenceSickleItem(ModItemTier.TERTIUM, 5, CropTier.THREE.getTextColor(), 3, 1), "tertium_sickle");
    public static final Item TERTIUM_SCYTHE = registerGear(new EssenceScytheItem(ModItemTier.TERTIUM, 5, CropTier.THREE.getTextColor(), 3, 1), "tertium_scythe");

    public static final Item IMPERIUM_SWORD = registerGear(new EssenceSwordItem(ModItemTier.IMPERIUM, 4, 1), "imperium_sword");
    public static final Item IMPERIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModItemTier.IMPERIUM, 4, 1), "imperium_pickaxe");
    public static final Item IMPERIUM_SHOVEL = registerGear(new EssenceShovelItem(ModItemTier.IMPERIUM, 4, 1), "imperium_shovel");
    public static final Item IMPERIUM_AXE = registerGear(new EssenceAxeItem(ModItemTier.IMPERIUM, 4, 1), "imperium_axe");
    public static final Item IMPERIUM_HOE = registerGear(new EssenceHoeItem(ModItemTier.IMPERIUM, 4, 1), "imperium_hoe");
    public static final Item IMPERIUM_STAFF = registerGear(new EssenceStaffItem(4, 1), "imperium_staff");
    public static final Item IMPERIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(9, 0.40, CropTier.FOUR.getTextColor()), "imperium_watering_can");
    public static final Item IMPERIUM_BOW = registerGear(new EssenceBowItem(ModItemTier.IMPERIUM, 4, 1, 1.55F), "imperium_bow");
    public static final Item IMPERIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModItemTier.IMPERIUM, 4, 1, 1.55F), "imperium_crossbow");
    public static final Item IMPERIUM_SHEARS = registerGear(new EssenceShearsItem(ModItemTier.IMPERIUM, 4, 4), "imperium_shears");
    public static final Item IMPERIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModItemTier.IMPERIUM, 4, 4), "imperium_fishing_rod");
    public static final Item IMPERIUM_SICKLE = registerGear(new EssenceSickleItem(ModItemTier.IMPERIUM, 6, CropTier.FOUR.getTextColor(), 4, 1), "imperium_sickle");
    public static final Item IMPERIUM_SCYTHE = registerGear(new EssenceScytheItem(ModItemTier.IMPERIUM, 6, CropTier.FOUR.getTextColor(), 4, 1), "imperium_scythe");

    public static final Item SUPREMIUM_SWORD = registerGear(new EssenceSwordItem(ModItemTier.SUPREMIUM, 5, 1), "supremium_sword");
    public static final Item SUPREMIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModItemTier.SUPREMIUM, 5, 1), "supremium_pickaxe");
    public static final Item SUPREMIUM_SHOVEL = registerGear(new EssenceShovelItem(ModItemTier.SUPREMIUM, 5, 1), "supremium_shovel");
    public static final Item SUPREMIUM_AXE = registerGear(new EssenceAxeItem(ModItemTier.SUPREMIUM, 5, 1), "supremium_axe");
    public static final Item SUPREMIUM_HOE = registerGear(new EssenceHoeItem(ModItemTier.SUPREMIUM, 5, 1), "supremium_hoe");
    public static final Item SUPREMIUM_STAFF = registerGear(new EssenceStaffItem(5, 1), "supremium_staff");
    public static final Item SUPREMIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(11, 0.45, CropTier.FIVE.getTextColor()), "supremium_watering_can");
    public static final Item SUPREMIUM_BOW = registerGear(new EssenceBowItem(ModItemTier.SUPREMIUM, 5, 1, 1.80F), "supremium_bow");
    public static final Item SUPREMIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModItemTier.SUPREMIUM, 5, 1, 1.80F), "supremium_crossbow");
    public static final Item SUPREMIUM_SHEARS = registerGear(new EssenceShearsItem(ModItemTier.SUPREMIUM, 5, 5), "supremium_shears");
    public static final Item SUPREMIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModItemTier.SUPREMIUM, 5, 5), "supremium_fishing_rod");
    public static final Item SUPREMIUM_SICKLE = registerGear(new EssenceSickleItem(ModItemTier.SUPREMIUM, 7, CropTier.FIVE.getTextColor(), 5, 1), "supremium_sickle");
    public static final Item SUPREMIUM_SCYTHE = registerGear(new EssenceScytheItem(ModItemTier.SUPREMIUM, 7, CropTier.FIVE.getTextColor(), 5, 1), "supremium_scythe");

    public static final Item AWAKENED_SUPREMIUM_SWORD = registerGear(new EssenceSwordItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_sword");
    public static final Item AWAKENED_SUPREMIUM_PICKAXE = registerGear(new EssencePickaxeItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_pickaxe");
    public static final Item AWAKENED_SUPREMIUM_SHOVEL = registerGear(new EssenceShovelItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_shovel");
    public static final Item AWAKENED_SUPREMIUM_AXE = registerGear(new EssenceAxeItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_axe");
    public static final Item AWAKENED_SUPREMIUM_HOE = registerGear(new EssenceHoeItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_hoe");
    public static final Item AWAKENED_SUPREMIUM_STAFF = registerGear(new EssenceStaffItem(5, 1), "awakened_supremium_staff");
    public static final Item AWAKENED_SUPREMIUM_WATERING_CAN = registerGear(new EssenceWateringCanItem(13, 0.50, CropTier.FIVE.getTextColor()), "awakened_supremium_watering_can");
    public static final Item AWAKENED_SUPREMIUM_BOW = registerGear(new EssenceBowItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2, 2.10F), "awakened_supremium_bow");
    public static final Item AWAKENED_SUPREMIUM_CROSSBOW = registerGear(new EssenceCrossbowItem(ModItemTier.AWAKENED_SUPREMIUM,  5, 2, 2.10F), "awakened_supremium_crossbow");
    public static final Item AWAKENED_SUPREMIUM_SHEARS = registerGear(new EssenceShearsItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 5), "awakened_supremium_shears");
    public static final Item AWAKENED_SUPREMIUM_FISHING_ROD = registerGear(new EssenceFishingRodItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 5), "awakened_supremium_fishing_rod");
    public static final Item AWAKENED_SUPREMIUM_SICKLE = registerGear(new EssenceSickleItem(ModItemTier.AWAKENED_SUPREMIUM, 8, CropTier.FIVE.getTextColor(), 5, 2), "awakened_supremium_sickle");
    public static final Item AWAKENED_SUPREMIUM_SCYTHE = registerGear(new EssenceScytheItem(ModItemTier.AWAKENED_SUPREMIUM, 8, CropTier.FIVE.getTextColor(), 5, 2), "awakened_supremium_scythe");

    public static final Item INFERIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.INFERIUM, 1, 1), "inferium_helmet");
    public static final Item INFERIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.INFERIUM, 1, 1), "inferium_chestplate");
    public static final Item INFERIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.INFERIUM, 1, 1), "inferium_leggings");
    public static final Item INFERIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.INFERIUM, 1, 1), "inferium_boots");
   
    public static final Item PRUDENTIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.PRUDENTIUM, 2, 1), "prudentium_helmet");
    public static final Item PRUDENTIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.PRUDENTIUM, 2, 1), "prudentium_chestplate");
    public static final Item PRUDENTIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.PRUDENTIUM, 2, 1), "prudentium_leggings");
    public static final Item PRUDENTIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.PRUDENTIUM, 2, 1), "prudentium_boots");

    public static final Item TERTIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.TERTIUM, 3, 1), "tertium_helmet");
    public static final Item TERTIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.TERTIUM, 3, 1), "tertium_chestplate");
    public static final Item TERTIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.TERTIUM, 3, 1), "tertium_leggings");
    public static final Item TERTIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.TERTIUM, 3, 1), "tertium_boots");

    public static final Item IMPERIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.IMPERIUM, 4, 1), "imperium_helmet");
    public static final Item IMPERIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.IMPERIUM, 4, 1), "imperium_chestplate");
    public static final Item IMPERIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.IMPERIUM, 4, 1), "imperium_leggings");
    public static final Item IMPERIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.IMPERIUM, 4, 1), "imperium_boots");

    public static final Item SUPREMIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.SUPREMIUM, 5, 1), "supremium_helmet");
    public static final Item SUPREMIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.SUPREMIUM, 5, 1), "supremium_chestplate");
    public static final Item SUPREMIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.SUPREMIUM, 5, 1), "supremium_leggings");
    public static final Item SUPREMIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.SUPREMIUM, 5, 1), "supremium_boots");

    public static final Item AWAKENED_SUPREMIUM_HELMET = registerGear(new EssenceHelmetItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_helmet");
    public static final Item AWAKENED_SUPREMIUM_CHESTPLATE = registerGear(new EssenceChestplateItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_chestplate");
    public static final Item AWAKENED_SUPREMIUM_LEGGINGS = registerGear(new EssenceLeggingsItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_leggings");
    public static final Item AWAKENED_SUPREMIUM_BOOTS = registerGear(new EssenceBootsItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_boots");

    public static void registerItems() {
        ITEMS.forEach((id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item));

        CropRegistry.getInstance().onRegisterItems();

        GEAR_ITEMS.forEach((id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item));

        AugmentRegistry.getInstance().onRegisterItems();
    }

    private static Item register(String name) {
        return register(new BaseItem(), name);
    }

    private static Item register(Item item, String name) {
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        ITEMS.put(id, item);
        return item;
    }

    private static Item registerGear(Item item, String name) {
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        GEAR_ITEMS.put(id, item);
        return item;
    }
}
