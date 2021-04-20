package com.alex.mysticalagriculture.compat.rei;

import com.alex.mysticalagriculture.crafting.recipe.InfusionRecipe;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InfusionRecipeDisplay implements RecipeDisplay {

    private final List<List<EntryStack>> input;
    private final List<List<EntryStack>> output;

    public InfusionRecipeDisplay(InfusionRecipe recipe) {
        input = EntryStack.ofIngredients(recipe.getPreviewInputs());
        output = new ArrayList<>();
        output.add(Collections.singletonList(EntryStack.create(recipe.getOutput())));
    }

    @Override
    public @NotNull List<List<EntryStack>> getInputEntries() {
        return input;
    }

    @Override
    public @NotNull List<List<EntryStack>> getResultingEntries() {
        return output;
    }

    @Override
    public @NotNull List<List<EntryStack>> getRequiredEntries() {
        return input;
    }

    @Override
    public @NotNull Identifier getRecipeCategory() {
        return new Identifier("mysticalagriculture:infusion");
    }

    @Override
    public @NotNull Optional<Identifier> getRecipeLocation() {
        return RecipeDisplay.super.getRecipeLocation();
    }

}
