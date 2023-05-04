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
import com.alex.mysticalagriculture.lib.ModToolMaterial;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.util.MachineUpgradeTier;
import com.alex.mysticalagriculture.zucchini.helper.ColorHelper;
import com.alex.mysticalagriculture.zucchini.item.BaseItem;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class Items {
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

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
    public static final Item SOULIUM_DAGGER = register(new SouliumDaggerItem(ModToolMaterial.SOULIUM, SouliumDaggerItem.DaggerType.BASIC), "soulium_dagger");
    public static final Item PASSIVE_SOULIUM_DAGGER = register(new SouliumDaggerItem(ModToolMaterial.SOULIUM, SouliumDaggerItem.DaggerType.PASSIVE), "passive_soulium_dagger");
    public static final Item HOSTILE_SOULIUM_DAGGER = register(new SouliumDaggerItem(ModToolMaterial.SOULIUM, SouliumDaggerItem.DaggerType.HOSTILE), "hostile_soulium_dagger");
    public static final Item CREATIVE_SOULIUM_DAGGER = register(new SouliumDaggerItem(ModToolMaterial.SOULIUM, SouliumDaggerItem.DaggerType.CREATIVE), "creative_soulium_dagger");
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

    public static final Item INFERIUM_SWORD = register(new EssenceSwordItem(ModToolMaterial.INFERIUM, 1, 1), "inferium_sword");
    public static final Item INFERIUM_PICKAXE = register(new EssencePickaxeItem(ModToolMaterial.INFERIUM, 1, 1), "inferium_pickaxe");
    public static final Item INFERIUM_SHOVEL = register(new EssenceShovelItem(ModToolMaterial.INFERIUM, 1, 1), "inferium_shovel");
    public static final Item INFERIUM_AXE = register(new EssenceAxeItem(ModToolMaterial.INFERIUM, 1, 1), "inferium_axe");
    public static final Item INFERIUM_HOE = register(new EssenceHoeItem(ModToolMaterial.INFERIUM, 1, 1), "inferium_hoe");
    public static final Item INFERIUM_WATERING_CAN = register(new EssenceWateringCanItem(3, 0.25, CropTier.ONE.getTextColor()), "inferium_watering_can");
    //public static final Item INFERIUM_BOW =
    //public static final Item INFERIUM_CROSSBOW =
    //public static final Item INFERIUM_SHEARS =
    //public static final Item INFERIUM_FISHING_ROD =
    //public static final Item INFERIUM_SICKLE =
    //public static final Item INFERIUM_SCYTHE =

    public static final Item PRUDENTIUM_SWORD = register(new EssenceSwordItem(ModToolMaterial.PRUDENTIUM, 2, 1), "prudentium_sword");
    public static final Item PRUDENTIUM_PICKAXE = register(new EssencePickaxeItem(ModToolMaterial.PRUDENTIUM, 2, 1), "prudentium_pickaxe");
    public static final Item PRUDENTIUM_SHOVEL = register(new EssenceShovelItem(ModToolMaterial.PRUDENTIUM, 2, 1), "prudentium_shovel");
    public static final Item PRUDENTIUM_AXE = register(new EssenceAxeItem(ModToolMaterial.PRUDENTIUM, 2, 1), "prudentium_axe");
    public static final Item PRUDENTIUM_HOE = register(new EssenceHoeItem(ModToolMaterial.PRUDENTIUM, 2, 1), "prudentium_hoe");
    public static final Item PRUDENTIUM_WATERING_CAN = register(new EssenceWateringCanItem(5, 0.30, CropTier.TWO.getTextColor()), "prudentium_watering_can");

    public static final Item TERTIUM_SWORD = register(new EssenceSwordItem(ModToolMaterial.TERTIUM, 3, 1), "tertium_sword");
    public static final Item TERTIUM_PICKAXE = register(new EssencePickaxeItem(ModToolMaterial.TERTIUM, 3, 1), "tertium_pickaxe");
    public static final Item TERTIUM_SHOVEL = register(new EssenceShovelItem(ModToolMaterial.TERTIUM, 3, 1), "tertium_shovel");
    public static final Item TERTIUM_AXE = register(new EssenceAxeItem(ModToolMaterial.TERTIUM, 3, 1), "tertium_axe");
    public static final Item TERTIUM_HOE = register(new EssenceHoeItem(ModToolMaterial.TERTIUM, 3, 1), "tertium_hoe");
    public static final Item TERTIUM_WATERING_CAN = register(new EssenceWateringCanItem(7, 0.35, CropTier.THREE.getTextColor()), "tertium_watering_can");

    public static final Item IMPERIUM_SWORD = register(new EssenceSwordItem(ModToolMaterial.IMPERIUM, 4, 1), "imperium_sword");
    public static final Item IMPERIUM_PICKAXE = register(new EssencePickaxeItem(ModToolMaterial.IMPERIUM, 4, 1), "imperium_pickaxe");
    public static final Item IMPERIUM_SHOVEL = register(new EssenceShovelItem(ModToolMaterial.IMPERIUM, 4, 1), "imperium_shovel");
    public static final Item IMPERIUM_AXE = register(new EssenceAxeItem(ModToolMaterial.IMPERIUM, 4, 1), "imperium_axe");
    public static final Item IMPERIUM_HOE = register(new EssenceHoeItem(ModToolMaterial.IMPERIUM, 4, 1), "imperium_hoe");
    public static final Item IMPERIUM_WATERING_CAN = register(new EssenceWateringCanItem(9, 0.40, CropTier.FOUR.getTextColor()), "imperium_watering_can");

    public static final Item SUPREMIUM_SWORD = register(new EssenceSwordItem(ModToolMaterial.SUPREMIUM, 5, 1), "supremium_sword");
    public static final Item SUPREMIUM_PICKAXE = register(new EssencePickaxeItem(ModToolMaterial.SUPREMIUM, 5, 1), "supremium_pickaxe");
    public static final Item SUPREMIUM_SHOVEL = register(new EssenceShovelItem(ModToolMaterial.SUPREMIUM, 5, 1), "supremium_shovel");
    public static final Item SUPREMIUM_AXE = register(new EssenceAxeItem(ModToolMaterial.SUPREMIUM, 5, 1), "supremium_axe");
    public static final Item SUPREMIUM_HOE = register(new EssenceHoeItem(ModToolMaterial.SUPREMIUM, 5, 1), "supremium_hoe");
    public static final Item SUPREMIUM_WATERING_CAN = register(new EssenceWateringCanItem(11, 0.45, CropTier.FIVE.getTextColor()), "supremium_watering_can");

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

    public static void onColors() {
        ColorProviderRegistry.ITEM.register((stack, tint) -> {
                    MobSoulType type = MobSoulUtils.getType(stack);
                    return tint == 1 && type != null ? type.getColor() : -1;
                }, Items.SOUL_JAR);

        ColorProviderRegistry.ITEM.register((stack, tint) -> {
            float damage = (float) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage();
            return ColorHelper.saturate(0x00D9D9, damage);
        }, Items.INFUSION_CRYSTAL);

        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.AIR.getEssenceColor(), Items.AIR_AGGLOMERATIO);
        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.EARTH.getEssenceColor(), Items.EARTH_AGGLOMERATIO);
        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.WATER.getEssenceColor(), Items.WATER_AGGLOMERATIO);
        ColorProviderRegistry.ITEM.register((stack, tint) -> ModCrops.FIRE.getEssenceColor(), Items.FIRE_AGGLOMERATIO);
    }
}
