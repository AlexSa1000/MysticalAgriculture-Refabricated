package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.api.crop.Crop;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class InferiumCropBlock extends MysticalCropBlock {
    public InferiumCropBlock(Crop crop) {
        super(crop);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        int age = state.get(AGE);

        int crop = 0;
        int seed = 1;

        if (age == this.getMaxAge()) {
            crop = 1;

            Vec3d vec = builder.get(LootContextParameters.ORIGIN);
            if (vec != null) {
                ServerWorld world = builder.getWorld();
                BlockPos pos = new BlockPos(vec);
                Block below = world.getBlockState(pos.down()).getBlock();

                if (below instanceof InfusedFarmlandBlock) {
                    InfusedFarmlandBlock farmland = (InfusedFarmlandBlock) below;
                    int tier = farmland.getTier().getValue();
                    crop = (int) ((0.5D * tier) + 0.5D);
                    if (tier > 1 && tier % 2 == 0 && Math.random() < 0.5D)
                        crop++;
                }

                double chance = this.getCrop().getSecondaryChance(below);
                if (Math.random() < chance)
                    seed = 2;

            }
        }

        List<ItemStack> drops = new ArrayList<>();
        if (crop > 0)
            drops.add(new ItemStack(this.getCropsItem(), crop));

        drops.add(new ItemStack(this.getSeedsItem(), seed));

        return drops;
    }
}
