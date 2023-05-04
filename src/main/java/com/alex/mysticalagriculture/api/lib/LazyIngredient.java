package com.alex.mysticalagriculture.api.lib;

import com.alex.mysticalagriculture.crafting.ingredient.NBTIngredient;
import net.fabricmc.fabric.impl.recipe.ingredient.builtin.NbtIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class LazyIngredient {
    public static final LazyIngredient EMPTY = new LazyIngredient(null, null, null) {
        @Override
        public Ingredient getIngredient() {
            return Ingredient.EMPTY;
        }
    };

    private final String name;
    private final NbtCompound nbt;
    private final Type type;
    private Ingredient ingredient;

    private LazyIngredient(String name, Type type, NbtCompound nbt) {
        this.name = name;
        this.type = type;
        this.nbt = nbt;
    }

    public static LazyIngredient item(String name) {
        return item(name, null);
    }

    public static LazyIngredient item(String name, NbtCompound nbt) {
        return new LazyIngredient(name, Type.ITEM, nbt);
    }

    public static LazyIngredient tag(String name) {
        return new LazyIngredient(name, Type.TAG, null);
    }

    public boolean isItem() {
        return this.type == Type.ITEM;
    }

    public boolean isTag() {
        return this.type == Type.TAG;
    }

    /*public Ingredient getIngredient() {
        if (this.ingredient == null) {
            if (this.isTag()) {
                TagKey<Item> tag = ServerTagManagerHolder.getTagManager().getItems().getTag(new Identifier(this.name));
                if (tag != null && !tag.values().isEmpty())
                    this.ingredient = Ingredient.fromTag(tag);
            } else if (this.isItem()) {
                Item item = Registry.ITEM.get(new Identifier(this.name));
                if (this.nbt == null || this.nbt.isEmpty()) {
                    this.ingredient = Ingredient.ofItems(item);
                } else {
                    ItemStack stack = new ItemStack(item);
                    stack.setTag(this.nbt);
                    this.ingredient = new NBTIngredient(stack);
                }
            }
        }

        return this.ingredient == null ? Ingredient.EMPTY : this.ingredient;
    }*/

    public Ingredient.Entry createValue() {
        if (this.isTag()) {
            var tag = ItemTags.of(name);
            return new Ingredient.TagEntry(tag);
        } else if (this.isItem()) {
            Item item = Registries.ITEM.get(new Identifier(this.name));
            if (item != null) {
                ItemStack stack = new ItemStack(item);

                if (this.nbt != null && !this.nbt.isEmpty()) {
                    stack.setNbt(this.nbt);
                }

                return new Ingredient.StackEntry(stack);
            }
        }

        return null;
    }

    public Ingredient getIngredient() {
        if (this.ingredient == null) {
            if (this.isTag()) {
                var tag = ItemTags.of(name);
                this.ingredient = Ingredient.fromTag(tag);
            } else if (this.isItem()) {
                var item = Registries.ITEM.get(new Identifier(this.name));

                if (item != null) {
                    if (this.nbt == null || this.nbt.isEmpty()) {
                        this.ingredient = Ingredient.ofItems(item);
                    } else {
                        var stack = new ItemStack(item);

                        stack.setNbt(this.nbt);

                        //this.ingredient = StrictNBTIngredient.of(stack);
                        this.ingredient = new NBTIngredient(stack);
                    }
                }
            }
        }

        return this.ingredient == null ? Ingredient.EMPTY : this.ingredient;
    }

    private enum Type {
        ITEM, TAG
    }
}
