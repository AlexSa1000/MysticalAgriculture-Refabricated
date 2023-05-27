package com.alex.mysticalagriculture.cucumber.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public interface OutputResolver {
    ItemStack resolve();

    static OutputResolver.Item create(PacketByteBuf buffer) {
        return new Item(buffer.readItemStack());
    }

    class Tag implements OutputResolver {
        private final String tag;
        private final int count;

        public Tag(String tag, int count) {
            this.tag = tag;
            this.count = count;
        }

        @Override
        public ItemStack resolve() {
            return TagMapper.getItemStackForTag(this.tag, this.count);
        }
    }

    class Item implements OutputResolver {
        private final ItemStack stack;

        public Item(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public ItemStack resolve() {
            return this.stack;
        }
    }
}
