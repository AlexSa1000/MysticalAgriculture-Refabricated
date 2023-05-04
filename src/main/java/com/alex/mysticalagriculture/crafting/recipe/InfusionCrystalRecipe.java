package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.zucchini.util.FabricRecipeRemainder;
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

import java.util.Map;

public class InfusionCrystalRecipe extends ShapedRecipe {
    private final ItemStack output;

    public InfusionCrystalRecipe(Identifier id, String group, CraftingRecipeCategory category, int width, int height, DefaultedList<Ingredient> inputs, ItemStack output, boolean showNotification) {
        super(id, group, category, width, height, inputs, output, showNotification);
        this.output = output;
    }

    /*@Override
    public ItemStack craft(CraftingInventory craftingInventory, DynamicRegistryManager dynamicRegistryManager) {
        return this.output;
    }*/

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for(int i = 0; i < inventory.size(); ++i) {
            ItemStack invStack = inventory.getStack(i);
            if (invStack.getItem() instanceof FabricRecipeRemainder) {
                ItemStack remainder = ((FabricRecipeRemainder) invStack.getItem()).getRemainder(invStack.copy(), inventory, null);
                defaultedList.set(i, remainder);
            }
        }
        return defaultedList;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.INFUSION_CRYSTAL;
    }

    /*public static class InfusionCrystalRecipeSerializer extends Serializer {

        @Override
        public InfusionCrystalRecipe read(Identifier identifier, JsonObject jsonObject) {
            ShapedRecipe shaped = super.read(identifier, jsonObject);
            //String string = JsonHelper.getString(jsonObject, "group", "");
            return new InfusionCrystalRecipe(shaped.getId(), shaped.group, shaped.getCategory(), shaped.getWidth(), shaped.getHeight(), shaped.getIngredients(), shaped.output, shaped.showNotification());
        }

        @Override
        public InfusionCrystalRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            ShapedRecipe shaped = super.read(identifier, packetByteBuf);
            return new InfusionCrystalRecipe(shaped.getId(), shaped.group, shaped.getCategory(), shaped.getWidth(), shaped.getHeight(), shaped.getIngredients(), shaped.output, shaped.showNotification());
        }
    }*/
    public static class Serializer implements RecipeSerializer<InfusionCrystalRecipe> {
        @Override
        public InfusionCrystalRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            CraftingRecipeCategory craftingRecipeCategory = CraftingRecipeCategory.CODEC.byId(JsonHelper.getString(jsonObject, "category", null), CraftingRecipeCategory.MISC);
            Map<String, Ingredient> map = ShapedRecipe.readSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] strings = ShapedRecipe.removePadding(ShapedRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern")));
            int i = strings[0].length();
            int j = strings.length;
            DefaultedList<Ingredient> defaultedList = ShapedRecipe.createPatternMatrix(strings, map, i, j);
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            boolean bl = JsonHelper.getBoolean(jsonObject, "show_notification", true);
            return new InfusionCrystalRecipe(identifier, string, craftingRecipeCategory, i, j, defaultedList, itemStack, bl);
        }

        @Override
        public InfusionCrystalRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            int i = packetByteBuf.readVarInt();
            int j = packetByteBuf.readVarInt();
            String string = packetByteBuf.readString();
            CraftingRecipeCategory craftingRecipeCategory = packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY);
            for (int k = 0; k < defaultedList.size(); ++k) {
                defaultedList.set(k, Ingredient.fromPacket(packetByteBuf));
            }
            ItemStack itemStack = packetByteBuf.readItemStack();
            boolean bl = packetByteBuf.readBoolean();
            return new InfusionCrystalRecipe(identifier, string, craftingRecipeCategory, i, j, defaultedList, itemStack, bl);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, InfusionCrystalRecipe shapedRecipe) {
            packetByteBuf.writeVarInt(shapedRecipe.getWidth());
            packetByteBuf.writeVarInt(shapedRecipe.getHeight());
            packetByteBuf.writeString(shapedRecipe.getGroup());
            packetByteBuf.writeEnumConstant(shapedRecipe.getCategory());
            for (Ingredient ingredient : shapedRecipe.getIngredients()) {
                ingredient.write(packetByteBuf);
            }
            packetByteBuf.writeItemStack(shapedRecipe.output);
            packetByteBuf.writeBoolean(shapedRecipe.showNotification());
        }
    }

}