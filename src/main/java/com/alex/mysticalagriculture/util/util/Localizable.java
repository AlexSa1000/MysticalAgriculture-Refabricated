package com.alex.mysticalagriculture.util.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class Localizable {
    private final String key;
    private final Formatting defaultColor;

    protected Localizable(String key) {
        this(key, null);
    }

    protected Localizable(String key, Formatting defaultColor) {
        this.key = key;
        this.defaultColor = defaultColor;
    }

    public static Localizable of(String key) {
        return new Localizable(key);
    }

    public static Localizable of(String key, Formatting defaultColor) {
        return new Localizable(key, defaultColor);
    }

    public String getKey() {
        return this.key;
    }

    public Formatting getDefaultColor() {
        return this.defaultColor;
    }

    public LocalizableBuilder args(Object... args) {
        return this.builder().args(args);
    }

    public LocalizableBuilder color(Formatting color) {
        return this.builder().color(color);
    }

    public MutableText build() {
        return this.builder().build();
    }

    public String buildString() {
        return this.builder().buildString();
    }

    private LocalizableBuilder builder() {
        return new LocalizableBuilder(this.key).color(this.defaultColor);
    }

    public static class LocalizableBuilder {
        private final String key;
        private Object[] args = new Object[0];
        private Formatting color;

        public LocalizableBuilder(String key) {
            this.key = key;
        }

        public LocalizableBuilder args(Object... args) {
            this.args = args;
            return this;
        }

        public LocalizableBuilder color(Formatting color) {
            this.color = color;
            return this;
        }

        public MutableText build() {
            MutableText component = new TranslatableText(this.key, this.args);
            if (this.color != null)
                component.formatted(this.color);

            return component;
        }

        public String buildString() {
            return this.build().getString();
        }
    }
}
