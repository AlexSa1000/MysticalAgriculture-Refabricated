package com.alex.mysticalagriculture.crafting.ingredient;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class NBTIngredient extends Ingredient {

    private final ItemStack stack;
    public NBTIngredient(ItemStack stack)
    {
        super(Stream.of(new Ingredient.StackEntry(stack)));
        this.stack = stack;
    }

    @Override
    public boolean test(@Nullable ItemStack input) {
        if (input == null)
            return false;
        return this.stack.getItem() == input.getItem() && this.stack.getDamage() == input.getDamage() && this.stack.isEqual(input);
    }
}
