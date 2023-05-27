package com.alex.mysticalagriculture.cucumber.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
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
        private String prependText = "";

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

        public LocalizableBuilder prepend(String text) {
            this.prependText += text;
            return this;
        }

        public MutableText build() {
            var component = Text.translatable(this.key, this.args);

            if (!this.prependText.equals("")) {
                component = Text.literal(this.prependText).append(component);
            }

            if (this.color != null) {
                component.formatted(this.color);
            }

            return component;
        }

        public String buildString() {
            return this.build().getString();
        }
    }
}
