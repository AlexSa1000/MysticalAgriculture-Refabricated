package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.mysticalagriculture.cucumber.helper.BlockHelper;
import com.alex.mysticalagriculture.cucumber.helper.ColorHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;

public class MiningAOEAugment extends Augment {
    private final int range;

    public MiningAOEAugment(Identifier id, int tier, int range) {
        super(id, tier, EnumSet.of(AugmentType.PICKAXE, AugmentType.AXE, AugmentType.SHOVEL), getColor(0xD5FFF6, tier), getColor(0x0EBABD, tier));
        this.range = range;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        var world = player.getEntityWorld();
        var trace = BlockHelper.rayTraceBlocks(world, player);
        int side = trace.getSide().ordinal();

        return harvest(stack, this.range, world, pos, side, player);
    }

    private static boolean harvest(ItemStack stack, int radius, World world, BlockPos pos, int side, PlayerEntity player) {
        if (world.isClient())
            return true;

        if (player.isSneaking())
            radius = 0;

        int xRange = radius;
        int yRange = radius;
        int zRange = 0;

        if (side == 0 || side == 1) {
            zRange = radius;
            yRange = 0;
        }

        if (side == 4 || side == 5) {
            xRange = 0;
            zRange = radius;
        }

        var state = world.getBlockState(pos);
        var hardness = state.getHardness(world, pos);

        BlockHelper.harvestBlock(stack, world, (ServerPlayerEntity) player, pos);

        if (radius > 0 && hardness >= 0.2F && canHarvestBlock(stack, state)) {
            BlockPos.stream(pos.add(-xRange, -yRange, -zRange), pos.add(xRange, yRange, zRange)).forEach(aoePos -> {
                if (aoePos != pos) {
                    var aoeState = world.getBlockState(aoePos);

                    if (canHarvestBlock(stack, aoeState) && world.getBlockEntity(aoePos) == null && aoeState.getHardness(world, aoePos) <= hardness + 5.0F) {
                        BlockHelper.harvestAOEBlock(stack, world, (ServerPlayerEntity) player, aoePos.toImmutable());
                    }
                }
            });
        }

        return true;
    }

    private static boolean canHarvestBlock(ItemStack stack, BlockState state) {
        return stack.isSuitableFor(state) || (!state.isToolRequired() && stack.getMiningSpeedMultiplier(state) > 1.0F);
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 5, 1));
    }

    public int getRange() {
        return range;
    }
}
