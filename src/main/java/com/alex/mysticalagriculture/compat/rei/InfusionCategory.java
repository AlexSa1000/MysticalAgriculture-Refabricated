package com.alex.mysticalagriculture.compat.rei;

import com.alex.cucumber.util.Localizable;
import com.alex.mysticalagriculture.crafting.recipe.InfusionRecipe;
import com.alex.mysticalagriculture.init.ModBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class InfusionCategory implements DisplayCategory<InfusionCategory.RecipeDisplay> {

    public static CategoryIdentifier<RecipeDisplay> INFUSION = CategoryIdentifier.of(new ResourceLocation(MOD_ID, "infusion"));

    @Override
    public CategoryIdentifier<RecipeDisplay> getCategoryIdentifier() {
        return INFUSION;
    }

    @Override
    public Component getTitle() {
        return Localizable.of("rei.category.mysticalagriculture.infusion").build();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.INFUSION_ALTAR);
    }

    @Override
    public List<Widget> setupDisplay(RecipeDisplay recipeDisplay, Rectangle bounds) {
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
        outputSlot.entries(recipeDisplay.getOutputEntries().get(0));
        outputSlot.disableBackground();
        widgets.add(outputSlot);

        return widgets;
    }

    @Override
    public int getDisplayWidth(RecipeDisplay display) {
        return 144 + 8;
    }

    @Override
    public int getDisplayHeight() {
        return 81 + 8;
    }

    record RecipeDisplay(InfusionRecipe recipe) implements Display {

        @Override
        public List<EntryIngredient> getInputEntries() {
            return EntryIngredients.ofIngredients(recipe.getIngredients());
        }

        @Override
        public List<EntryIngredient> getOutputEntries() {
            return Collections.singletonList(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess())));
        }

        @Override
        public CategoryIdentifier<?> getCategoryIdentifier() {
            return INFUSION;
        }
    }

}
