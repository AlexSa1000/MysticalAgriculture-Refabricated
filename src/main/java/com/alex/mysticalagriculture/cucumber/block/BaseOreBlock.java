package com.alex.mysticalagriculture.cucumber.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

public class BaseOreBlock extends BaseBlock {
    private final int minExp;
    private final int maxExp;

    public BaseOreBlock(Material material, Function<Settings, Settings> settings, int minExp, int maxExp) {
        super(material, settings.compose(Settings::requiresTool));
        this.minExp = minExp;
        this.maxExp = maxExp;
    }

    public BaseOreBlock(Material material, BlockSoundGroup sound, float hardness, float resistance, int minExp, int maxExp) {
        this(material, p -> p.sounds(sound).strength(hardness, resistance), minExp, maxExp);
    }


    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) == 0) {
            int i = MathHelper.nextInt(world.random, this.minExp, this.maxExp);
            if (i > 0) {
                this.dropExperience(world, pos, i);
            }
        }
    }
}
