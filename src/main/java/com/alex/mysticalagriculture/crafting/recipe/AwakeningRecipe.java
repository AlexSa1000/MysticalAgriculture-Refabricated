package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.cucumber.crafting.SpecialRecipe;
import com.alex.mysticalagriculture.api.crafting.IAwakeningRecipe;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AwakeningRecipe implements SpecialRecipe, IAwakeningRecipe {
    public static final int RECIPE_SIZE = 9;

    private final ResourceLocation recipeId;
    private final NonNullList<Ingredient> inputs;
    private final NonNullList<ItemStack> essences;
    private final ItemStack output;
    private final boolean transferNBT;

    public AwakeningRecipe(ResourceLocation recipeId, NonNullList<Ingredient> inputs, NonNullList<ItemStack> essences, ItemStack output, boolean transferNBT) {
        this.recipeId = recipeId;
        this.essences = essences;
        this.output = output;
        this.transferNBT = transferNBT;

        var allInputs = NonNullList.withSize(9, Ingredient.EMPTY);

        allInputs.set(0, inputs.get(0));
        allInputs.set(1, Ingredient.of(essences.get(0)));
        allInputs.set(2, inputs.get(1));
        allInputs.set(3, Ingredient.of(essences.get(1)));
        allInputs.set(4, inputs.get(2));
        allInputs.set(5, Ingredient.of(essences.get(2)));
        allInputs.set(6, inputs.get(3));
        allInputs.set(7, Ingredient.of(essences.get(3)));
        allInputs.set(8, inputs.get(4));

        this.inputs = allInputs;
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
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.AWAKENING;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.AWAKENING;
    }

    @Override
    public NonNullList<ItemStack> getEssences() {
        return this.essences;
    }

    @Override
    public Map<ItemStack, Integer> getMissingEssences(List<ItemStack> items) {
        var remaining = new ArrayList<>(this.essences);
        var missing = new LinkedHashMap<ItemStack, Integer>();

        for (var item : items) {
            for (var essence : remaining) {
                if (ItemStack.isSameItemSameTags(item, essence)) {
                    var current = item.getCount();
                    var required = essence.getCount();

                    if (current < required) {
                        missing.put(essence, required - current);
                    }

                    remaining.remove(essence);

                    break;
                }
            }
        }

        for (var essence : remaining) {
            missing.put(essence, essence.getCount());
        }

        return missing;
    }

    public static class Serializer implements RecipeSerializer<AwakeningRecipe> {
        @Override
        public AwakeningRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var inputs = NonNullList.withSize(5, Ingredient.EMPTY);
            var input = GsonHelper.getAsJsonObject(json, "input");

            inputs.set(0, Ingredient.fromJson(input));

            var ingredients = GsonHelper.getAsJsonArray(json, "ingredients");

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i + 1, Ingredient.fromJson(ingredients.get(i)));
            }

            var essences = NonNullList.withSize(4, ItemStack.EMPTY);
            var essenceArray = GsonHelper.getAsJsonArray(json, "essences");

            for (int i = 0; i < essenceArray.size(); i++) {
                essences.set(i, ShapedRecipe.itemStackFromJson(essenceArray.get(i).getAsJsonObject()));
            }

            var result = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result"));
            var transferNBT = GsonHelper.getAsBoolean(json, "transfer_nbt", false);

            return new AwakeningRecipe(recipeId, inputs, essences, result, transferNBT);
        }

        @Override
        public AwakeningRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            var inputs = NonNullList.withSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            size = buffer.readVarInt();
            var essences = NonNullList.withSize(size, ItemStack.EMPTY);

            for (int i = 0; i < size; i++) {
                essences.set(i, buffer.readItem());
            }

            var output = buffer.readItem();
            var transferNBT = buffer.readBoolean();

            return new AwakeningRecipe(recipeId, inputs, essences, output, transferNBT);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AwakeningRecipe recipe) {
            buffer.writeVarInt(5);

            // only send the non-vessel ingredients
            for (int i = 0; i <= 8; i += 2) {
                recipe.inputs.get(i).toNetwork(buffer);
            }

            buffer.writeVarInt(4);

            for (int i = 0; i < 4; i++) {
                buffer.writeItem(recipe.essences.get(i));
            }

            buffer.writeItem(recipe.output);
            buffer.writeBoolean(recipe.transferNBT);
        }
    }
}