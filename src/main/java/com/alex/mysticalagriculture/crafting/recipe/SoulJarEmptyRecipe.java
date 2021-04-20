package com.alex.mysticalagriculture.crafting.recipe;


import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.crafting.ingredient.FilledSoulJarIngredient;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.items.SoulJarItem;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SoulJarEmptyRecipe extends ShapelessRecipe {
    public SoulJarEmptyRecipe(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> inputs) {
        super(id, group, output, inputs);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        boolean hasJar = false;
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (hasJar && !stack.isEmpty())
                return false;

            Item item = stack.getItem();
            if (item instanceof SoulJarItem) {
                double souls = MobSoulUtils.getSouls(stack);
                if (souls > 0) {
                    hasJar = true;
                }
            } else if (!stack.isEmpty()) {
                return false;
            }
        }

        return hasJar;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.CRAFTING_SOUL_JAR_EMPTY;
    }

    public static class Serializer implements RecipeSerializer<SoulJarEmptyRecipe> {
        @Override
        public SoulJarEmptyRecipe read(Identifier recipeId, JsonObject json) {
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(1, new FilledSoulJarIngredient());

            return new SoulJarEmptyRecipe(recipeId, "", new ItemStack(Items.SOUL_JAR), ingredients);
        }

        @Override
        public SoulJarEmptyRecipe read(Identifier recipeId, PacketByteBuf buffer) {
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(1, new FilledSoulJarIngredient());

            return new SoulJarEmptyRecipe(recipeId, "", new ItemStack(Items.SOUL_JAR), ingredients);
        }

        @Override
        public void write(PacketByteBuf buffer, SoulJarEmptyRecipe recipe) { }
    }
}
