package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.items.*;
import com.alex.mysticalagriculture.items.armor.EssenceBootsItem;
import com.alex.mysticalagriculture.items.armor.EssenceChestplateItem;
import com.alex.mysticalagriculture.items.armor.EssenceHelmetItem;
import com.alex.mysticalagriculture.items.armor.EssenceLeggingsItem;
import com.alex.mysticalagriculture.items.tool.*;
import com.alex.mysticalagriculture.lib.ModArmorMaterial;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.lib.ModItemTier;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.util.MachineUpgradeTier;
import com.alex.mysticalagriculture.cucumber.helper.ColorHelper;
import com.alex.mysticalagriculture.cucumber.item.BaseItem;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Identifier;
import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ModItems {
    public static final Map<ResourceLocation, Item> ITEMS = new LinkedHashMap<>();

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
    public static final Item UPGRADE_BASE = register("upgrade_base");
    public static final Item INFERIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.INFERIUM), "inferium_upgrade");
    public static final Item PRUDENTIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.PRUDENTIUM), "prudentium_upgrade");
    public static final Item TERTIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.TERTIUM), "tertium_upgrade");
    public static final Item IMPERIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.IMPERIUM), "imperium_upgrade");
    public static final Item SUPREMIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.SUPREMIUM), "supremium_upgrade");
    public static final Item AWAKENED_SUPREMIUM_UPGRADE = register(new MachineUpgradeItem(MachineUpgradeTier.AWAKENED_SUPREMIUM), "awakened_supremium_upgrade");

    public static final Item INFERIUM_SWORD = register(new EssenceSwordItem(ModItemTier.INFERIUM, 1, 1), "inferium_sword");
    public static final Item INFERIUM_PICKAXE = register(new EssencePickaxeItem(ModItemTier.INFERIUM, 1, 1), "inferium_pickaxe");
    public static final Item INFERIUM_SHOVEL = register(new EssenceShovelItem(ModItemTier.INFERIUM, 1, 1), "inferium_shovel");
    public static final Item INFERIUM_AXE = register(new EssenceAxeItem(ModItemTier.INFERIUM, 1, 1), "inferium_axe");
    public static final Item INFERIUM_HOE = register(new EssenceHoeItem(ModItemTier.INFERIUM, 1, 1), "inferium_hoe");
    public static final Item INFERIUM_WATERING_CAN = register(new EssenceWateringCanItem(3, 0.25, CropTier.ONE.getTextColor()), "inferium_watering_can");
    public static final Item INFERIUM_BOW = register(new EssenceBowItem(ModItemTier.INFERIUM, 1, 1, 1.1F), "inferium_bow");
    public static final Item INFERIUM_CROSSBOW = register(new EssenceCrossbowItem(ModItemTier.INFERIUM, 1, 1, 1.1F), "inferium_crossbow");
    //public static final Item INFERIUM_SHEARS =
    //public static final Item INFERIUM_FISHING_ROD =
    //public static final Item INFERIUM_SICKLE =
    //public static final Item INFERIUM_SCYTHE =

    public static final Item PRUDENTIUM_SWORD = register(new EssenceSwordItem(ModItemTier.PRUDENTIUM, 2, 1), "prudentium_sword");
    public static final Item PRUDENTIUM_PICKAXE = register(new EssencePickaxeItem(ModItemTier.PRUDENTIUM, 2, 1), "prudentium_pickaxe");
    public static final Item PRUDENTIUM_SHOVEL = register(new EssenceShovelItem(ModItemTier.PRUDENTIUM, 2, 1), "prudentium_shovel");
    public static final Item PRUDENTIUM_AXE = register(new EssenceAxeItem(ModItemTier.PRUDENTIUM, 2, 1), "prudentium_axe");
    public static final Item PRUDENTIUM_HOE = register(new EssenceHoeItem(ModItemTier.PRUDENTIUM, 2, 1), "prudentium_hoe");
    public static final Item PRUDENTIUM_WATERING_CAN = register(new EssenceWateringCanItem(5, 0.30, CropTier.TWO.getTextColor()), "prudentium_watering_can");
    public static final Item PRUDENTIUM_BOW = register(new EssenceBowItem(ModItemTier.PRUDENTIUM, 2, 1, 1.2F), "prudentium_bow");
    public static final Item PRUDENTIUM_CROSSBOW = register(new EssenceCrossbowItem(ModItemTier.PRUDENTIUM, 2, 1, 1.2F), "prudentium_crossbow");

    public static final Item TERTIUM_SWORD = register(new EssenceSwordItem(ModItemTier.TERTIUM, 3, 1), "tertium_sword");
    public static final Item TERTIUM_PICKAXE = register(new EssencePickaxeItem(ModItemTier.TERTIUM, 3, 1), "tertium_pickaxe");
    public static final Item TERTIUM_SHOVEL = register(new EssenceShovelItem(ModItemTier.TERTIUM, 3, 1), "tertium_shovel");
    public static final Item TERTIUM_AXE = register(new EssenceAxeItem(ModItemTier.TERTIUM, 3, 1), "tertium_axe");
    public static final Item TERTIUM_HOE = register(new EssenceHoeItem(ModItemTier.TERTIUM, 3, 1), "tertium_hoe");
    public static final Item TERTIUM_WATERING_CAN = register(new EssenceWateringCanItem(7, 0.35, CropTier.THREE.getTextColor()), "tertium_watering_can");
    public static final Item TERTIUM_BOW = register(new EssenceBowItem(ModItemTier.TERTIUM, 3, 1, 1.35F), "tertium_bow");
    public static final Item TERTIUM_CROSSBOW = register(new EssenceCrossbowItem(ModItemTier.TERTIUM, 3, 1, 1.35F), "tertium_crossbow");

    public static final Item IMPERIUM_SWORD = register(new EssenceSwordItem(ModItemTier.IMPERIUM, 4, 1), "imperium_sword");
    public static final Item IMPERIUM_PICKAXE = register(new EssencePickaxeItem(ModItemTier.IMPERIUM, 4, 1), "imperium_pickaxe");
    public static final Item IMPERIUM_SHOVEL = register(new EssenceShovelItem(ModItemTier.IMPERIUM, 4, 1), "imperium_shovel");
    public static final Item IMPERIUM_AXE = register(new EssenceAxeItem(ModItemTier.IMPERIUM, 4, 1), "imperium_axe");
    public static final Item IMPERIUM_HOE = register(new EssenceHoeItem(ModItemTier.IMPERIUM, 4, 1), "imperium_hoe");
    public static final Item IMPERIUM_WATERING_CAN = register(new EssenceWateringCanItem(9, 0.40, CropTier.FOUR.getTextColor()), "imperium_watering_can");
    public static final Item IMPERIUM_BOW = register(new EssenceBowItem(ModItemTier.IMPERIUM, 4, 1, 1.55F), "imperium_bow");
    public static final Item IMPERIUM_CROSSBOW = register(new EssenceCrossbowItem(ModItemTier.IMPERIUM, 4, 1, 1.55F), "imperium_crossbow");

    public static final Item SUPREMIUM_SWORD = register(new EssenceSwordItem(ModItemTier.SUPREMIUM, 5, 1), "supremium_sword");
    public static final Item SUPREMIUM_PICKAXE = register(new EssencePickaxeItem(ModItemTier.SUPREMIUM, 5, 1), "supremium_pickaxe");
    public static final Item SUPREMIUM_SHOVEL = register(new EssenceShovelItem(ModItemTier.SUPREMIUM, 5, 1), "supremium_shovel");
    public static final Item SUPREMIUM_AXE = register(new EssenceAxeItem(ModItemTier.SUPREMIUM, 5, 1), "supremium_axe");
    public static final Item SUPREMIUM_HOE = register(new EssenceHoeItem(ModItemTier.SUPREMIUM, 5, 1), "supremium_hoe");
    public static final Item SUPREMIUM_WATERING_CAN = register(new EssenceWateringCanItem(11, 0.45, CropTier.FIVE.getTextColor()), "supremium_watering_can");
    public static final Item SUPREMIUM_BOW = register(new EssenceBowItem(ModItemTier.SUPREMIUM, 5, 1, 1.80F), "supremium_bow");
    public static final Item SUPREMIUM_CROSSBOW = register(new EssenceCrossbowItem(ModItemTier.SUPREMIUM, 5, 1, 1.80F), "supremium_crossbow");

    public static final Item AWAKENED_SUPREMIUM_SWORD = register(new EssenceSwordItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_sword");
    public static final Item AWAKENED_SUPREMIUM_PICKAXE = register(new EssencePickaxeItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_pickaxe");
    public static final Item AWAKENED_SUPREMIUM_SHOVEL = register(new EssenceShovelItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_shovel");
    public static final Item AWAKENED_SUPREMIUM_AXE = register(new EssenceAxeItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_axe");
    public static final Item AWAKENED_SUPREMIUM_HOE = register(new EssenceHoeItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_hoe");
    public static final Item AWAKENED_SUPREMIUM_WATERING_CAN = register(new EssenceWateringCanItem(13, 0.50, CropTier.FIVE.getTextColor()), "awakened_supremium_watering_can");
    public static final Item AWAKENED_SUPREMIUM_BOW = register(new EssenceBowItem(ModItemTier.AWAKENED_SUPREMIUM, 5, 2, 2.10F), "awakened_supremium_bow");
    public static final Item AWAKENED_SUPREMIUM_CROSSBOW = register(new EssenceCrossbowItem(ModItemTier.AWAKENED_SUPREMIUM,  5, 2, 2.10F), "awakened_supremium_crossbow");

    public static final Item INFERIUM_HELMET = register(new EssenceHelmetItem(ModArmorMaterial.INFERIUM, 1, 1), "inferium_helmet");
    public static final Item INFERIUM_CHESTPLATE = register(new EssenceChestplateItem(ModArmorMaterial.INFERIUM, 1, 1), "inferium_chestplate");
    public static final Item INFERIUM_LEGGINGS = register(new EssenceLeggingsItem(ModArmorMaterial.INFERIUM, 1, 1), "inferium_leggings");
    public static final Item INFERIUM_BOOTS = register(new EssenceBootsItem(ModArmorMaterial.INFERIUM, 1, 1), "inferium_boots");
   
    public static final Item PRUDENTIUM_HELMET = register(new EssenceHelmetItem(ModArmorMaterial.PRUDENTIUM, 2, 1), "prudentium_helmet");
    public static final Item PRUDENTIUM_CHESTPLATE = register(new EssenceChestplateItem(ModArmorMaterial.PRUDENTIUM, 2, 1), "prudentium_chestplate");
    public static final Item PRUDENTIUM_LEGGINGS = register(new EssenceLeggingsItem(ModArmorMaterial.PRUDENTIUM, 2, 1), "prudentium_leggings");
    public static final Item PRUDENTIUM_BOOTS = register(new EssenceBootsItem(ModArmorMaterial.PRUDENTIUM, 2, 1), "prudentium_boots");

    public static final Item TERTIUM_HELMET = register(new EssenceHelmetItem(ModArmorMaterial.TERTIUM, 3, 1), "tertium_helmet");
    public static final Item TERTIUM_CHESTPLATE = register(new EssenceChestplateItem(ModArmorMaterial.TERTIUM, 3, 1), "tertium_chestplate");
    public static final Item TERTIUM_LEGGINGS = register(new EssenceLeggingsItem(ModArmorMaterial.TERTIUM, 3, 1), "tertium_leggings");
    public static final Item TERTIUM_BOOTS = register(new EssenceBootsItem(ModArmorMaterial.TERTIUM, 3, 1), "tertium_boots");

    public static final Item IMPERIUM_HELMET = register(new EssenceHelmetItem(ModArmorMaterial.IMPERIUM, 4, 1), "imperium_helmet");
    public static final Item IMPERIUM_CHESTPLATE = register(new EssenceChestplateItem(ModArmorMaterial.IMPERIUM, 4, 1), "imperium_chestplate");
    public static final Item IMPERIUM_LEGGINGS = register(new EssenceLeggingsItem(ModArmorMaterial.IMPERIUM, 4, 1), "imperium_leggings");
    public static final Item IMPERIUM_BOOTS = register(new EssenceBootsItem(ModArmorMaterial.IMPERIUM, 4, 1), "imperium_boots");

    public static final Item SUPREMIUM_HELMET = register(new EssenceHelmetItem(ModArmorMaterial.SUPREMIUM, 5, 1), "supremium_helmet");
    public static final Item SUPREMIUM_CHESTPLATE = register(new EssenceChestplateItem(ModArmorMaterial.SUPREMIUM, 5, 1), "supremium_chestplate");
    public static final Item SUPREMIUM_LEGGINGS = register(new EssenceLeggingsItem(ModArmorMaterial.SUPREMIUM, 5, 1), "supremium_leggings");
    public static final Item SUPREMIUM_BOOTS = register(new EssenceBootsItem(ModArmorMaterial.SUPREMIUM, 5, 1), "supremium_boots");

    public static final Item AWAKENED_SUPREMIUM_HELMET = register(new EssenceHelmetItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_helmet");
    public static final Item AWAKENED_SUPREMIUM_CHESTPLATE = register(new EssenceChestplateItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_chestplate");
    public static final Item AWAKENED_SUPREMIUM_LEGGINGS = register(new EssenceLeggingsItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_leggings");
    public static final Item AWAKENED_SUPREMIUM_BOOTS = register(new EssenceBootsItem(ModArmorMaterial.AWAKENED_SUPREMIUM, 5, 2), "awakened_supremium_boots");

    public static void registerItems() {
        ITEMS.forEach((id, item) -> Registry.register(Registries.ITEM, id, item));

        CropRegistry.getInstance().onRegisterItems();
        AugmentRegistry.getInstance().onRegisterItems();
    }

    private static Item register(String name) {
        return register(new BaseItem(), name);
    }

    private static Item register(Item item, String name) {
        Identifier id = new Identifier(MOD_ID, name);
        ITEMS.put(id, item);
        return item;
    }
}
