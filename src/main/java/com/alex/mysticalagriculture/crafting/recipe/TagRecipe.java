package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.util.crafting.TagMapper;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

import java.util.Map;

public class TagRecipe extends ShapedNoMirrorRecipe {
    public TagRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> inputs, ItemStack output) {
        super(id, group, width, height, inputs, output);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.CRAFTING_TAG;
    }

    public static class Serializer implements RecipeSerializer<TagRecipe> {
        @Override
        public TagRecipe read(Identifier recipeId, JsonObject json) {
            String group = JsonHelper.getString(json, "group", "");
            Map<String, Ingredient> map = ShapedRecipe.getComponents(JsonHelper.getObject(json, "key"));
            String[] pattern = ShapedRecipe.combinePattern(ShapedRecipe.getPattern(JsonHelper.getArray(json, "pattern")));
            int width = pattern[0].length();
            int height = pattern.length;
            DefaultedList<Ingredient> ingredients = ShapedRecipe.getIngredients(pattern, map, width, height);

            JsonObject result = JsonHelper.getObject(json, "result");
            String tag = JsonHelper.getString(result, "tag");
            int count = JsonHelper.getInt(result, "count", 1);
            Item item = TagMapper.getItemForTag(tag);
            if (item == Items.AIR)
                return null;

            ItemStack output = new ItemStack(item, count);

            return new TagRecipe(recipeId, group, width, height, ingredients, output);
        }

        @Override
        public TagRecipe read(Identifier recipeId, PacketByteBuf buffer) {
            int width = buffer.readVarInt();
            int height = buffer.readVarInt();
            String group = buffer.readString(32767);
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(width * height, Ingredient.EMPTY);

            for (int k = 0; k < ingredients.size(); k++) {
                ingredients.set(k, Ingredient.fromPacket(buffer));
            }

            ItemStack output = buffer.readItemStack();

            return new TagRecipe(recipeId, group, width, height, ingredients, output);
        }

        @Override
        public void write(PacketByteBuf buffer, TagRecipe recipe) {
            buffer.writeVarInt(recipe.getWidth());
            buffer.writeVarInt(recipe.getHeight());
            buffer.writeString(recipe.group);

            for (Ingredient ingredient : recipe.getPreviewInputs()) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.getOutput());
        }
    }
}
