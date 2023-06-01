package com.alex.mysticalagriculture.cucumber.crafting.recipe;

import com.alex.mysticalagriculture.init.RecipeSerializers;
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
import net.minecraft.world.World;

import java.util.Map;

public class ShapedNoMirrorRecipe extends ShapedRecipe {
    private final ItemStack output;

    public ShapedNoMirrorRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> inputs, ItemStack output) {
        super(id, group, width, height, inputs, output);
        this.output = output;
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        for (int i = 0; i <= inventory.getWidth() - this.getWidth(); i++) {
            for (int j = 0; j <= inventory.getHeight() - this.getHeight(); j++) {
                if (this.checkMatch(inventory, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.CRAFTING_SHAPED_NO_MIRROR;
    }

    private boolean checkMatch(CraftingInventory inventory, int x, int y) {
        for (int i = 0; i < inventory.getWidth(); ++i) {
            for (int j = 0; j < inventory.getHeight(); ++j) {
                int k = i - x;
                int l = j - y;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.getWidth() && l < this.getHeight()) {
                    ingredient = this.getIngredients().get(k + l * this.getWidth());
                }

                if (!ingredient.test(inventory.getStack(i + j * inventory.getWidth()))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static class Serializer implements RecipeSerializer<ShapedNoMirrorRecipe> {
        public Serializer() {
        }

        @Override
        public ShapedNoMirrorRecipe read(Identifier identifier, JsonObject jsonObject) {
            String group = JsonHelper.getString(jsonObject, "group", "");
            Map<String, Ingredient> key = ShapedRecipe.readSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] pattern = ShapedRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern"));
            int width = pattern[0].length();
            int height = pattern.length;
            DefaultedList<Ingredient> ingredients = ShapedRecipe.createPatternMatrix(pattern, key, width, height);
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));

            return new ShapedNoMirrorRecipe(identifier, group, width, height, ingredients, output);
        }

        @Override
        public ShapedNoMirrorRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String group = packetByteBuf.readString(32767);
            int width = packetByteBuf.readVarInt();
            int height = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(width * height, Ingredient.EMPTY);

            for (int k = 0; k < ingredients.size(); ++k) {
                ingredients.set(k, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack output = packetByteBuf.readItemStack();

            return new ShapedNoMirrorRecipe(identifier, group, width, height, ingredients, output);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, ShapedNoMirrorRecipe shapedRecipe) {
            packetByteBuf.writeString(shapedRecipe.getGroup());
            packetByteBuf.writeVarInt(shapedRecipe.getWidth());
            packetByteBuf.writeVarInt(shapedRecipe.getHeight());

            for (var ingredient : shapedRecipe.getIngredients()) {
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(shapedRecipe.getOutput());
        }
    }
}
