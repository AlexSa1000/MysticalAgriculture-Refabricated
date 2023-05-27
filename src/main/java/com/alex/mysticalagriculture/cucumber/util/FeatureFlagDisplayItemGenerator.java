package com.alex.mysticalagriculture.cucumber.util;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Objects;

@FunctionalInterface
public interface FeatureFlagDisplayItemGenerator {
    void add(ItemGroup.DisplayContext var1, Output var2);

    static ItemGroup.EntryCollector create(FeatureFlagDisplayItemGenerator generator) {
        return (parameters, output) -> {
            generator.add(parameters, FeatureFlagDisplayItemGenerator.Output.from(output));
        };
    }

    public interface Output extends ItemGroup.Entries {
        static Output from(ItemGroup.Entries output) {
            Objects.requireNonNull(output);
            return output::add;
        }

        default void add(ItemStack stack, FeatureFlag... flags) {
            if (Arrays.stream(flags).allMatch(FeatureFlag::isEnabled)) {
                this.add(stack);
            }

        }

        default void add(ItemConvertible item) {
            this.add(new ItemStack((ItemConvertible)item));
        }

        default void add(ItemConvertible item, FeatureFlag... flags) {
            if (Arrays.stream(flags).allMatch(FeatureFlag::isEnabled)) {
                this.add(new ItemStack((ItemConvertible)item));
            }

        }
    }
}
