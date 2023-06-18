package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.api.crafting.ISoulExtractionRecipe;
import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.cucumber.crafting.SpecialRecipe;
import com.alex.cucumber.helper.StackHelper;
import com.alex.mysticalagriculture.init.ModItems;
import com.alex.mysticalagriculture.init.ModRecipeSerializers;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class SoulExtractionRecipe implements SpecialRecipe, ISoulExtractionRecipe {
    private final ResourceLocation recipeId;
    private final NonNullList<Ingredient> inputs;
    private final MobSoulType type;
    private final double souls;
    private final ItemStack output;

    public SoulExtractionRecipe(ResourceLocation recipeId, Ingredient input, MobSoulType type, double souls) {
        this.recipeId = recipeId;
        this.inputs = NonNullList.of(Ingredient.EMPTY, input);
        this.type = type;
        this.souls = souls;
        this.output = MobSoulUtils.getSoulJar(type, souls, ModItems.SOUL_JAR);
    }

    @Override
    public ItemStack assemble(Container inventory) {
        var stack = inventory.getItem(2);
        var jar = StackHelper.withSize(stack, 1, false);

        MobSoulUtils.addSoulsToJar(jar, this.type, this.souls);

        return jar;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
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
        return ModRecipeSerializers.SOUL_EXTRACTION;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.SOUL_EXTRACTION;
    }

    @Override
    public boolean matches(Container inventory, int startIndex, int endIndex) {
        var input = inventory.getItem(0);

        if (!this.inputs.get(0).test(input))
            return false;

        var output = inventory.getItem(2);

        if (!output.sameItem(this.output))
            return false;

        return MobSoulUtils.canAddTypeToJar(output, this.type) && !MobSoulUtils.isJarFull(output);
    }

    @Override
    public MobSoulType getMobSoulType() {
        return this.type;
    }

    @Override
    public double getSouls() {
        return this.souls;
    }

    public static class Serializer implements RecipeSerializer<SoulExtractionRecipe> {
        @Override
        public SoulExtractionRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var ingredient = json.getAsJsonObject("input");
            var input = Ingredient.fromJson(ingredient);
            var output = GsonHelper.getAsJsonObject(json, "output");
            var type = GsonHelper.getAsString(output, "type");
            float amount = GsonHelper.getAsFloat(output, "souls");

            var mobSoulType = MobSoulTypeRegistry.getInstance().getMobSoulTypeById(new ResourceLocation(type));

            if (mobSoulType == null) {
                throw new JsonParseException("Invalid mob soul type id: " + type);
            }

            return new SoulExtractionRecipe(recipeId, input, mobSoulType, amount);
        }

        @Override
        public SoulExtractionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            var input = Ingredient.fromNetwork(buffer);
            var type = buffer.readResourceLocation();
            double souls = buffer.readDouble();

            var mobSoulType = MobSoulTypeRegistry.getInstance().getMobSoulTypeById(type);

            return new SoulExtractionRecipe(recipeId, input, mobSoulType, souls);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SoulExtractionRecipe recipe) {
            recipe.inputs.get(0).toNetwork(buffer);
            buffer.writeResourceLocation(recipe.type.getId());
            buffer.writeDouble(recipe.souls);
        }
    }
}
