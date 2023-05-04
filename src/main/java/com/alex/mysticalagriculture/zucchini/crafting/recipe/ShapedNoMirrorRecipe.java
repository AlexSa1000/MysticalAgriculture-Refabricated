package com.alex.mysticalagriculture.zucchini.crafting.recipe;

import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Map;

public class ShapedNoMirrorRecipe extends ShapedRecipe {
    private final ItemStack output;

    public ShapedNoMirrorRecipe(Identifier id, String group, CraftingRecipeCategory category, int width, int height, DefaultedList<Ingredient> inputs, ItemStack output, boolean showNotification) {
        super(id, group, category, width, height, inputs, output, showNotification);
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
            CraftingRecipeCategory category = (CraftingRecipeCategory)CraftingRecipeCategory.CODEC.byId(JsonHelper.getString(jsonObject, "category", (String)null), CraftingRecipeCategory.MISC);
            Map<String, Ingredient> key = ShapedRecipe.readSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] pattern = ShapedRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern"));
            int width = pattern[0].length();
            int height = pattern.length;
            DefaultedList<Ingredient> ingredients = ShapedRecipe.createPatternMatrix(pattern, key, width, height);
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            boolean showNotification = JsonHelper.getBoolean(jsonObject, "show_notification", true);
            return new ShapedNoMirrorRecipe(identifier, group, category, width, height, ingredients, output, showNotification);
        }

        @Override
        public ShapedNoMirrorRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String group = packetByteBuf.readString(32767);
            CraftingRecipeCategory category = (CraftingRecipeCategory)packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
            int width = packetByteBuf.readVarInt();
            int height = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(width * height, Ingredient.EMPTY);

            for (int k = 0; k < ingredients.size(); ++k) {
                ingredients.set(k, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack output = packetByteBuf.readItemStack();
            boolean showNotification = packetByteBuf.readBoolean();
            return new ShapedNoMirrorRecipe(identifier, group, category, width, height, ingredients, output, showNotification);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, ShapedNoMirrorRecipe shapedRecipe) {
            packetByteBuf.writeString(shapedRecipe.getGroup());
            packetByteBuf.writeEnumConstant(shapedRecipe.getCategory());
            packetByteBuf.writeVarInt(shapedRecipe.getWidth());
            packetByteBuf.writeVarInt(shapedRecipe.getHeight());
            Iterator var3 = shapedRecipe.getIngredients().iterator();

            while(var3.hasNext()) {
                Ingredient ingredient = (Ingredient)var3.next();
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(shapedRecipe.output);
            packetByteBuf.writeBoolean(shapedRecipe.showNotification());
        }
    }
}
