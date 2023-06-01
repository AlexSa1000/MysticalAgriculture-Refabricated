package com.alex.mysticalagriculture.crafting.ingredient;

import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class StrictNBTIngredient implements CustomIngredient {
    private final Ingredient base;
    private final ItemStack stack;

    public StrictNBTIngredient(ItemStack stack)
    {
        this.base = new Ingredient(Stream.of(new Ingredient.StackEntry(stack)));
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
        return this.stack.getItem() == input.getItem() && this.stack.getDamage() == input.getDamage() && this.stack.isEqual(input);    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return Arrays.asList(base.getMatchingStacks());
    }

    @Override
    public boolean requiresTesting() {
        return base.requiresTesting();
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return RecipeSerializers.CROP_COMPONENT_INGREDIENT;
    }

    public Ingredient getBase() {
        return base;
    }

    public static class Serializer implements CustomIngredientSerializer<StrictNBTIngredient> {


        @Override
        public Identifier getIdentifier() {
            return new Identifier(MOD_ID, "strict_nbt");
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
            json.addProperty("item", Registry.ITEM.getId(ingredient.stack.getItem()).toString());
            json.addProperty("count", ingredient.stack.getCount());
            if (ingredient.stack.hasNbt())
                json.addProperty("nbt", ingredient.stack.getNbt().toString());
        }

        @Override
        public StrictNBTIngredient read(PacketByteBuf buf) {
            return new StrictNBTIngredient(buf.readItemStack());
        }

        @Override
        public void write(PacketByteBuf buf, StrictNBTIngredient ingredient) {
            buf.writeItemStack(ingredient.stack);
        }
    }
}
