package com.alex.mysticalagriculture.crafting.ingredient;

import com.alex.mysticalagriculture.init.ModRecipeSerializers;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class StrictNBTIngredient implements CustomIngredient {
    private final Ingredient base;
    private final ItemStack stack;

    public StrictNBTIngredient(ItemStack stack)
    {
        this.base = new Ingredient(Stream.of(new Ingredient.ItemValue(stack)));
        this.stack = stack;
    }

    public static StrictNBTIngredient of(ItemStack stack)
    {
        return new StrictNBTIngredient(stack);
    }

    @Override
    public boolean test(ItemStack input) {
        if (input == null)
            return false;
        return this.stack.getItem() == input.getItem() && this.stack.getDamageValue() == input.getDamageValue() && this.stack.equals(input);
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return Arrays.asList(base.getItems());
    }

    @Override
    public boolean requiresTesting() {
        return base.requiresTesting();
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return ModRecipeSerializers.CROP_COMPONENT_INGREDIENT;
    }

    public Ingredient getBase() {
        return base;
    }

    public static class Serializer implements CustomIngredientSerializer<StrictNBTIngredient> {


        @Override
        public ResourceLocation getIdentifier() {
            return new ResourceLocation(MOD_ID, "strict_nbt");
        }

        @Override
        public StrictNBTIngredient read(JsonObject json) {
            return null;
        }

        /*@Override
        public StrictNBTIngredient read(JsonObject json) {
            return new StrictNBTIngredient(CraftingHelper.getItemStack(json, true));
        }*/

        @Override
        public void write(JsonObject json, StrictNBTIngredient ingredient) {
            json.addProperty("item", BuiltInRegistries.ITEM.getKey(ingredient.stack.getItem()).toString());
            json.addProperty("count", ingredient.stack.getCount());
            if (ingredient.stack.hasTag())
                json.addProperty("nbt", ingredient.stack.getTag().toString());
        }

        @Override
        public StrictNBTIngredient read(FriendlyByteBuf buf) {
            return new StrictNBTIngredient(buf.readItem());
        }

        @Override
        public void write(FriendlyByteBuf buf, StrictNBTIngredient ingredient) {
            buf.writeItem(ingredient.stack);
        }
    }
}