package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.cucumber.crafting.SpecialRecipe;
import com.alex.mysticalagriculture.api.crafting.IInfusionRecipe;
import com.alex.mysticalagriculture.init.ModRecipeSerializers;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class InfusionRecipe implements SpecialRecipe, IInfusionRecipe {
    public static final int RECIPE_SIZE = 9;
    private final ResourceLocation recipeId;
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final boolean transferNBT;

    public InfusionRecipe(ResourceLocation recipeId, NonNullList<Ingredient> inputs, ItemStack output, boolean transferNBT) {
        this.recipeId = recipeId;
        this.inputs = inputs;
        this.output = output;
        this.transferNBT = transferNBT;
    }

    @Override
    public ItemStack assemble(Container inventory, RegistryAccess access) {
        var stack = inventory.getItem(0);
        var result = this.output.copy();

        if (this.transferNBT) {
            var tag = stack.getTag();

            if (tag != null) {
                result.setTag(tag.copy());

                return result;
            }
        }

        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<InfusionRecipe> getSerializer() {
        return ModRecipeSerializers.INFUSION;
    }

    @Override
    public RecipeType<? extends IInfusionRecipe> getType() {
        return ModRecipeTypes.INFUSION;
    }

    @Override
    public boolean matches(Container inventory) {
        var altarStack = inventory.getItem(0);
        return !this.inputs.isEmpty() && this.inputs.get(0).test(altarStack) && SpecialRecipe.super.matches(inventory);
    }

    public static class Serializer implements RecipeSerializer<InfusionRecipe> {
        @Override
        public InfusionRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var inputs = NonNullList.withSize(RECIPE_SIZE, Ingredient.EMPTY);
            var input = GsonHelper.getAsJsonObject(json, "input");

            inputs.set(0, Ingredient.fromJson(input));

            var ingredients = GsonHelper.getAsJsonArray(json, "ingredients");

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i + 1, Ingredient.fromJson(ingredients.get(i)));
            }

            var result = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result"));
            var transferNBT = GsonHelper.getAsBoolean(json, "transfer_nbt", false);

            return new InfusionRecipe(recipeId, inputs, result, transferNBT);
        }

        @Override
        public InfusionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            var inputs = NonNullList.withSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            var output = buffer.readItem();
            var transferNBT = buffer.readBoolean();

            return new InfusionRecipe(recipeId, inputs, output, transferNBT);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, InfusionRecipe recipe) {
            buffer.writeVarInt(recipe.inputs.size());

            for (var ingredient : recipe.inputs) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.output);
            buffer.writeBoolean(recipe.transferNBT);
        }
    }
}