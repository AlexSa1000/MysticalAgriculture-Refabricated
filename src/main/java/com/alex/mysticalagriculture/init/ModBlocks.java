package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.blocks.*;
import com.alex.cucumber.block.*;
import com.alex.cucumber.item.BaseBlockItem;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.alex.mysticalagriculture.util.ReprocessorTier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.alex.mysticalagriculture.MysticalAgriculture.CREATIVE_TAB;
import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ModBlocks {
    public static final Map<ResourceLocation, Block> BLOCKS = new LinkedHashMap<>();
    public static final Map<ResourceLocation, BlockItem> BLOCK_ITEMS = new LinkedHashMap<>();

    public static final Block PROSPERITY_BLOCK = register(new BaseBlock(Material.STONE, SoundType.STONE, 4.0F, 6.0F, true), "prosperity_block");
    public static final Block INFERIUM_BLOCK = register(new BaseBlock(Material.STONE, SoundType.STONE, 4.0F, 6.0F, true), "inferium_block");
    public static final Block PRUDENTIUM_BLOCK = register(new BaseBlock(Material.STONE, SoundType.STONE, 4.0F, 6.0F, true), "prudentium_block");
    public static final Block TERTIUM_BLOCK = register(new BaseBlock(Material.STONE, SoundType.STONE, 4.0F, 6.0F, true), "tertium_block");
    public static final Block IMPERIUM_BLOCK = register(new BaseBlock(Material.STONE, SoundType.STONE, 4.0F, 5.0F, true), "imperium_block");
    public static final Block SUPREMIUM_BLOCK = register(new BaseBlock(Material.STONE, SoundType.STONE, 4.0F, 6.0F, true), "supremium_block");
    public static final Block AWAKENED_SUPREMIUM_BLOCK = register(new BaseBlock(Material.STONE, SoundType.STONE, 4.0F, 6.0F, true), "awakened_supremium_block");
    public static final Block SOULIUM_BLOCK = register(new BaseBlock(Material.STONE, SoundType.STONE, 4.0F, 6.0F, true), "soulium_block");
    public static final Block PROSPERITY_INGOT_BLOCK = register(new BaseBlock(Material.STONE, SoundType.METAL, 5.0F, 6.0F, true), "prosperity_ingot_block");
    public static final Block INFERIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, SoundType.METAL, 5.0F, 6.0F, true), "inferium_ingot_block");
    public static final Block PRUDENTIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, SoundType.METAL, 5.0F, 6.0F, true), "prudentium_ingot_block");
    public static final Block TERTIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, SoundType.METAL, 5.0F, 6.0F, true), "tertium_ingot_block");
    public static final Block IMPERIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, SoundType.METAL, 5.0F, 6.0F, true), "imperium_ingot_block");
    public static final Block SUPREMIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, SoundType.METAL, 5.0F, 6.0F, true), "supremium_ingot_block");
    public static final Block AWAKENED_SUPREMIUM_INGOT_BLOCK = register(new BaseBlock(Material.METAL, SoundType.METAL, 5.0F, 6.0F, true), "awakened_supremium_ingot_block");
    public static final Block SOULIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, SoundType.METAL, 5.0F, 6.0F, true), "soulium_ingot_block");
    public static final Block PROSPERITY_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, SoundType.METAL, 5.0F, 6.0F, true), "prosperity_gemstone_block");
    public static final Block INFERIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, SoundType.METAL, 5.0F, 6.0F, true), "inferium_gemstone_block");
    public static final Block PRUDENTIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, SoundType.METAL, 5.0F, 6.0F, true), "prudentium_gemstone_block");
    public static final Block TERTIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, SoundType.METAL, 5.0F, 6.0F, true), "tertium_gemstone_block");
    public static final Block IMPERIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, SoundType.METAL, 5.0F, 6.0F, true), "imperium_gemstone_block");
    public static final Block SUPREMIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, SoundType.METAL, 5.0F, 6.0F, true), "supremium_gemstone_block");
    public static final Block AWAKENED_SUPREMIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, SoundType.METAL, 5.0F, 6.0F, true), "awakened_supremium_gemstone_block");
    public static final Block SOULIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, SoundType.METAL, 5.0F, 6.0F, true), "soulium_gemstone_block");
    public static final Block INFERIUM_FARMLAND = register(new InfusedFarmlandBlock(CropTier.ONE), "inferium_farmland");
    public static final Block PRUDENTIUM_FARMLAND  = register(new InfusedFarmlandBlock(CropTier.TWO), "prudentium_farmland");
    public static final Block TERTIUM_FARMLAND  = register(new InfusedFarmlandBlock(CropTier.THREE), "tertium_farmland");
    public static final Block IMPERIUM_FARMLAND  = register(new InfusedFarmlandBlock(CropTier.FOUR), "imperium_farmland");
    public static final Block SUPREMIUM_FARMLAND  = register(new InfusedFarmlandBlock(CropTier.FIVE), "supremium_farmland");
    public static final Block INFERIUM_GROWTH_ACCELERATOR = register(new GrowthAcceleratorBlock(12, CropTier.ONE.getTextColor()), "inferium_growth_accelerator");
    public static final Block PRUDENTIUM_GROWTH_ACCELERATOR = register(new GrowthAcceleratorBlock(24, CropTier.TWO.getTextColor()), "prudentium_growth_accelerator");
    public static final Block TERTIUM_GROWTH_ACCELERATOR = register(new GrowthAcceleratorBlock(36, CropTier.THREE.getTextColor()), "tertium_growth_accelerator");
    public static final Block IMPERIUM_GROWTH_ACCELERATOR = register(new GrowthAcceleratorBlock(48, CropTier.FOUR.getTextColor()), "imperium_growth_accelerator");
    public static final Block SUPREMIUM_GROWTH_ACCELERATOR = register(new GrowthAcceleratorBlock(60, CropTier.FIVE.getTextColor()), "supremium_growth_accelerator");
    public static final Block INFERIUM_FURNACE = register(new EssenceFurnaceBlock.Inferium(), "inferium_furnace");
    public static final Block PRUDENTIUM_FURNACE = register(new EssenceFurnaceBlock.Prudentium(), "prudentium_furnace");
    public static final Block TERTIUM_FURNACE = register(new EssenceFurnaceBlock.Tertium(), "tertium_furnace");
    public static final Block IMPERIUM_FURNACE = register(new EssenceFurnaceBlock.Imperium(), "imperium_furnace");
    public static final Block SUPREMIUM_FURNACE = register(new EssenceFurnaceBlock.Supremium(), "supremium_furnace");
    public static final Block AWAKENED_SUPREMIUM_FURNACE = register(new EssenceFurnaceBlock.AwakenedSupremium(), "awakened_supremium_furnace");
    public static final Block PROSPERITY_ORE = register(new BaseOreBlock(Material.STONE, SoundType.STONE, 3.0F, 3.0F, 2, 5), "prosperity_ore");
    public static final Block DEEPSLATE_PROSPERITY_ORE = register(new BaseOreBlock(Material.STONE, SoundType.DEEPSLATE, 4.5F, 3.0F, 2, 5), "deepslate_prosperity_ore");
    public static final Block INFERIUM_ORE = register(new BaseOreBlock(Material.STONE, SoundType.STONE, 3.0F, 3.0F, 2, 5), "inferium_ore");
    public static final Block DEEPSLATE_INFERIUM_ORE = register(new BaseOreBlock(Material.STONE, SoundType.DEEPSLATE, 4.5F, 3.0F, 2, 5), "deepslate_inferium_ore");
    public static final Block SOULIUM_ORE = register(new BaseOreBlock(Material.STONE, SoundType.STONE, 3.0F, 3.0F, 3, 7), "soulium_ore");
    public static final Block SOULSTONE = register(new BaseBlock(Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone");
    public static final Block SOULSTONE_COBBLE = register(new BaseBlock(Material.STONE, SoundType.STONE, 2.0F, 6.0F, true), "soulstone_cobble");
    public static final Block SOULSTONE_BRICKS = register(new BaseBlock(Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone_bricks");
    public static final Block SOULSTONE_CRACKED_BRICKS = register(new BaseBlock(Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone_cracked_bricks");
    public static final Block SOULSTONE_CHISELED_BRICKS = register(new BaseBlock(Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone_chiseled_bricks");
    public static final Block SOULSTONE_SMOOTH = register(new BaseBlock(Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone_smooth");
    public static final Block SOUL_GLASS = register(new BaseGlassBlock(Material.GLASS, SoundType.GLASS, 0.3F, 0.3F), "soul_glass");
    public static final Block SOULSTONE_SLAB = register(new BaseSlabBlock(Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone_slab");
    public static final Block SOULSTONE_COBBLE_SLAB = register(new BaseSlabBlock(Material.STONE, SoundType.STONE, 2.0F, 6.0F, true), "soulstone_cobble_slab");
    public static final Block SOULSTONE_BRICKS_SLAB = register(new BaseSlabBlock(Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone_bricks_slab");
    public static final Block SOULSTONE_SMOOTH_SLAB = register(new BaseSlabBlock(Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone_smooth_slab");
    public static final Block SOULSTONE_STAIRS = register(new BaseStairsBlock(SOULSTONE::defaultBlockState, Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone_stairs");
    public static final Block SOULSTONE_COBBLE_STAIRS = register(new BaseStairsBlock(SOULSTONE_COBBLE::defaultBlockState, Material.STONE, SoundType.STONE, 2.0F, 6.0F, true), "soulstone_cobble_stairs");
    public static final Block SOULSTONE_BRICKS_STAIRS = register(new BaseStairsBlock(SOULSTONE_BRICKS::defaultBlockState, Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone_bricks_stairs");
    public static final Block SOULSTONE_COBBLE_WALL = register(new BaseWallBlock(Material.STONE, SoundType.STONE, 2.0F, 6.0F, true), "soulstone_cobble_wall");
    public static final Block SOULSTONE_BRICKS_WALL = register(new BaseWallBlock(Material.STONE, SoundType.STONE, 1.5F, 6.0F, true), "soulstone_bricks_wall");
    public static final Block WITHERPROOF_BLOCK = register(new WitherproofBlock(), "witherproof_block");
    public static final Block WITHERPROOF_BRICKS = register(new WitherproofBlock(), "witherproof_bricks");
    public static final Block WITHERPROOF_GLASS = register(new WitherproofGlassBlock(), "witherproof_glass");
    public static final Block INFUSION_PEDESTAL = register(new InfusionPedestalBlock(), "infusion_pedestal");
    public static final Block INFUSION_ALTAR = register(new InfusionAltarBlock(), "infusion_altar");
    public static final Block AWAKENING_PEDESTAL = register(new AwakeningPedestalBlock(), "awakening_pedestal");
    public static final Block AWAKENING_ALTAR = register(new AwakeningAltarBlock(), "awakening_altar");
    public static final Block ESSENCE_VESSEL = register(new EssenceVesselBlock(), "essence_vessel");
    public static final Block TINKERING_TABLE = register(new TinkeringTableBlock(), "tinkering_table");
    public static final Block MACHINE_FRAME = register(new BaseBlock(Material.STONE, SoundType.STONE, 1.5F, 6.0F), "machine_frame");
    public static final Block BASIC_REPROCESSOR = register(new ReprocessorBlock(ReprocessorTier.BASIC), "basic_reprocessor");
    public static final Block INFERIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorTier.INFERIUM), "inferium_reprocessor");
    public static final Block PRUDENTIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorTier.PRUDENTIUM), "prudentium_reprocessor");
    public static final Block TERTIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorTier.TERTIUM), "tertium_reprocessor");
    public static final Block IMPERIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorTier.IMPERIUM), "imperium_reprocessor");
    public static final Block SUPREMIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorTier.SUPREMIUM), "supremium_reprocessor");
    public static final Block AWAKENED_SUPREMIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorTier.AWAKENED_SUPREMIUM), "awakened_supremium_reprocessor");
    public static final Block SOUL_EXTRACTOR = register(new SoulExtractorBlock(), "soul_extractor");
    public static final Block HARVESTER = register(new HarvesterBlock(), "harvester");

    public static final Block INFERIUM_CROP = registerNoItem(new InferiumCropBlock(ModCrops.INFERIUM),"inferium_crop");

    public static void registerModBlocks() {
        BLOCKS.forEach((id, block) -> {
            Registry.register(Registry.BLOCK, id, block);
        });

        BLOCK_ITEMS.forEach((id, block_item) -> {
            Registry.register(Registry.ITEM, id, block_item);
        });

        CropRegistry.getInstance().setAllowRegistration(true);
        CropRegistry.getInstance().onRegisterBlocks();
        CropRegistry.getInstance().setAllowRegistration(false);
    }

    private static Block register(Block block, String name) {
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        BLOCKS.put(id, block);
        BLOCK_ITEMS.put(id, new BaseBlockItem(block, p -> p.tab(CREATIVE_TAB)));
        return block;
    }

    private static Block registerNoItem(Block block, String name) {
        var id = new ResourceLocation(MOD_ID, name);
        BLOCKS.put(id, block);
        return block;
    }
}
