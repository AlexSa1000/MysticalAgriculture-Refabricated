package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.util.FabricRecipeRemainder;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class InfusionCrystalRecipe extends ShapedRecipe {
    public InfusionCrystalRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> ingredients, ItemStack output) {
        super(id, group, width, height, ingredients, output);
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        return getOutput().copy();
    }

    @Override
    public DefaultedList<ItemStack> getRemainingStacks(CraftingInventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for(int i = 0; i < inventory.size(); ++i) {
            ItemStack invStack = inventory.getStack(i);
            if (invStack.getItem() instanceof FabricRecipeRemainder) {
                ItemStack remainder = ((FabricRecipeRemainder) invStack.getItem()).getRemainder(invStack.copy(), inventory, null);
                defaultedList.set(i, remainder);
            }
        }
        return defaultedList;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.INFUSION_CRYSTAL;
    }

    public static class InfusionCrystalRecipeSerializer extends Serializer {

        @Override
        public InfusionCrystalRecipe read(Identifier identifier, JsonObject jsonObject) {
            ShapedRecipe shaped = super.read(identifier, jsonObject);
            String string = JsonHelper.getString(jsonObject, "group", "");
            return new InfusionCrystalRecipe(shaped.getId(), string, shaped.getWidth(), shaped.getHeight(), shaped.getPreviewInputs(), shaped.getOutput());
        }

        @Override
        public InfusionCrystalRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            ShapedRecipe shaped = super.read(identifier, packetByteBuf);
            return new InfusionCrystalRecipe(shaped.getId(), shaped.group, shaped.getWidth(), shaped.getHeight(), shaped.getPreviewInputs(), shaped.getOutput());
        }


    }
}