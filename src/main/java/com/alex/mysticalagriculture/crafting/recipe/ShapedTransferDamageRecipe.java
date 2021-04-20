package com.alex.mysticalagriculture.crafting.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
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
        ItemStack damageable = ItemStack.EMPTY;
        for (int i = 0; i < inv.size(); i++) {
            ItemStack slotStack = inv.getStack(i);
            if (slotStack.isDamageable()) {
                damageable = slotStack;
                break;
            }
        }

        if (damageable.isEmpty())
            return super.craft(inv);

        ItemStack result = this.getOutput().copy();
        result.setDamage(damageable.getDamage());

        return result;
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
            return new ShapedTransferDamageRecipe(identifier, s, i, j, nonnulllist, itemstack);
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
            return new ShapedTransferDamageRecipe(identifier, s, i, j, inputs, output);
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
