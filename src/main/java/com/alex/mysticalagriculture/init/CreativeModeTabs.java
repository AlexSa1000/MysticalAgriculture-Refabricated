package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.registry.AugmentRegistry;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import com.alex.mysticalagriculture.zucchini.helper.NBTHelper;
import com.alex.mysticalagriculture.zucchini.util.FeatureFlagDisplayItemGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeModeTabs {
    
    public static final ItemGroup.EntryCollector displayItems = FeatureFlagDisplayItemGenerator.create((parameters, output) -> {
        var stack = ItemStack.EMPTY;

        output.add(Blocks.PROSPERITY_BLOCK);
        output.add(Blocks.INFERIUM_BLOCK);
        output.add(Blocks.PRUDENTIUM_BLOCK);
        output.add(Blocks.TERTIUM_BLOCK);
        output.add(Blocks.IMPERIUM_BLOCK);
        output.add(Blocks.SUPREMIUM_BLOCK);
        output.add(Blocks.AWAKENED_SUPREMIUM_BLOCK);
        output.add(Blocks.SOULIUM_BLOCK);
        output.add(Blocks.PROSPERITY_INGOT_BLOCK);
        output.add(Blocks.INFERIUM_INGOT_BLOCK);
        output.add(Blocks.PRUDENTIUM_INGOT_BLOCK);
        output.add(Blocks.TERTIUM_INGOT_BLOCK);
        output.add(Blocks.IMPERIUM_INGOT_BLOCK);
        output.add(Blocks.SUPREMIUM_INGOT_BLOCK);
        output.add(Blocks.AWAKENED_SUPREMIUM_INGOT_BLOCK);
        output.add(Blocks.SOULIUM_INGOT_BLOCK);
        output.add(Blocks.PROSPERITY_GEMSTONE_BLOCK);
        output.add(Blocks.INFERIUM_GEMSTONE_BLOCK);
        output.add(Blocks.PRUDENTIUM_GEMSTONE_BLOCK);
        output.add(Blocks.TERTIUM_GEMSTONE_BLOCK);
        output.add(Blocks.IMPERIUM_GEMSTONE_BLOCK);
        output.add(Blocks.SUPREMIUM_GEMSTONE_BLOCK);
        output.add(Blocks.AWAKENED_SUPREMIUM_GEMSTONE_BLOCK);
        output.add(Blocks.SOULIUM_GEMSTONE_BLOCK);
        output.add(Blocks.INFERIUM_FARMLAND);
        output.add(Blocks.PRUDENTIUM_FARMLAND);
        output.add(Blocks.TERTIUM_FARMLAND);
        output.add(Blocks.IMPERIUM_FARMLAND);
        output.add(Blocks.SUPREMIUM_FARMLAND);
        output.add(Blocks.INFERIUM_GROWTH_ACCELERATOR);
        output.add(Blocks.PRUDENTIUM_GROWTH_ACCELERATOR);
        output.add(Blocks.TERTIUM_GROWTH_ACCELERATOR);
        output.add(Blocks.IMPERIUM_GROWTH_ACCELERATOR);
        output.add(Blocks.SUPREMIUM_GROWTH_ACCELERATOR);
        output.add(Blocks.INFERIUM_FURNACE);
        output.add(Blocks.PRUDENTIUM_FURNACE);
        output.add(Blocks.TERTIUM_FURNACE);
        output.add(Blocks.IMPERIUM_FURNACE);
        output.add(Blocks.SUPREMIUM_FURNACE);
        output.add(Blocks.AWAKENED_SUPREMIUM_FURNACE);
        output.add(Blocks.PROSPERITY_ORE);
        output.add(Blocks.DEEPSLATE_PROSPERITY_ORE);
        output.add(Blocks.INFERIUM_ORE);
        output.add(Blocks.DEEPSLATE_INFERIUM_ORE);
        output.add(Blocks.SOULIUM_ORE);
        output.add(Blocks.SOULSTONE);
        output.add(Blocks.SOULSTONE_COBBLE);
        output.add(Blocks.SOULSTONE_BRICKS);
        output.add(Blocks.SOULSTONE_CRACKED_BRICKS);
        output.add(Blocks.SOULSTONE_CHISELED_BRICKS);
        output.add(Blocks.SOULSTONE_SMOOTH);
        output.add(Blocks.SOUL_GLASS);
        output.add(Blocks.SOULSTONE_SLAB);
        output.add(Blocks.SOULSTONE_COBBLE_SLAB);
        output.add(Blocks.SOULSTONE_BRICKS_SLAB);
        output.add(Blocks.SOULSTONE_SMOOTH_SLAB);
        output.add(Blocks.SOULSTONE_STAIRS);
        output.add(Blocks.SOULSTONE_COBBLE_STAIRS);
        output.add(Blocks.SOULSTONE_BRICKS_STAIRS);
        output.add(Blocks.SOULSTONE_COBBLE_WALL);
        output.add(Blocks.SOULSTONE_BRICKS_WALL);
        output.add(Blocks.WITHERPROOF_BLOCK);
        output.add(Blocks.WITHERPROOF_BRICKS);
        output.add(Blocks.WITHERPROOF_GLASS);
        output.add(Blocks.INFUSION_PEDESTAL);
        output.add(Blocks.INFUSION_ALTAR);
        output.add(Blocks.AWAKENING_PEDESTAL);
        output.add(Blocks.AWAKENING_ALTAR);
        output.add(Blocks.ESSENCE_VESSEL);
        output.add(Blocks.TINKERING_TABLE);
        output.add(Blocks.MACHINE_FRAME);
        output.add(Blocks.BASIC_REPROCESSOR);
        output.add(Blocks.INFERIUM_REPROCESSOR);
        output.add(Blocks.PRUDENTIUM_REPROCESSOR);
        output.add(Blocks.TERTIUM_REPROCESSOR);
        output.add(Blocks.IMPERIUM_REPROCESSOR);
        output.add(Blocks.SUPREMIUM_REPROCESSOR);
        output.add(Blocks.AWAKENED_SUPREMIUM_REPROCESSOR);
        output.add(Blocks.SOUL_EXTRACTOR);
        //output.add(Blocks.HARVESTER);

        output.add(Items.PROSPERITY_SHARD);
        output.add(Items.INFERIUM_ESSENCE);
        output.add(Items.PRUDENTIUM_ESSENCE);
        output.add(Items.TERTIUM_ESSENCE);
        output.add(Items.IMPERIUM_ESSENCE);
        output.add(Items.SUPREMIUM_ESSENCE);
        output.add(Items.AWAKENED_SUPREMIUM_ESSENCE);
        output.add(Items.PROSPERITY_INGOT);
        output.add(Items.INFERIUM_INGOT);
        output.add(Items.PRUDENTIUM_INGOT);
        output.add(Items.TERTIUM_INGOT);
        output.add(Items.IMPERIUM_INGOT);
        output.add(Items.SUPREMIUM_INGOT);
        output.add(Items.AWAKENED_SUPREMIUM_INGOT);
        output.add(Items.SOULIUM_INGOT);
        output.add(Items.PROSPERITY_NUGGET);
        output.add(Items.INFERIUM_NUGGET);
        output.add(Items.PRUDENTIUM_NUGGET);
        output.add(Items.TERTIUM_NUGGET);
        output.add(Items.IMPERIUM_NUGGET);
        output.add(Items.SUPREMIUM_NUGGET);
        output.add(Items.AWAKENED_SUPREMIUM_NUGGET);
        output.add(Items.SOULIUM_NUGGET);
        output.add(Items.PROSPERITY_GEMSTONE);
        output.add(Items.INFERIUM_GEMSTONE);
        output.add(Items.PRUDENTIUM_GEMSTONE);
        output.add(Items.TERTIUM_GEMSTONE);
        output.add(Items.IMPERIUM_GEMSTONE);
        output.add(Items.SUPREMIUM_GEMSTONE);
        output.add(Items.AWAKENED_SUPREMIUM_GEMSTONE);
        output.add(Items.SOULIUM_GEMSTONE);
        output.add(Items.PROSPERITY_SEED_BASE);
        output.add(Items.SOULIUM_SEED_BASE);
        output.add(Items.SOUL_DUST);
        output.add(Items.SOULIUM_DUST);
        output.add(Items.COGNIZANT_DUST);
        output.add(Items.SOULIUM_DAGGER);
        output.add(Items.PASSIVE_SOULIUM_DAGGER);
        output.add(Items.HOSTILE_SOULIUM_DAGGER);
        output.add(Items.CREATIVE_SOULIUM_DAGGER);
        output.add(Items.INFUSION_CRYSTAL);
        output.add(Items.MASTER_INFUSION_CRYSTAL);
        output.add(Items.FERTILIZED_ESSENCE);
        output.add(Items.MYSTICAL_FERTILIZER);
        output.add(Items.AIR_AGGLOMERATIO);
        output.add(Items.EARTH_AGGLOMERATIO);
        output.add(Items.WATER_AGGLOMERATIO);
        output.add(Items.NATURE_AGGLOMERATIO);
        output.add(Items.DYE_AGGLOMERATIO);
        output.add(Items.NETHER_AGGLOMERATIO);
        output.add(Items.CORAL_AGGLOMERATIO);
        output.add(Items.HONEY_AGGLOMERATIO);
        output.add(Items.PRISMARINE_AGGLOMERATIO);
        output.add(Items.END_AGGLOMERATIO);

        /*if (ModCrops.MYSTICAL_FLOWER.isEnabled()) {
            output.add(Items.MYSTICAL_FLOWER_AGGLOMERATIO);
        }*/

        output.add(Items.EXPERIENCE_DROPLET);
        //output.add(Items.WAND);
        output.add(Items.BLANK_SKULL);
        output.add(Items.BLANK_RECORD);
        output.add(Items.UNATTUNED_AUGMENT);
        output.add(Items.SOUL_JAR);

        for (var type : MobSoulTypeRegistry.getInstance().getMobSoulTypes()) {
            output.add(MobSoulUtils.getFilledSoulJar(type, Items.SOUL_JAR));
        }

        output.add(Items.EXPERIENCE_CAPSULE);

        stack = new ItemStack(Items.WATERING_CAN);
        NBTHelper.setBoolean(stack, "Water", false);
        output.add(stack);

        //output.add(Items.DIAMOND_SICKLE);
        //output.add(Items.DIAMOND_SCYTHE);
        output.add(Items.UPGRADE_BASE);
        output.add(Items.INFERIUM_UPGRADE);
        output.add(Items.PRUDENTIUM_UPGRADE);
        output.add(Items.TERTIUM_UPGRADE);
        output.add(Items.IMPERIUM_UPGRADE);
        output.add(Items.SUPREMIUM_UPGRADE);
        output.add(Items.AWAKENED_SUPREMIUM_UPGRADE);

        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (crop.isEnabled() && !crop.getName().equals("inferium")) {
                output.add(crop.getEssenceItem());
            }
        }

        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (crop.isEnabled()) {
                output.add(crop.getSeedsItem());
            }
        }

        output.add(Items.INFERIUM_SWORD);
        output.add(Items.INFERIUM_PICKAXE);
        output.add(Items.INFERIUM_SHOVEL);
        output.add(Items.INFERIUM_AXE);
        output.add(Items.INFERIUM_HOE);
//                output.add(Items.IMPERIUM_STAFF);

        stack = new ItemStack(Items.INFERIUM_WATERING_CAN);
        NBTHelper.setBoolean(stack, "Water", false);
        NBTHelper.setBoolean(stack, "Active", false);
        output.add(stack);
//        output.add(Items.INFERIUM_BOW);
//        output.add(Items.INFERIUM_CROSSBOW);
//        output.add(Items.INFERIUM_SHEARS);
//        output.add(Items.INFERIUM_FISHING_ROD);
//        output.add(Items.INFERIUM_SICKLE);
//        output.add(Items.INFERIUM_SCYTHE);
        output.add(Items.PRUDENTIUM_SWORD);
        output.add(Items.PRUDENTIUM_PICKAXE);
        output.add(Items.PRUDENTIUM_SHOVEL);
        output.add(Items.PRUDENTIUM_AXE);
        output.add(Items.PRUDENTIUM_HOE);
//                output.add(Items.PRUDENTIUM_STAFF);

        stack = new ItemStack(Items.PRUDENTIUM_WATERING_CAN);
        NBTHelper.setBoolean(stack, "Water", false);
        NBTHelper.setBoolean(stack, "Active", false);
        output.add(stack);

        /*output.add(Items.PRUDENTIUM_BOW);
        output.add(Items.PRUDENTIUM_CROSSBOW);
        output.add(Items.PRUDENTIUM_SHEARS);
        output.add(Items.PRUDENTIUM_FISHING_ROD);
        output.add(Items.PRUDENTIUM_SICKLE);
        output.add(Items.PRUDENTIUM_SCYTHE);*/
        output.add(Items.TERTIUM_SWORD);
        output.add(Items.TERTIUM_PICKAXE);
        output.add(Items.TERTIUM_SHOVEL);
        output.add(Items.TERTIUM_AXE);
        output.add(Items.TERTIUM_HOE);
//                output.add(Items.TERTIUM_STAFF);

        stack = new ItemStack(Items.TERTIUM_WATERING_CAN);
        NBTHelper.setBoolean(stack, "Water", false);
        NBTHelper.setBoolean(stack, "Active", false);
        output.add(stack);

        /*output.add(Items.TERTIUM_BOW);
        output.add(Items.TERTIUM_CROSSBOW);
        output.add(Items.TERTIUM_SHEARS);
        output.add(Items.TERTIUM_FISHING_ROD);
        output.add(Items.TERTIUM_SICKLE);
        output.add(Items.TERTIUM_SCYTHE);*/
        output.add(Items.IMPERIUM_SWORD);
        output.add(Items.IMPERIUM_PICKAXE);
        output.add(Items.IMPERIUM_SHOVEL);
        output.add(Items.IMPERIUM_AXE);
        output.add(Items.IMPERIUM_HOE);
//                output.add(Items.IMPERIUM_STAFF);

        stack = new ItemStack(Items.IMPERIUM_WATERING_CAN);
        NBTHelper.setBoolean(stack, "Water", false);
        NBTHelper.setBoolean(stack, "Active", false);
        output.add(stack);

        /*output.add(Items.IMPERIUM_BOW);
        output.add(Items.IMPERIUM_CROSSBOW);
        output.add(Items.IMPERIUM_SHEARS);
        output.add(Items.IMPERIUM_FISHING_ROD);
        output.add(Items.IMPERIUM_SICKLE);
        output.add(Items.IMPERIUM_SCYTHE);*/
        output.add(Items.SUPREMIUM_SWORD);
        output.add(Items.SUPREMIUM_PICKAXE);
        output.add(Items.SUPREMIUM_SHOVEL);
        output.add(Items.SUPREMIUM_AXE);
        output.add(Items.SUPREMIUM_HOE);
//                output.add(Items.SUPREMIUM_STAFF);

        stack = new ItemStack(Items.SUPREMIUM_WATERING_CAN);
        NBTHelper.setBoolean(stack, "Water", false);
        NBTHelper.setBoolean(stack, "Active", false);
        output.add(stack);

        /*output.add(Items.SUPREMIUM_BOW);
        output.add(Items.SUPREMIUM_CROSSBOW);
        output.add(Items.SUPREMIUM_SHEARS);
        output.add(Items.SUPREMIUM_FISHING_ROD);
        output.add(Items.SUPREMIUM_SICKLE);
        output.add(Items.SUPREMIUM_SCYTHE);
        output.add(Items.AWAKENED_SUPREMIUM_SWORD);
        output.add(Items.AWAKENED_SUPREMIUM_PICKAXE);
        output.add(Items.AWAKENED_SUPREMIUM_SHOVEL);
        output.add(Items.AWAKENED_SUPREMIUM_AXE);
        output.add(Items.AWAKENED_SUPREMIUM_HOE);*/
//                output.add(Items.AWAKENED_SUPREMIUM_STAFF);

        /*stack = new ItemStack(Items.AWAKENED_SUPREMIUM_WATERING_CAN.get());
        NBTHelper.setBoolean(stack, "Water", false);
        NBTHelper.setBoolean(stack, "Active", false);
        output.add(stack);*/

        /*output.add(Items.AWAKENED_SUPREMIUM_BOW);
        output.add(Items.AWAKENED_SUPREMIUM_CROSSBOW);
        output.add(Items.AWAKENED_SUPREMIUM_SHEARS);
        output.add(Items.AWAKENED_SUPREMIUM_FISHING_ROD);
        output.add(Items.AWAKENED_SUPREMIUM_SICKLE);
        output.add(Items.AWAKENED_SUPREMIUM_SCYTHE);*/

        output.add(Items.INFERIUM_HELMET);
        output.add(Items.INFERIUM_CHESTPLATE);
        output.add(Items.INFERIUM_LEGGINGS);
        output.add(Items.INFERIUM_BOOTS);
        output.add(Items.PRUDENTIUM_HELMET);
        output.add(Items.PRUDENTIUM_CHESTPLATE);
        output.add(Items.PRUDENTIUM_LEGGINGS);
        output.add(Items.PRUDENTIUM_BOOTS);
        output.add(Items.TERTIUM_HELMET);
        output.add(Items.TERTIUM_CHESTPLATE);
        output.add(Items.TERTIUM_LEGGINGS);
        output.add(Items.TERTIUM_BOOTS);
        output.add(Items.IMPERIUM_HELMET);
        output.add(Items.IMPERIUM_CHESTPLATE);
        output.add(Items.IMPERIUM_LEGGINGS);
        output.add(Items.IMPERIUM_BOOTS);
        output.add(Items.SUPREMIUM_HELMET);
        output.add(Items.SUPREMIUM_CHESTPLATE);
        output.add(Items.SUPREMIUM_LEGGINGS);
        output.add(Items.SUPREMIUM_BOOTS);
        /*output.add(Items.AWAKENED_SUPREMIUM_HELMET);
        output.add(Items.AWAKENED_SUPREMIUM_CHESTPLATE);
        output.add(Items.AWAKENED_SUPREMIUM_LEGGINGS);
        output.add(Items.AWAKENED_SUPREMIUM_BOOTS);*/

        for (var augment : AugmentRegistry.getInstance().getAugments()) {
            if (augment.isEnabled()) {
                output.add(augment.getItem());
            }
        }
    });
}
