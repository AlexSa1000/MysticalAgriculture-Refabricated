package com.alex.mysticalagriculture.data.recipe;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.crafting.ingredient.CropComponentIngredient;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class InfusionRecipeBuilder {
    private final Item result;
    private final int count;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final JsonArray conditions = new JsonArray();
    private Ingredient input = Ingredient.EMPTY;

    public InfusionRecipeBuilder(ItemConvertible output, int count) {
        this.result = output.asItem();
        this.count = count;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addIngredient(ItemConvertible ingredient) {
        this.ingredients.add(Ingredient.ofItems(ingredient));
    }

    public void addCondition(JsonObject condition) {
        this.conditions.add(condition);
    }

    public static InfusionRecipeBuilder newSeedRecipe(Crop crop) {
        var builder = new InfusionRecipeBuilder(crop.getSeedsItem(), 1);

        var essence = (new CropComponentIngredient(crop, CropComponentIngredient.ComponentType.ESSENCE)).toVanilla();
        var seed = (new CropComponentIngredient(crop, CropComponentIngredient.ComponentType.SEED)).toVanilla();
        var material = (new CropComponentIngredient(crop, CropComponentIngredient.ComponentType.MATERIAL)).toVanilla();

        builder.input = seed;

        builder.addIngredient(material);
        builder.addIngredient(essence);
        builder.addIngredient(material);
        builder.addIngredient(essence);
        builder.addIngredient(material);
        builder.addIngredient(essence);
        builder.addIngredient(material);
        builder.addIngredient(essence);

        JsonObject condition;

        condition = new JsonObject();
        condition.addProperty("condition", "mysticalagriculture:crop_enabled");
        condition.addProperty("crop", crop.getId().toString());

        builder.addCondition(condition);

        condition = new JsonObject();
        condition.addProperty("condition", "mysticalagriculture:crop_has_material");
        condition.addProperty("crop", crop.getId().toString());

        builder.addCondition(condition);

        return builder;
    }

    public void build(Consumer<RecipeJsonProvider> consumer, Identifier id) {
        consumer.accept(new InfusionRecipeBuilder.Result(id, this.result, this.count, this.input, this.ingredients, this.conditions));
    }

    public static class Result implements RecipeJsonProvider {
        private final Identifier id;
        private final Item result;
        private final int count;
        private final Ingredient input;
        private final List<Ingredient> ingredients;
        private final JsonArray conditions;

        public Result(Identifier id, Item result, int count, Ingredient input, List<Ingredient> ingredients, JsonArray conditions) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.input = input;
            this.ingredients = ingredients;
            this.conditions = conditions;
        }

        @Override
        public void serialize(JsonObject json) {
            json.add("fabric:load_conditions", this.conditions);

            json.add("input", this.input.toJson());

            var ingredients = new JsonArray();
            for (var ingredient : this.ingredients) {
                ingredients.add(ingredient.toJson());
            }

            json.add("ingredients", ingredients);

            var result = new JsonObject();

            result.addProperty("item", Registry.ITEM.getId(this.result).toString());

            if (this.count > 1) {
                result.addProperty("count", this.count);
            }

            json.add("result", result);
        }

        @Override
        public Identifier getRecipeId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return RecipeSerializers.INFUSION;
        }

        @Nullable
        @Override
        public JsonObject toAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public Identifier getAdvancementId() {
            return null;
        }
    }
}
