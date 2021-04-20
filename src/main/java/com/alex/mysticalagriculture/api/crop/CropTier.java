package com.alex.mysticalagriculture.api.crop;

import net.minecraft.block.Block;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class CropTier {
    public static final CropTier ELEMENTAL = new CropTier(new Identifier(MOD_ID, "elemental"), 1, 0x748E00, Formatting.YELLOW);
    public static final CropTier ONE = new CropTier(new Identifier(MOD_ID, "1"), 1, 0x748E00, Formatting.YELLOW);
    public static final CropTier TWO = new CropTier(new Identifier(MOD_ID, "2"), 2, 0x008C23, Formatting.GREEN);
    public static final CropTier THREE = new CropTier(new Identifier(MOD_ID, "3"), 3, 0xB74900, Formatting.GOLD);
    public static final CropTier FOUR = new CropTier(new Identifier(MOD_ID, "4"), 4, 0x007FDB, Formatting.AQUA);
    public static final CropTier FIVE = new CropTier(new Identifier(MOD_ID, "5"), 5, 0xC40000, Formatting.RED);


    private final Identifier id;
    private final int value;
    private final int color;
    private MutableText displayName;
    private final Formatting textColor;
    private final boolean fertilizable;
    private final boolean secondarySeedDrop;

    private Supplier<? extends FarmlandBlock> farmland;
    private Supplier<? extends Item> essence;

    public CropTier(Identifier id, int value, int color, Formatting textColor) {
        this.id = id;
        this.value = value;
        this.color = color;
        this.textColor = textColor;
        this.fertilizable = true;
        this.secondarySeedDrop = true;
    }

    public String getName() {
        return this.id.getPath();
    }

    public String getModId() {
        return this.id.getNamespace();
    }

    public Formatting getTextColor() {
        return this.textColor;
    }

    public MutableText getDisplayName() {
        if (this.displayName != null)
            return this.displayName.formatted(this.getTextColor());

        return new TranslatableText(String.format("cropTier.%s.%s", this.getModId(), this.getName())).formatted(this.getTextColor());
    }

    public boolean isFertilizable() {
        return fertilizable;
    }

    public int getColor() {
        return this.color;
    }

    public Item getEssence() {
        return this.essence == null ? null : this.essence.get();
    }

    public FarmlandBlock getFarmland() {
        return this.farmland == null ? null : this.farmland.get();
    }

    public int getValue() {
        return this.value;
    }

    public boolean hasSecondarySeedDrop() {
        return this.secondarySeedDrop;
    }

    public boolean isEffectiveFarmland(Block block) {
        return this.farmland.get() == block;
    }

    public CropTier setFarmland(Supplier<? extends FarmlandBlock> farmland) {
        this.farmland = farmland;
        return this;
    }

    public CropTier setEssence(Supplier<? extends Item> essence) {
        this.essence = essence;
        return this;
    }

}
