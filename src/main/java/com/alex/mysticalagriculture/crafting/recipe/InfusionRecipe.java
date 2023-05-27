package com.alex.mysticalagriculture.crafting.recipe;

import com.alex.mysticalagriculture.cucumber.crafting.SpecialRecipe;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.alex.mysticalagriculture.init.RecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class InfusionRecipe implements SpecialRecipe {
    public static final int RECIPE_SIZE = 9;
    private final Identifier recipeId;
    private final DefaultedList<Ingredient> inputs;
    private final ItemStack output;

    public InfusionRecipe(Identifier recipeId, DefaultedList<Ingredient> inputs, ItemStack output) {
        this.recipeId = recipeId;
        this.inputs = inputs;
        this.output = output;
    }


    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int var1, int var2) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public Identifier getId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<InfusionRecipe> getSerializer() {
        return RecipeSerializers.INFUSION;
    }

    @Override
    public RecipeType<? extends InfusionRecipe> getType() {
        return RecipeTypes.INFUSION;
    }

    @Override
    public boolean matches(Inventory inventory) {
        var altarStack = inventory.getStack(0);
        return !this.inputs.isEmpty() && this.inputs.get(0).test(altarStack) && SpecialRecipe.super.matches(inventory);
    }

    /*public static Ingredient getIngredient(Crop crop, ComponentType type) {
        Ingredient ingredient;
        switch (type) {
            case ESSENCE:
                ingredient = Ingredient.ofItems(crop.getTier().getEssence());
                break;
            case SEED:
                ingredient = Ingredient.ofItems(crop.getType().getCraftingSeed());
                break;
            case MATERIAL:
                ingredient = crop.getCraftingMaterial();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return ingredient;
    }*/

    /*public static class InfusionRecipeSerializer implements RecipeSerializer<InfusionRecipe> {
        @Override
        public InfusionRecipe read(Identifier id, JsonObject json) {
            //var inputs = DefaultedList.ofSize(RECIPE_SIZE, Ingredient.EMPTY);
            DefaultedList<Ingredient> inputs = DefaultedList.of();
            var input = JsonHelper.getObject(json, "input");

            if (!input.has("item") && !input.has("tag")) {
                String cropId = JsonHelper.getString(input, "crop");
                String typeName = JsonHelper.getString(input, "component");

                Crop crop = ModCrops.getCropById(cropId);
                ComponentType type = ComponentType.fromName(typeName);

                inputs.add(getIngredient(crop, type));
            } else {
                inputs.add(Ingredient.fromJson(input));
            }

            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            for (JsonElement i : ingredients) {
                if (i.isJsonObject()) {
                    JsonObject object = i.getAsJsonObject();
                    if (!object.has("item") && !object.has("tag")) {
                        String cropId = JsonHelper.getString(object, "crop");
                        String typeName = JsonHelper.getString(object, "component");

                        Crop crop = ModCrops.getCropById(cropId);
                        ComponentType type = ComponentType.fromName(typeName);

                        inputs.add(getIngredient(crop, type));
                    } else {
                        inputs.add(Ingredient.fromJson(i));
                    }
                }
            }

            ItemStack output = ShapedRecipe.outputFromJson(json.getAsJsonObject("result"));

            return new InfusionRecipe(id, inputs, output);
        }

        @Override
        public InfusionRecipe read(Identifier id, PacketByteBuf buf) {
            int size = buf.readVarInt();
            var inputs = DefaultedList.ofSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();

            return new InfusionRecipe(id, inputs, output);
        }

        @Override
        public void write(PacketByteBuf buf, InfusionRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());

            for (Ingredient ingredient : recipe.inputs) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.output);
        }
    }

    public enum ComponentType {
        ESSENCE("essence"),
        SEED("seed"),
        MATERIAL("material");

        public final String name;

        ComponentType(String name) {
            this.name = name;
        }

        public static ComponentType fromName(String name) {
            return Arrays.stream(values()).filter(t -> t.name.equals(name)).findFirst().orElse(null);
        }
    }*/

    public static class Serializer implements RecipeSerializer<InfusionRecipe> {
        @Override
        public InfusionRecipe read(Identifier recipeId, JsonObject json) {
            var inputs = DefaultedList.ofSize(RECIPE_SIZE, Ingredient.EMPTY);
            var input = JsonHelper.getObject(json, "input");

            inputs.set(0, Ingredient.fromJson(input));

            var ingredients = JsonHelper.getArray(json, "ingredients");

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i + 1, Ingredient.fromJson(ingredients.get(i)));

            }

            var result = ShapedRecipe.outputFromJson(json.getAsJsonObject("result"));

            return new InfusionRecipe(recipeId, inputs, result);
        }

        @Override
        public InfusionRecipe read(Identifier recipeId, PacketByteBuf buffer) {
            int size = buffer.readVarInt();
            var inputs = DefaultedList.ofSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromPacket(buffer));
            }

            ItemStack output = buffer.readItemStack();

            return new InfusionRecipe(recipeId, inputs, output);
        }

        @Override
        public void write(PacketByteBuf buffer, InfusionRecipe recipe) {
            buffer.writeVarInt(recipe.inputs.size());

            for (var ingredient : recipe.inputs) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.output);
        }
    }
}
