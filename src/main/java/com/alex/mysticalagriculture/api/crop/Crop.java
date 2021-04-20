package com.alex.mysticalagriculture.api.crop;

import com.alex.mysticalagriculture.api.lib.LazyIngredient;
import com.alex.mysticalagriculture.blocks.InfusedFarmlandBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.function.Supplier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class Crop {

    private final String id;
    private final CropTier tier;
    private final CropType type;
    private int essenceColor;
    private int flowerColor;
    private int seedColor;
    private Supplier<? extends CropBlock> crop;
    private Supplier<? extends Item> essence;
    private Supplier<? extends AliasedBlockItem> seeds;
    private final LazyIngredient craftingMaterial;

    public Crop(String id, CropTier tier, CropType type, LazyIngredient craftingMaterial) {
        this.id = id;
        this.tier = tier;
        this.type = type;
        this.craftingMaterial = craftingMaterial;
        this.getCraftingMaterial();
    }

    public Crop(String id, CropTier tier, CropType type, int color, LazyIngredient craftingMaterial) {
        this.id = id;
        this.tier = tier;
        this.type = type;
        this.setColor(color);
        this.craftingMaterial = craftingMaterial;
        this.getCraftingMaterial();
    }

    public String getId() {
        return this.id;
    }

    public CropBlock getCrop() {
        return this.crop == null ? null : this.crop.get();
    }

    public Item getEssence() {
        return this.essence == null ? null : this.essence.get();
    }

    public ItemConvertible getSeeds() {
        return this.seeds == null ? null : this.seeds.get();
    }

    public int getEssenceColor() {
        return this.essenceColor;
    }

    public int getFlowerColor() {
        return this.flowerColor;
    }

    public int getSeedColor() {
        return this.seedColor;
    }

    public Crop setCrop(Supplier<? extends CropBlock> crop) {
        this.crop = crop;
        return this;
    }

    public Crop setEssence(Supplier<? extends Item> essence) {
        this.essence = essence;
        return this;
    }

    public void setSeeds(Supplier<? extends AliasedBlockItem> seeds) {
        this.seeds = seeds;
    }

    public void setFlowerColor(int color) {
        this.flowerColor = color;
    }

    public void setEssenceColor(int color) {
        this.essenceColor = color;
    }

    public void setSeedColor(int color) {
        this.seedColor = color;
    }

    public Crop setColor(int color) {
        this.setFlowerColor(color);
        this.setEssenceColor(color);
        this.setSeedColor(color);

        return this;
    }

    public Ingredient getCraftingMaterial() {
        return this.craftingMaterial.getIngredient();
    }

    public CropTier getTier() {
        return this.tier;
    }

    public Text getDisplayName() {
        return new TranslatableText(String.format("crop.%s.%s", MOD_ID, getId()));
    }

    public double getSecondaryChance(Block block) {
        double chance = 0;
        if (!this.getTier().hasSecondarySeedDrop())
            return chance;
        if (block instanceof InfusedFarmlandBlock)
            chance += 0.1;
        if (this.getTier().isEffectiveFarmland(block))
            chance += 0.1;

        return chance;
    }

    public CropType getType() {
        return this.type;
    }

}
