package com.alex.mysticalagriculture.util.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class BaseOreBlock extends BaseBlock {
    private final int minExp;
    private final int maxExp;

    public BaseOreBlock(Material material, BlockSoundGroup sound, float hardness, float resistance, int minExp, int maxExp) {
        super(material, sound, hardness, resistance);
        this.minExp = minExp;
        this.maxExp = maxExp;
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        super.onStacksDropped(state, world, pos, stack);
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            int i = MathHelper.nextInt(world.random, this.minExp, this.maxExp);
            if (i > 0) {
                this.dropExperience(world, pos, i);
            }
        }
    }
}
