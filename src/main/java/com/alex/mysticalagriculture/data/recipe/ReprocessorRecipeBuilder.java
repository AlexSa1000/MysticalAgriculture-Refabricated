package com.alex.mysticalagriculture.data.recipe;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.init.ModRecipeSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ReprocessorRecipeBuilder {
    private final Ingredient input;
    private final Item result;
    private final int count;
    private final JsonArray conditions = new JsonArray();

    public ReprocessorRecipeBuilder(Ingredient input, ItemConvertible output, int count) {
        this.input = input;
        this.result = output.asItem();
        this.count = count;
    }

    public void addCondition(JsonObject condition) {
        this.conditions.add(condition);
    }

    public static ReprocessorRecipeBuilder newSeedReprocessingRecipe(Crop crop) {
        var input = Ingredient.ofItems(crop.getSeedsItem());
        var output = crop.getEssenceItem();

        var builder = new ReprocessorRecipeBuilder(input, output, 2);

        var condition = new JsonObject();
        condition.addProperty("condition", "mysticalagriculture:crop_enabled");
        condition.addProperty("crop", crop.getId().toString());

        builder.addCondition(condition);

        return builder;
    }

    public void build(Consumer<RecipeJsonProvider> consumer, Identifier id) {
        consumer.accept(new ReprocessorRecipeBuilder.Result(id, this.result, this.count, this.input, this.conditions));
    }

    public static class Result implements RecipeJsonProvider {
        private final Identifier id;
        private final Item result;
        private final int count;
        private final Ingredient input;
        private final JsonArray conditions;

        public Result(Identifier id, Item result, int count, Ingredient input, JsonArray conditions) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.input = input;
            this.conditions = conditions;
        }

        @Override
        public void serialize(JsonObject json) {
            json.add("fabric:load_conditions", this.conditions);
            json.add("input", this.input.toJson());

            var result = new JsonObject();

            result.addProperty("item", Registries.ITEM.getId(this.result).toString());

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
            return ModRecipeSerializers.REPROCESSOR;
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
