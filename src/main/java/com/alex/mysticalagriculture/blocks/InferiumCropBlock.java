package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.config.ModConfigs;
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

            var vec = builder.get(LootContextParameters.ORIGIN);
            if (vec != null) {
                var world = builder.getWorld();
                var pos = new BlockPos((int) Math.floor(vec.x), (int) Math.floor(vec.y), (int) Math.floor(vec.z));
                var below = world.getBlockState(pos.down()).getBlock();

                if (below instanceof InfusedFarmlandBlock farmland) {
                    int tier = farmland.getTier().getValue();
                    crop = (int) ((0.5D * tier) + 0.5D);
                    if (tier > 1 && tier % 2 == 0 && Math.random() < 0.5D)
                        crop++;
                }

                double chance = this.getCrop().getSecondaryChance(below);
                if (ModConfigs.SECONDARY_SEED_DROPS.get() && Math.random() < chance)
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
