package com.alex.mysticalagriculture.api.crop;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class CropTier {
    public static final CropTier ELEMENTAL = new CropTier(new ResourceLocation(MOD_ID, "elemental"), 1, 0x748E00, ChatFormatting.YELLOW);
    public static final CropTier ONE = new CropTier(new ResourceLocation(MOD_ID, "1"), 1, 0x748E00, ChatFormatting.YELLOW);
    public static final CropTier TWO = new CropTier(new ResourceLocation(MOD_ID, "2"), 2, 0x008C23, ChatFormatting.GREEN);
    public static final CropTier THREE = new CropTier(new ResourceLocation(MOD_ID, "3"), 3, 0xB74900, ChatFormatting.GOLD);
    public static final CropTier FOUR = new CropTier(new ResourceLocation(MOD_ID, "4"), 4, 0x007FDB, ChatFormatting.AQUA);
    public static final CropTier FIVE = new CropTier(new ResourceLocation(MOD_ID, "5"), 5, 0xC40000, ChatFormatting.RED);


    private final ResourceLocation id;
    private final int value;
    private final int color;
    private final ChatFormatting textColor;
    private Supplier<? extends Block> farmland;
    private Supplier<? extends Item> essence;
    private MutableComponent displayName;
    private boolean fertilizable;
    private final boolean secondarySeedDrop;

    public CropTier(ResourceLocation id, int value, int color, ChatFormatting textColor) {
        this.id = id;
        this.value = value;
        this.color = color;
        this.textColor = textColor;
        this.fertilizable = true;
        this.secondarySeedDrop = true;
    }

    public ResourceLocation getId() {
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

    public ChatFormatting getTextColor() {
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

    public MutableComponent getDisplayName() {
        if (this.displayName != null)
            return this.displayName.withStyle(this.getTextColor());

        return Component.translatable(String.format("cropTier.%s.%s", this.getModId(), this.getName())).withStyle(this.getTextColor());
    }
    public CropTier setDisplayName(MutableComponent name) {
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
