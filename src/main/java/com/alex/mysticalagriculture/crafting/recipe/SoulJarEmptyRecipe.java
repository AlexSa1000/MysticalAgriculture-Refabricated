package com.alex.mysticalagriculture.crafting.recipe;


import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.crafting.ingredient.FilledSoulJarIngredient;
import com.alex.mysticalagriculture.init.ModItems;
import com.alex.mysticalagriculture.init.ModRecipeSerializers;
import com.alex.mysticalagriculture.items.SoulJarItem;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

public class SoulJarEmptyRecipe extends ShapelessRecipe {
    public SoulJarEmptyRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> inputs) {
        super(id, group, output, inputs);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {
        boolean hasJar = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            var stack = inv.getItem(i);

            if (hasJar && !stack.isEmpty())
                return false;

            var item = stack.getItem();

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
        return ModRecipeSerializers.CRAFTING_SOUL_JAR_EMPTY;
    }

    public static class Serializer implements RecipeSerializer<SoulJarEmptyRecipe> {
        @Override
        public SoulJarEmptyRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(1, new FilledSoulJarIngredient());

            return new SoulJarEmptyRecipe(recipeId, "", new ItemStack(ModItems.SOUL_JAR), ingredients);
        }

        @Override
        public SoulJarEmptyRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(1, new FilledSoulJarIngredient());

            return new SoulJarEmptyRecipe(recipeId, "", new ItemStack(ModItems.SOUL_JAR), ingredients);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SoulJarEmptyRecipe recipe) { }
    }
}
