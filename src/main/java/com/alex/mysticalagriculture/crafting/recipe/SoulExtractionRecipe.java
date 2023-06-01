package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.cucumber.crafting.SpecialRecipe;
import com.alex.mysticalagriculture.cucumber.helper.StackHelper;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class SoulExtractionRecipe implements SpecialRecipe, com.alex.mysticalagriculture.api.crafting.SoulExtractionRecipe {
    private final Identifier recipeId;
    private final DefaultedList<Ingredient> inputs;
    private final MobSoulType type;
    private final double souls;
    private final ItemStack output;

    public SoulExtractionRecipe(Identifier recipeId, Ingredient input, MobSoulType type, double souls) {
        this.recipeId = recipeId;
        this.inputs = DefaultedList.copyOf(Ingredient.EMPTY, input);
        this.type = type;
        this.souls = souls;
        this.output = MobSoulUtils.getSoulJar(type, souls, Items.SOUL_JAR);
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        var stack = inventory.getStack(2);
        var jar = StackHelper.withSize(stack, 1, false);

        MobSoulUtils.addSoulsToJar(jar, this.type, this.souls);

        return jar;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public Identifier getId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.SOUL_EXTRACTION;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.SOUL_EXTRACTION;
    }

    @Override
    public boolean matches(Inventory inventory, int startIndex, int endIndex) {
        var input = inventory.getStack(0);

        if (!this.inputs.get(0).test(input))
            return false;

        var output = inventory.getStack(2);

        if (!output.isItemEqual(this.output))
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
        public SoulExtractionRecipe read(Identifier id, JsonObject json) {
            var ingredient = json.getAsJsonObject("input");
            var input = Ingredient.fromJson(ingredient);
            var output = JsonHelper.getObject(json, "output");
            var type = JsonHelper.getString(output, "type");
            float amount = JsonHelper.getFloat(output, "souls");

            var mobSoulType = MobSoulTypeRegistry.getInstance().getMobSoulTypeById(new Identifier(type));

            if (mobSoulType == null) {
                throw new JsonParseException("Invalid mob soul type id: " + type);
            }

            return new SoulExtractionRecipe(id, input, mobSoulType, amount);
        }

        @Override
        public SoulExtractionRecipe read(Identifier id, PacketByteBuf buffer) {
            var input = Ingredient.fromPacket(buffer);
            var type = buffer.readIdentifier();
            double souls = buffer.readDouble();

            var mobSoulType = MobSoulTypeRegistry.getInstance().getMobSoulTypeById(type);

            return new SoulExtractionRecipe(id, input, mobSoulType, souls);
        }

        @Override
        public void write(PacketByteBuf buffer, SoulExtractionRecipe recipe) {
            recipe.inputs.get(0).write(buffer);
            buffer.writeIdentifier(recipe.type.getId());
            buffer.writeDouble(recipe.souls);
        }
    }
}
