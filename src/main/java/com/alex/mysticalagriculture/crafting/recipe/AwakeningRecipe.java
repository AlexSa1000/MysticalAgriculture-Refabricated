package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.api.crafting.IAwakeningRecipe;
import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.cucumber.crafting.SpecialRecipe;
import com.alex.mysticalagriculture.init.ModRecipeSerializers;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import com.alex.mysticalagriculture.lib.ModCrops;
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

public class AwakeningRecipe implements SpecialRecipe, IAwakeningRecipe {
    public static final int RECIPE_SIZE = 9;
    private final ResourceLocation recipeId;
    private final NonNullList<Ingredient> inputs;
    private final AwakeningRecipe.EssenceVesselRequirements essences;
    private final ItemStack output;
    private final boolean transferNBT;

    public AwakeningRecipe(ResourceLocation recipeId, NonNullList<Ingredient> inputs, AwakeningRecipe.EssenceVesselRequirements essences, ItemStack output, boolean transferNBT) {
        this.recipeId = recipeId;
        this.inputs = NonNullList.withSize(9, Ingredient.EMPTY);
        this.essences = essences;
        this.output = output;
        this.transferNBT = transferNBT;

        this.inputs.set(0, inputs.get(0));
        this.inputs.set(1, createEssenceIngredient(ModCrops.AIR));
        this.inputs.set(2, inputs.get(1));
        this.inputs.set(3, createEssenceIngredient(ModCrops.EARTH));
        this.inputs.set(4, inputs.get(2));
        this.inputs.set(5, createEssenceIngredient(ModCrops.WATER));
        this.inputs.set(6, inputs.get(3));
        this.inputs.set(7, createEssenceIngredient(ModCrops.FIRE));
        this.inputs.set(8, inputs.get(4));
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
    public EssenceVesselRequirements getEssenceRequirements() {
        return this.essences;
    }

    private static Ingredient createEssenceIngredient(Crop crop) {
        var item = crop.getEssenceItem();
        return item == null ? Ingredient.EMPTY : Ingredient.of(item);
    }

    public static class Serializer implements RecipeSerializer<AwakeningRecipe> {
        @Override
        public AwakeningRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var inputs = NonNullList.withSize(RECIPE_SIZE, Ingredient.EMPTY);
            var input = GsonHelper.getAsJsonObject(json, "input");

            inputs.set(0, Ingredient.fromJson(input));

            var ingredients = GsonHelper.getAsJsonArray(json, "ingredients");

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i + 1, Ingredient.fromJson(ingredients.get(i)));
            }

            var essences = GsonHelper.getAsJsonObject(json, "essences");

            var essenceRequirements = new AwakeningRecipe.EssenceVesselRequirements(
                    GsonHelper.getAsInt(essences, "air"),
                    GsonHelper.getAsInt(essences, "earth"),
                    GsonHelper.getAsInt(essences, "water"),
                    GsonHelper.getAsInt(essences, "fire")
            );

            var result = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result"));
            var transferNBT = GsonHelper.getAsBoolean(json, "transfer_nbt", false);

            return new AwakeningRecipe(recipeId, inputs, essenceRequirements, result, transferNBT);
        }

        @Override
        public AwakeningRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            var inputs = NonNullList.withSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            var essences = new AwakeningRecipe.EssenceVesselRequirements(
                    buffer.readVarInt(),
                    buffer.readVarInt(),
                    buffer.readVarInt(),
                    buffer.readVarInt()
            );

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

            buffer.writeVarInt(recipe.essences.air());
            buffer.writeVarInt(recipe.essences.earth());
            buffer.writeVarInt(recipe.essences.water());
            buffer.writeVarInt(recipe.essences.fire());

            buffer.writeItem(recipe.output);
            buffer.writeBoolean(recipe.transferNBT);
        }
    }
}