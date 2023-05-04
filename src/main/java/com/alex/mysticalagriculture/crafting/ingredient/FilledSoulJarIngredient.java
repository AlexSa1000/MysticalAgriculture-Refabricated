package com.alex.mysticalagriculture.crafting.ingredient;

import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.items.SoulJarItem;
import com.alex.mysticalagriculture.lib.ModMobSoulTypes;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeMatcher;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Stream;

public class FilledSoulJarIngredient extends Ingredient {
    private ItemStack[] stacks;
    private IntList stacksPacked;

    public FilledSoulJarIngredient() {
        super(Stream.empty());
        initMatchingStacks();
    }

    @Override
    public ItemStack[] getMatchingStacks() {
        return this.stacks;
    }

    @Override
    public IntList getMatchingItemIds() {
        if (this.stacksPacked == null) {
            this.stacksPacked = new IntArrayList(this.stacks.length);
            Arrays.stream(this.stacks).forEach(s -> this.stacksPacked.add(RecipeMatcher.getItemId(s)));
            this.stacksPacked.sort(IntComparators.NATURAL_COMPARATOR);
        }

        return this.stacksPacked;
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        if (stack != null) {
            return stack.getItem() instanceof SoulJarItem && MobSoulUtils.getSouls(stack) > 0;
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return (this.stacks == null || this.stacks.length == 0) && (this.stacksPacked == null || this.stacksPacked.isEmpty());
    }

    @Override
    public JsonElement toJson() {
        JsonArray json = new JsonArray();
        JsonObject obj = new JsonObject();

        obj.addProperty("item", "mysticalagriculture:soul_jar");
        json.add(obj);

        return json;
    }

    private void initMatchingStacks() {
        this.stacks = MobSoulTypeRegistry.getInstance().getMobSoulTypes().stream()
                .map(type -> MobSoulUtils.getFilledSoulJar(type, Items.SOUL_JAR))
                .toArray(ItemStack[]::new);
    }
}
