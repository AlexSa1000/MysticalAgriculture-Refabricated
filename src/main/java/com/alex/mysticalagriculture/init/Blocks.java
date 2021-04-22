package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.blocks.*;
import com.alex.mysticalagriculture.util.block.*;
import com.alex.mysticalagriculture.util.iface.Colored;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.alex.mysticalagriculture.MysticalAgriculture.ITEM_GROUP;
import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class Blocks {
    public static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    public static final Map<BlockItem, Identifier> BLOCK_ITEMS = new LinkedHashMap<>();

    public static final Block PROSPERITY_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 4.0F, 6.0F, FabricToolTags.PICKAXES), "prosperity_block");
    public static final Block INFERIUM_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 4.0F, 6.0F, FabricToolTags.PICKAXES), "inferium_block");
    public static final Block PRUDENTIUM_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 4.0F, 6.0F, FabricToolTags.PICKAXES), "prudentium_block");
    public static final Block TERTIUM_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 4.0F, 6.0F, FabricToolTags.PICKAXES), "tertium_block");
    public static final Block IMPERIUM_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 4.0F, 5.0F, FabricToolTags.PICKAXES), "imperium_block");
    public static final Block SUPREMIUM_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 4.0F, 6.0F, FabricToolTags.PICKAXES), "supremium_block");
    public static final Block SOULIUM_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 4.0F, 6.0F, FabricToolTags.PICKAXES), "soulium_block");
    public static final Block PROSPERITY_INGOT_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "prosperity_ingot_block");
    public static final Block INFERIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "inferium_ingot_block");
    public static final Block PRUDENTIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "prudentium_ingot_block");
    public static final Block TERTIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "tertium_ingot_block");
    public static final Block IMPERIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "imperium_ingot_block");
    public static final Block SUPREMIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "supremium_ingot_block");
    public static final Block SOULIUM_INGOT_BLOCK = register(new BaseBlock(Material.STONE, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "soulium_ingot_block");
    public static final Block PROSPERITY_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "prosperity_gemstone_block");
    public static final Block INFERIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "inferium_gemstone_block");
    public static final Block PRUDENTIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "prudentium_gemstone_block");
    public static final Block TERTIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "tertium_gemstone_block");
    public static final Block IMPERIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "imperium_gemstone_block");
    public static final Block SUPREMIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "supremium_gemstone_block");
    public static final Block SOULIUM_GEMSTONE_BLOCK = register(new BaseBlock(Material.METAL, BlockSoundGroup.METAL, 5.0F, 6.0F, FabricToolTags.PICKAXES), "soulium_gemstone_block");
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
    public static final Block INFERIUM_FURNACE = register(new EssenceFurnaceBlock(EssenceFurnaceBlock.FurnaceTier.INFERIUM), "inferium_furnace");
    public static final Block PRUDENTIUM_FURNACE = register(new EssenceFurnaceBlock(EssenceFurnaceBlock.FurnaceTier.PRUDENTIUM), "prudentium_furnace");
    public static final Block TERTIUM_FURNACE = register(new EssenceFurnaceBlock(EssenceFurnaceBlock.FurnaceTier.TERTIUM), "tertium_furnace");
    public static final Block IMPERIUM_FURNACE = register(new EssenceFurnaceBlock(EssenceFurnaceBlock.FurnaceTier.IMPERIUM), "imperium_furnace");
    public static final Block SUPREMIUM_FURNACE = register(new EssenceFurnaceBlock(EssenceFurnaceBlock.FurnaceTier.SUPREMIUM), "supremium_furnace");
    public static final Block PROSPERITY_ORE = register(new BaseOreBlock(Material.STONE, BlockSoundGroup.STONE, 3.0F, 3.0F, 0, 2, 5), "prosperity_ore");
    public static final Block INFERIUM_ORE = register(new BaseOreBlock(Material.STONE, BlockSoundGroup.STONE, 3.0F, 3.0F, 0, 2, 5), "inferium_ore");
    public static final Block SOULIUM_ORE = register(new BaseOreBlock(Material.STONE, BlockSoundGroup.STONE, 3.0F, 3.0F, 0, 3, 7), "soulium_ore");
    public static final Block SOULSTONE = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone");
    public static final Block SOULSTONE_COBBLE = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 2.0F, 6.0F, FabricToolTags.PICKAXES), "soulstone_cobble");
    public static final Block SOULSTONE_BRICKS = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone_bricks");
    public static final Block SOULSTONE_CRACKED_BRICKS = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone_cracked_bricks");
    public static final Block SOULSTONE_CHISELED_BRICKS = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone_chiseled_bricks");
    public static final Block SOULSTONE_SMOOTH = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone_smooth");
    public static final Block SOUL_GLASS = register(new BaseGlassBlock(Material.GLASS, BlockSoundGroup.GLASS, 0.3F, 0.3F), "soul_glass");
    public static final Block SOULSTONE_SLAB = register(new BaseSlabBlock(Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone_slab");
    public static final Block SOULSTONE_COBBLE_SLAB = register(new BaseSlabBlock(Material.STONE, BlockSoundGroup.STONE, 2.0F, 6.0F, FabricToolTags.PICKAXES), "soulstone_cobble_slab");
    public static final Block SOULSTONE_BRICKS_SLAB = register(new BaseSlabBlock(Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone_bricks_slab");
    public static final Block SOULSTONE_SMOOTH_SLAB = register(new BaseSlabBlock(Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone_smooth_slab");
    public static final Block SOULSTONE_STAIRS = register(new BaseStairsBlock(SOULSTONE.getDefaultState(), Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone_stairs");
    public static final Block SOULSTONE_COBBLE_STAIRS = register(new BaseStairsBlock(SOULSTONE_COBBLE.getDefaultState(), Material.STONE, BlockSoundGroup.STONE, 2.0F, 6.0F, FabricToolTags.PICKAXES), "soulstone_cobble_stairs");
    public static final Block SOULSTONE_BRICKS_STAIRS = register(new BaseStairsBlock(SOULSTONE_BRICKS.getDefaultState(), Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone_bricks_stairs");
    public static final Block SOULSTONE_COBBLE_WALL = register(new BaseWallBlock(Material.STONE, BlockSoundGroup.STONE, 2.0F, 6.0F, FabricToolTags.PICKAXES), "soulstone_cobble_wall");
    public static final Block SOULSTONE_BRICKS_WALL = register(new BaseWallBlock(Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F, FabricToolTags.PICKAXES), "soulstone_bricks_wall");
    public static final Block WITHERPROOF_BLOCK = register(new WitherproofBlock(), "witherproof_block");
    public static final Block WITHERPROOF_BRICKS = register(new WitherproofBlock(), "witherproof_bricks");
    public static final Block WITHERPROOF_GLASS = register(new WitherproofGlassBlock(), "witherproof_glass");
    public static final Block INFUSION_PEDESTAL = register(new InfusionPedestalBlock(), "infusion_pedestal");
    public static final Block INFUSION_ALTAR = register(new InfusionAltarBlock(), "infusion_altar");
    public static final Block TINKERING_TABLE = register(new TinkeringTableBlock(), "tinkering_table");
    public static final Block MACHINE_FRAME = register(new BaseBlock(Material.STONE, BlockSoundGroup.STONE, 1.5F, 6.0F), "machine_frame");
    public static final Block BASIC_REPROCESSOR = register(new ReprocessorBlock(ReprocessorBlock.ReprocessorTier.BASIC), "basic_reprocessor");
    public static final Block INFERIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorBlock.ReprocessorTier.INFERIUM), "inferium_reprocessor");
    public static final Block PRUDENTIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorBlock.ReprocessorTier.PRUDENTIUM), "prudentium_reprocessor");
    public static final Block TERTIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorBlock.ReprocessorTier.TERTIUM), "tertium_reprocessor");
    public static final Block IMPERIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorBlock.ReprocessorTier.IMPERIUM), "imperium_reprocessor");
    public static final Block SUPREMIUM_REPROCESSOR = register(new ReprocessorBlock(ReprocessorBlock.ReprocessorTier.SUPREMIUM), "supremium_reprocessor");

    private static Block register(Block block, String name) {
        Identifier id = new Identifier(MOD_ID, name);
        BLOCKS.put(block, id);
        BLOCK_ITEMS.put(new BlockItem(block, new Item.Settings().group(ITEM_GROUP)), id);
        return block;
    }

    public static void registerBlocks() {
        BLOCKS.forEach((block, id) -> {
            Registry.register(Registry.BLOCK, id, block);
        });

        BLOCK_ITEMS.forEach((block_item, id) -> {
            Registry.register(Registry.ITEM, id, block_item);
        });
    }

    public static void onColors() {
        ColorProviderRegistry.BLOCK.register(new Colored.BlockColors(), InfusedFarmlandBlock.FARMLANDS.toArray(new InfusedFarmlandBlock[0]));
        ColorProviderRegistry.ITEM.register(new Colored.ItemBlockColors(), InfusedFarmlandBlock.FARMLANDS.toArray(new InfusedFarmlandBlock[0]));
    }
}
