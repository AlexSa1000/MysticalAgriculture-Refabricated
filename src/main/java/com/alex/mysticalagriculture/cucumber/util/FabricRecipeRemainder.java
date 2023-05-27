package com.alex.mysticalagriculture.cucumber.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;

public interface FabricRecipeRemainder {
    ItemStack getRemainder(ItemStack stack, CraftingInventory craftingInventory, PlayerEntity playerEntity);
}
