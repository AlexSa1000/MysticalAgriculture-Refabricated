package com.alex.mysticalagriculture.compat.columns;

import com.alex.mysticalagriculture.init.Blocks;
import io.github.haykam821.columns.block.ColumnBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.alex.mysticalagriculture.MysticalAgriculture.ITEM_GROUP;
import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public final class BlockusColumnBlocks {
    private BlockusColumnBlocks() {
        return;
    }

    private static void registerColumnBlockAndItem(String path, Block base) {
        Identifier id = new Identifier(MOD_ID, path);

        Block block = new ColumnBlock(FabricBlockSettings.copy(base));
        Registry.register(Registry.BLOCK, id, block);

        Item item = new BlockItem(block, new Item.Settings().group(ITEM_GROUP));
        Registry.register(Registry.ITEM, id, item);
    }

    public static void init() {
        registerColumnBlockAndItem("soulstone_cobble_column", Blocks.SOULSTONE_COBBLE);
        registerColumnBlockAndItem("soulstone_bricks_column", Blocks.SOULSTONE_BRICKS);
    }
}
