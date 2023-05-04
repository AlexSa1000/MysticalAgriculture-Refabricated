package com.alex.mysticalagriculture.api.crop;

import net.minecraft.block.Block;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
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
    private final Formatting textColor;
    private Supplier<? extends Block> farmland;
    private Supplier<? extends Item> essence;
    private MutableText displayName;
    private boolean fertilizable;
    private final boolean secondarySeedDrop;

    public CropTier(Identifier id, int value, int color, Formatting textColor) {
        this.id = id;
        this.value = value;
        this.color = color;
        this.textColor = textColor;
        this.fertilizable = true;
        this.secondarySeedDrop = true;
    }

    public Identifier getId() {
        return this.id;
    }

    public String getName() {
        return this.id.getPath();
    }

    public String getModId() {
        return this.id.getNamespace();
    }
    public int getValue() {
        return this.value;
    }

    public int getColor() {
        return this.color;
    }

    public Formatting getTextColor() {
        return this.textColor;
    }

    public Block getFarmland() {
        return this.farmland == null ? null : this.farmland.get();
    }
    public CropTier setFarmland(Supplier<? extends Block> farmland) {
        this.farmland = farmland;
        return this;
    }

    public Item getEssence() {
        return this.essence == null ? null : this.essence.get();
    }
    public CropTier setEssence(Supplier<? extends Item> essence) {
        this.essence = essence;
        return this;
    }

    public boolean isEffectiveFarmland(Block block) {
        return this.farmland.get() == block;
    }

    public MutableText getDisplayName() {
        if (this.displayName != null)
            return this.displayName.formatted(this.getTextColor());

        return Text.translatable(String.format("cropTier.%s.%s", this.getModId(), this.getName())).formatted(this.getTextColor());
    }
    public CropTier setDisplayName(MutableText name) {
        this.displayName = name;
        return this;
    }

    public boolean isFertilizable() {
        return fertilizable;
    }
    public CropTier setFertilizable(boolean fertilizable) {
        this.fertilizable = fertilizable;
        return this;
    }

    public boolean hasSecondarySeedDrop() {
        return this.secondarySeedDrop;
    }
}
