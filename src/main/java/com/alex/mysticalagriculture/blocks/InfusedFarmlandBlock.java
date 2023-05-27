package com.alex.mysticalagriculture.blocks;

import com.alex.mysticalagriculture.api.crop.CropTier;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.iface.Colored;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 1.0F, world.getDamageSources().fall());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        int moisture = state.get(MOISTURE);

        if (!isWaterNearby(world, pos) && !world.hasRain(pos.up())) {
            if (moisture > 0) {
                world.setBlockState(pos, state.with(MOISTURE, moisture - 1), 2);
            }
        } else if (moisture < 7) {
            world.setBlockState(pos, state.with(MOISTURE, 7), 2);
        }    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        var stack = builder.get(LootContextParameters.TOOL);

        if (stack != null && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) > 0) {
            drops.add(new ItemStack(this));
        } else {
            drops.add(new ItemStack(Blocks.DIRT));
            var random = builder.getWorld().getRandom();
            if (random.nextInt(100) < 25)
                drops.add(new ItemStack(this.tier.getEssence(), 1));
        }

        return drops;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(ModTooltips.TIER.args(this.tier.getDisplayName()).build());
    }

    @Override
    public int getColor(int index) {
        return this.tier.getColor();
    }

    public CropTier getTier() {
        return this.tier;
    }
}
