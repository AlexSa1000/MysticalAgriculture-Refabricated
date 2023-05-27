package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.block.BaseBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrowthAcceleratorBlock extends BaseBlock {
    private final int range;
    private final Formatting textColor;

    public GrowthAcceleratorBlock(int range, Formatting textColor) {
        super(Material.STONE, BlockSoundGroup.STONE, 5.0F, 8.0F, true);
        this.range = range;
        this.textColor = textColor;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, getTickRate());
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        BlockPos.stream(pos.up(2), pos.add(0, this.range + 2, 0))
                .filter(aoePos -> world.getBlockState(aoePos).getBlock() instanceof Fertilizable)
                .findFirst()
                .ifPresent(aoePos -> world.getBlockState(aoePos).randomTick(world, aoePos, random));

        world.scheduleBlockTick(pos, this, getTickRate());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(ModTooltips.GROWTH_ACCELERATOR.build());
        var rangeNumber = Text.literal(String.valueOf(this.range)).formatted(this.textColor);
        tooltip.add(ModTooltips.GROWTH_ACCELERATOR_RANGE.args(rangeNumber).build());
    }


    private static int getTickRate() {
        double variance = Math.random() * (1.1 - 0.9) + 0.9;
        return (int) (ModConfigs.GROWTH_ACCELERATOR_COOLDOWN.get() * variance) * 20;
    }
}
