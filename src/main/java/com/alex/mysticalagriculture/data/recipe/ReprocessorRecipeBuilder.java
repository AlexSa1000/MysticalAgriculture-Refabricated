package com.alex.mysticalagriculture.data.recipe;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.init.RecipeSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ReprocessorRecipeBuilder {
    private final Ingredient input;
    private final Item result;
    private final int count;
    private final JsonArray conditions = new JsonArray();

    public ReprocessorRecipeBuilder(Ingredient input, ItemLike output, int count) {
        this.input = input;
        this.result = output.asItem();
        this.count = count;
    }

    public void addCondition(JsonObject condition) {
        this.conditions.add(condition);
    }

    public static ReprocessorRecipeBuilder newSeedReprocessingRecipe(Crop crop) {
        var input = Ingredient.of(crop.getSeedsItem());
        var output = crop.getEssenceItem();

        var builder = new ReprocessorRecipeBuilder(input, output, 2);

        var condition = new JsonObject();
        condition.addProperty("condition", "mysticalagriculture:crop_enabled");
        condition.addProperty("crop", crop.getId().toString());

        builder.addCondition(condition);

        return builder;
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new ReprocessorRecipeBuilder.Result(id, this.result, this.count, this.input, this.conditions));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final Ingredient input;
        private final JsonArray conditions;

        public Result(ResourceLocation id, Item result, int count, Ingredient input, JsonArray conditions) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.input = input;
            this.conditions = conditions;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("fabric:load_conditions", this.conditions);
            json.add("input", this.input.toJson());

            var result = new JsonObject();

            result.addProperty("item", Registry.ITEM.getKey(this.result).toString());

            if (this.count > 1) {
                result.addProperty("count", this.count);
            }

            json.add("result", result);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializers.REPROCESSOR;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
