package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.util.iface.Colored;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

import java.util.ArrayList;
import java.util.List;

public class InfusedFarmlandBlock extends FarmlandBlock implements Colored {
    public static final List<InfusedFarmlandBlock> FARMLANDS = new ArrayList<>();
    private final CropTier tier;

    public InfusedFarmlandBlock(CropTier tier) {
        super(Settings.copy(Blocks.FARMLAND));
        this.tier = tier;

        FARMLANDS.add(this);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        ItemStack stack = builder.get(LootContextParameters.TOOL);

        if (stack != null && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) > 0) {
            drops.add(new ItemStack(this));
        } else {
            drops.add(new ItemStack(Blocks.DIRT));
            if (builder.getWorld().getRandom().nextInt(100) < 25)
                drops.add(new ItemStack(this.tier.getEssence(), 1));
        }

        return drops;
    }

    @Override
    public int getColor(int index) {
        return this.tier.getColor();
    }

    public CropTier getTier() {
        return this.tier;
    }
}
