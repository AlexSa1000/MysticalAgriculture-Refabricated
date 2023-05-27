package com.alex.mysticalagriculture.cucumber.crafting.recipe;

import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

import java.util.Iterator;
import java.util.Map;

public class ShapedTransferDamageRecipe extends ShapedRecipe {
    private final ItemStack result;

    public ShapedTransferDamageRecipe(Identifier id, String group, CraftingRecipeCategory category, int width, int height, DefaultedList<Ingredient> inputs, ItemStack result, boolean showNotification) {
        super(id, group, category, width, height, inputs, result, showNotification);
        this.result = result;
    }

    @Override
    public ItemStack craft(CraftingInventory inv, DynamicRegistryManager dynamicRegistryManager) {
        ItemStack damageable = ItemStack.EMPTY;

        for (int i = 0; i < inv.size(); i++) {
            ItemStack slotStack = inv.getStack(i);
            if (slotStack.isDamageable()) {
                damageable = slotStack;
                break;
            }
        }

        if (damageable.isEmpty()) {
            return super.craft(inv, dynamicRegistryManager);
        } else {
            ItemStack result = this.getOutput(dynamicRegistryManager).copy();
            result.setDamage(damageable.getDamage());
            return result;
        }
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
            CraftingRecipeCategory category = (CraftingRecipeCategory)CraftingRecipeCategory.CODEC.byId(JsonHelper.getString(jsonObject, "category", (String)null), CraftingRecipeCategory.MISC);
            Map<String, Ingredient> key = ShapedRecipe.readSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] pattern = ShapedRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern"));
            int width = pattern[0].length();
            int height = pattern.length;
            DefaultedList<Ingredient> ingredients = ShapedRecipe.createPatternMatrix(pattern, key, width, height);
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            boolean showNotification = JsonHelper.getBoolean(jsonObject, "show_notification", true);
            return new ShapedTransferDamageRecipe(identifier, group, category, width, height, ingredients, output, showNotification);
        }

        @Override
        public ShapedTransferDamageRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
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
            return new ShapedTransferDamageRecipe(identifier, group, category, width, height, ingredients, output, showNotification);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, ShapedTransferDamageRecipe shapedRecipe) {
            packetByteBuf.writeString(shapedRecipe.getGroup());
            packetByteBuf.writeEnumConstant(shapedRecipe.getCategory());
            packetByteBuf.writeVarInt(shapedRecipe.getWidth());
            packetByteBuf.writeVarInt(shapedRecipe.getHeight());
            Iterator var3 = shapedRecipe.getIngredients().iterator();

            while(var3.hasNext()) {
                Ingredient ingredient = (Ingredient)var3.next();
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(shapedRecipe.result);
            packetByteBuf.writeBoolean(shapedRecipe.showNotification());
        }
    }
}
