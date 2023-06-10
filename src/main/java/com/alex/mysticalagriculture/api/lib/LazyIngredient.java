package com.alex.mysticalagriculture.api.lib;

import com.alex.mysticalagriculture.crafting.ingredient.StrictNBTIngredient;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class LazyIngredient {
    public static final LazyIngredient EMPTY = new LazyIngredient(null, null, null) {
        @Override
        public Ingredient getIngredient() {
            return Ingredient.EMPTY;
        }
    };

    private final String name;
    private final CompoundTag nbt;
    private final Type type;
    private Ingredient ingredient;

    private LazyIngredient(String name, Type type, CompoundTag nbt) {
        this.name = name;
        this.type = type;
        this.nbt = nbt;
    }

    public static LazyIngredient item(String name) {
        return item(name, null);
    }

    public static LazyIngredient item(String name, CompoundTag nbt) {
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
                TagKey<Item> tag = ServerTagManagerHolder.getTagManager().getItems().getTag(new ResourceLocation(this.name));
                if (tag != null && !tag.values().isEmpty())
                    this.ingredient = Ingredient.fromTag(tag);
            } else if (this.isItem()) {
                Item item = Registry.ITEM.get(new ResourceLocation(this.name));
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

    public Ingredient.Value createValue() {
        if (this.isTag()) {
            var tag = ItemTags.bind(name);
            return new Ingredient.TagValue(tag);
        } else if (this.isItem()) {
            Item item = Registry.ITEM.get(new ResourceLocation(this.name));
            if (item != null) {
                ItemStack stack = new ItemStack(item);

                if (this.nbt != null && !this.nbt.isEmpty()) {
                    stack.setTag(this.nbt);
                }

                return new Ingredient.ItemValue(stack);
            }
        }

        return null;
    }

    public Ingredient getIngredient() {
        if (this.ingredient == null) {
            if (this.isTag()) {
                var tag = ItemTags.bind(name);
                this.ingredient = Ingredient.of(tag);
            } else if (this.isItem()) {
                var item = Registry.ITEM.get(new ResourceLocation(this.name));

                if (item != null) {
                    if (this.nbt == null || this.nbt.isEmpty()) {
                        this.ingredient = Ingredient.of(item);
                    } else {
                        var stack = new ItemStack(item);

                        stack.setTag(this.nbt);

                        //this.ingredient = StrictNBTIngredient.of(stack);
                        this.ingredient = StrictNBTIngredient.of(stack).getBase();
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
