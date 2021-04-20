package com.alex.mysticalagriculture.crafting.recipe;

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
    public ShapedNoMirrorRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> inputs, ItemStack output) {
        super(id, group, width, height, inputs, output);
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
                    ingredient = this.getPreviewInputs().get(k + l * this.getWidth());
                }

                if (!ingredient.test(inventory.getStack(i + j * inventory.getWidth()))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static class Serializer extends ShapedRecipe.Serializer {
        @Override
        public ShapedRecipe read(Identifier identifier, JsonObject jsonObject) {
            String s = JsonHelper.getString(jsonObject, "group", "");
            Map<String, Ingredient> map = ShapedRecipe.getComponents(JsonHelper.getObject(jsonObject, "key"));
            String[] astring = ShapedRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern"));
            int i = astring[0].length();
            int j = astring.length;
            DefaultedList<Ingredient> nonnulllist = ShapedRecipe.getIngredients(astring, map, i, j);
            ItemStack itemstack = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
            return new ShapedNoMirrorRecipe(identifier, s, i, j, nonnulllist, itemstack);
        }

        @Override
        public ShapedRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            int i = packetByteBuf.readVarInt();
            int j = packetByteBuf.readVarInt();
            String s = packetByteBuf.readString(32767);
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(i * j, Ingredient.EMPTY);

            for (int k = 0; k < inputs.size(); ++k) {
                inputs.set(k, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack output = packetByteBuf.readItemStack();
            return new ShapedNoMirrorRecipe(identifier, s, i, j, inputs, output);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, ShapedRecipe shapedRecipe) {
            packetByteBuf.writeVarInt(shapedRecipe.getWidth());
            packetByteBuf.writeVarInt(shapedRecipe.getHeight());
            packetByteBuf.writeString(shapedRecipe.group);

            for (Ingredient ingredient : shapedRecipe.getPreviewInputs()) {
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(shapedRecipe.getOutput());
        }
    }
}
