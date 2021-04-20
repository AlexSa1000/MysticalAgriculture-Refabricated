package com.alex.mysticalagriculture.compat.rei;

import com.alex.mysticalagriculture.init.Blocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.widgets.Arrow;
import me.shedaniel.rei.api.widgets.Slot;
import me.shedaniel.rei.api.widgets.Widgets;
import me.shedaniel.rei.gui.widget.Widget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReprocessorRecipeCategory implements RecipeCategory<ReprocessorRecipeDisplay> {

    @Override
    public @NotNull Identifier getIdentifier() {
        return REIPlugin.REPROCESSOR;
    }

    @Override
    public @NotNull EntryStack getLogo() {
        return EntryStack.create(Blocks.BASIC_REPROCESSOR);
    }

    @Override
    public @NotNull String getCategoryName() {
        return I18n.translate("rei.category.mysticalagriculture.reprocessor");
    }

    @Override
    public @NotNull List<Widget> setupDisplay(ReprocessorRecipeDisplay recipeDisplay, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createCategoryBase(bounds));

        Slot input = Widgets.createSlot(new Point(bounds.x + 0 + 4 + 1, bounds.y + 4 + 4 + 1));
        input.entries(recipeDisplay.getInputEntries().get(0));
        widgets.add(input);

        Arrow arrow = Widgets.createArrow(new Point(bounds.x + 24 + 4, bounds.y + 4 + 4));
        arrow.animationDurationMS(10000);
        widgets.add(arrow);

        Widget outputBackground = Widgets.createResultSlotBackground(new Point(bounds.x + 56 + 4 + 5, bounds.y + 0 + 4 + 5));
        widgets.add(outputBackground);

        Slot output = Widgets.createSlot(new Point(bounds.x + 56 + 4 + 5, bounds.y + 0 + 4 + 5));
        output.entries(recipeDisplay.getResultingEntries().get(0));
        output.disableBackground();
        widgets.add(output);


        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 26 + 8;
    }

    @Override
    public int getDisplayWidth(ReprocessorRecipeDisplay display) {
        return 82 + 8;
    }
}
