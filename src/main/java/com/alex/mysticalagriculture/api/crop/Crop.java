package com.alex.mysticalagriculture.api.crop;

import com.alex.mysticalagriculture.api.lib.LazyIngredient;
import com.alex.mysticalagriculture.blocks.InfusedFarmlandBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class Crop {

    private final Identifier id;
    private Text displayName;
    private CropTier tier;
    private CropType type;
    private int essenceColor;
    private int flowerColor;
    private int seedColor;
    private CropTextures textures;
    private Supplier<? extends CropBlock> crop;
    private Supplier<? extends Item> essence;
    private Supplier<? extends AliasedBlockItem> seeds;
    private Supplier<? extends Block> crux;
    private final LazyIngredient craftingMaterial;
    private boolean enabled;
    private boolean registerCropBlock;
    private boolean registerEssenceItem;
    private boolean registerSeedsItem;
    private boolean hasEffect;
    private CropRecipes recipeConfig;
    private Set<Identifier> requiredBiomes;

    public Crop(Identifier id, CropTier tier, CropType type, LazyIngredient craftingMaterial) {
        this(id, tier, type, new CropTextures(), craftingMaterial);
    }

    public Crop(Identifier id, CropTier tier, CropType type, int color, LazyIngredient craftingMaterial) {
        this(id, tier, type, new CropTextures(), color, craftingMaterial);
    }

    public Crop(Identifier id, CropTier tier, CropType type, CropTextures textures, LazyIngredient craftingMaterial) {
        this(id, tier, type, textures, -1, craftingMaterial);
    }

    public Crop(Identifier id, CropTier tier, CropType type, CropTextures textures, int color, LazyIngredient craftingMaterial) {
        this.id = id;
        this.tier = tier;
        this.type = type;
        this.textures = textures.init(id);
        this.setColor(color);
        this.craftingMaterial = craftingMaterial;
        //this.baseSecondaryChance = -1;
        this.enabled = true;
        this.registerCropBlock = true;
        this.registerEssenceItem = true;
        this.registerSeedsItem = true;
        this.recipeConfig = new CropRecipes();
        this.hasEffect = false;
        //this.recipeConfig = new CropRecipes();
        //this.requiredBiomes = new HashSet<>();
    }

    public Identifier getId() {
        return this.id;
    }

    public String getName() {
        return this.getId().getPath();
    }

    public String getModId() {
        return this.getId().getNamespace();
    }

    public String getNameWithSuffix(String suffix) {
        return String.format("%s_%s", this.getName(), suffix);
    }

    public Text getDisplayName() {
        return this.displayName != null
                ? this.displayName
                : Text.translatable(String.format("crop.%s.%s", this.getModId(), this.getName()));
    }
    public Crop setDisplayName(Text name) {
        this.displayName = name;
        return this;
    }

    public CropTier getTier() {
        return this.tier;
    }
    public Crop setTier(CropTier tier) {
        this.tier = tier;
        return this;
    }

    public CropType getType() {
        return this.type;
    }
    public Crop setType(CropType type) {
        this.type = type;
        return this;
    }

    public CropTextures getTextures() {
        return this.textures;
    }

    public boolean isFlowerColored() {
        return this.getFlowerColor() > -1;
    }
    public int getFlowerColor() {
        return this.flowerColor;
    }
    public void setFlowerColor(int color) {
        this.flowerColor = color;
    }

    public boolean isEssenceColored() {
        return this.getEssenceColor() > -1;
    }
    public int getEssenceColor() {
        return this.essenceColor;
    }
    public void setEssenceColor(int color) {
        this.essenceColor = color;
    }

    public boolean isSeedColored() {
        return this.getSeedColor() > -1;
    }
    public int getSeedColor() {
        return this.seedColor;
    }
    public void setSeedColor(int color) {
        this.seedColor = color;
    }

    public CropBlock getCropBlock() {
        return this.crop == null ? null : this.crop.get();
    }
    public Crop setCropBlock(Supplier<? extends CropBlock> crop) {
        return this.setCropBlock(crop, false);
    }
    public Crop setCropBlock(Supplier<? extends CropBlock> crop, boolean register) {
        this.crop = crop;
        this.registerCropBlock = register;
        return this;
    }
    public boolean shouldRegisterCropBlock() {
        return this.registerCropBlock;
    }

    public Item getEssenceItem() {
        return this.essence == null ? null : this.essence.get();
    }
    public Crop setEssenceItem(Supplier<? extends Item> essence) {
        return this.setEssenceItem(essence, false);
    }
    public Crop setEssenceItem(Supplier<? extends Item> essence, boolean register) {
        this.essence = essence;
        this.registerEssenceItem = register;
        return this;
    }
    public boolean shouldRegisterEssenceItem() {
        return this.registerEssenceItem;
    }

    public ItemConvertible getSeedsItem() {
        return this.seeds == null ? null : this.seeds.get();
    }
    public Crop setSeedsItem(Supplier<? extends AliasedBlockItem> seeds) {
        return this.setSeedsItem(seeds, false);
    }
    public Crop setSeedsItem(Supplier<? extends AliasedBlockItem> seeds, boolean register) {
        this.seeds = seeds;
        this.registerSeedsItem = register;
        return this;
    }
    public boolean shouldRegisterSeedsItem() {
        return this.registerSeedsItem;
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

    public Ingredient getCraftingMaterial() {
        return this.craftingMaterial.getIngredient();
    }

    public LazyIngredient getLazyIngredient() {
        return this.craftingMaterial;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
    public Crop setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Block getCruxBlock() {
        return this.crux == null ? null : this.crux.get();
    }
    public Crop setCruxBlock(Supplier<? extends Block> crux) {
        this.crux = crux;
        return this;
    }

    public boolean hasEffect(ItemStack stack) {
        return this.hasEffect;
    }
    public Crop setHasEffect(boolean hasEffect) {
        this.hasEffect = hasEffect;
        return this;
    }

    public CropRecipes getRecipeConfig() {
        return this.recipeConfig;
    }

    public Set<Identifier> getRequiredBiomes() {
        return this.requiredBiomes;
    }
    public Crop addRequiredBiome(Identifier id) {
        this.requiredBiomes.add(id);
        return this;
    }

    public Crop setColor(int color) {
        this.setFlowerColor(color);
        this.setEssenceColor(color);
        this.setSeedColor(color);

        return this;
    }
}
