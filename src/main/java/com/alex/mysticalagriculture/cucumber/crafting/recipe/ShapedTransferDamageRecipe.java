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

import java.util.Map;

public class ShapedTransferDamageRecipe extends ShapedRecipe {
    public ShapedTransferDamageRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> inputs, ItemStack output) {
        super(id, group, width, height, inputs, output);
    }

    @Override
    public ItemStack craft(CraftingInventory inv) {
        var damageable = ItemStack.EMPTY;

        for (int i = 0; i < inv.size(); i++) {
            var slotStack = inv.getStack(i);

            if (slotStack.isDamageable()) {
                damageable = slotStack;
                break;
            }
        }

        if (damageable.isEmpty())
            return super.craft(inv);

        var result = this.getOutput().copy();

        result.setDamage(damageable.getDamage());

        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return (RecipeSerializer)RecipeSerializers.CRAFTING_SHAPED_TRANSFER_DAMAGE;
    }

    public static class Serializer implements RecipeSerializer<ShapedTransferDamageRecipe> {
        public Serializer() {
        }

        @Override
        public ShapedTransferDamageRecipe read(Identifier identifier, JsonObject jsonObject) {
            String group = JsonHelper.getString(jsonObject, "group", "");
            Map<String, Ingredient> key = ShapedRecipe.readSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] pattern = ShapedRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern"));
            int width = pattern[0].length();
            int height = pattern.length;
            DefaultedList<Ingredient> ingredients = ShapedRecipe.createPatternMatrix(pattern, key, width, height);
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));

            return new ShapedTransferDamageRecipe(identifier, group, width, height, ingredients, output);
        }

        @Override
        public ShapedTransferDamageRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String group = packetByteBuf.readString(32767);
            int width = packetByteBuf.readVarInt();
            int height = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(width * height, Ingredient.EMPTY);

            for (int k = 0; k < ingredients.size(); ++k) {
                ingredients.set(k, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack output = packetByteBuf.readItemStack();

            return new ShapedTransferDamageRecipe(identifier, group, width, height, ingredients, output);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, ShapedTransferDamageRecipe shapedRecipe) {
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
