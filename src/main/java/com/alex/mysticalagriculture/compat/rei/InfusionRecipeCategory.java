package com.alex.mysticalagriculture.compat.rei;

import com.alex.mysticalagriculture.init.Blocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.widgets.Slot;
import me.shedaniel.rei.api.widgets.Widgets;
import me.shedaniel.rei.gui.widget.Widget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InfusionRecipeCategory implements RecipeCategory<InfusionRecipeDisplay> {

    @Override
    public @NotNull Identifier getIdentifier() {
        return REIPlugin.INFUSION;
    }

    @Override
    public @NotNull EntryStack getLogo() {
        return EntryStack.create(Blocks.INFUSION_ALTAR);
    }

    @Override
    public @NotNull String getCategoryName() {
        return I18n.translate("rei.category.mysticalagriculture.infusion");
    }

    @Override
    public @NotNull List<Widget> setupDisplay(InfusionRecipeDisplay recipeDisplay, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createCategoryBase(bounds));

        Widget middleSlotBackground = Widgets.createResultSlotBackground(new Point(bounds.x + 28 + 4 + 5, bounds.y + 28 + 4 + 5));
        widgets.add(middleSlotBackground);

        Slot middleSlot = Widgets.createSlot(new Point(bounds.x + 28 + 4 + 5, bounds.y + 28 + 4 + 5));
        middleSlot.entries(recipeDisplay.getInputEntries().get(0));
        middleSlot.disableBackground();
        widgets.add(middleSlot);

        Slot slot1 = Widgets.createSlot(new Point(bounds.x + 6 + 4 + 1, bounds.y + 6 + 4 + 1));
        slot1.entries(recipeDisplay.getInputEntries().get(1));
        widgets.add(slot1);

        Slot slot2 = Widgets.createSlot(new Point(bounds.x + 32 + 4 + 1, bounds.y + 0 + 4 + 1));
        slot2.entries(recipeDisplay.getInputEntries().get(2));
        widgets.add(slot2);

        Slot slot3 = Widgets.createSlot(new Point(bounds.x + 58 + 4 + 1, bounds.y + 6 + 4 + 1));
        slot3.entries(recipeDisplay.getInputEntries().get(3));
        widgets.add(slot3);

        Slot slot4 = Widgets.createSlot(new Point(bounds.x + 64 + 4 + 1, bounds.y + 32 + 4 + 1));
        slot4.entries(recipeDisplay.getInputEntries().get(4));
        widgets.add(slot4);

        Slot slot5 = Widgets.createSlot(new Point(bounds.x + 58 + 4 + 1, bounds.y + 58 + 4 + 1));
        slot5.entries(recipeDisplay.getInputEntries().get(5));
        widgets.add(slot5);

        Slot slot6 = Widgets.createSlot(new Point(bounds.x + 32 + 4 + 1, bounds.y + 63 + 4 + 1));
        slot6.entries(recipeDisplay.getInputEntries().get(6));
        widgets.add(slot6);

        Slot slot7 = Widgets.createSlot(new Point(bounds.x + 6 + 4 + 1, bounds.y + 58 + 4 + 1));
        slot7.entries(recipeDisplay.getInputEntries().get(7));
        widgets.add(slot7);

        Slot slot8 = Widgets.createSlot(new Point(bounds.x + 0 + 4 + 1, bounds.y + 32 + 4 + 1));
        slot8.entries(recipeDisplay.getInputEntries().get(8));
        widgets.add(slot8);

        Widget arrow = Widgets.createArrow(new Point(bounds.x + 88 + 4, bounds.y + 32 + 4));
        widgets.add(arrow);

        Widget outputSlotBackground = Widgets.createResultSlotBackground(new Point(bounds.x + 118 + 4 + 5, bounds.y + 28 + 4 + 5));
        widgets.add(outputSlotBackground);

        Slot outputSlot = Widgets.createSlot(new Point(bounds.x + 118 + 4 + 5, bounds.y + 28 + 4 + 5));
        outputSlot.entries(recipeDisplay.getResultingEntries().get(0));
        outputSlot.disableBackground();
        widgets.add(outputSlot);


        return widgets;
    }

    @Override
    public int getDisplayWidth(InfusionRecipeDisplay display) {
        return 144 + 8;
    }

    @Override
    public int getDisplayHeight() {
        return 81 + 8;
    }

}
