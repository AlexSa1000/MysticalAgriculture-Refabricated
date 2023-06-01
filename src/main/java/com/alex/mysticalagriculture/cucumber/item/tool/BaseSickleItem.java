package com.alex.mysticalagriculture.cucumber.item.tool;

import com.alex.mysticalagriculture.cucumber.helper.BlockHelper;
import com.alex.mysticalagriculture.cucumber.iface.Enableable;
import com.alex.mysticalagriculture.cucumber.lib.ModTags;
import com.alex.mysticalagriculture.forge.common.extensions.ForgeItem;
import draylar.magna.api.MagnaTool;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

public class BaseSickleItem extends MiningToolItem implements MagnaTool, ForgeItem {
    private final float attackDamage;
    private final float attackSpeed;
    private final int range;

    public BaseSickleItem(ToolMaterial tier, float attackDamage, float attackSpeed, int range, Function<Settings, Settings> properties) {
        super(attackDamage, attackSpeed, tier, ModTags.MINEABLE_WITH_SICKLE, properties.apply(new Settings()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.range = range;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this instanceof Enableable enableable) {
            if (enableable.isEnabled())
                super.appendStacks(group, stacks);
        } else {
            super.appendStacks(group, stacks);
        }
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return isValidMaterial(state) ? this.getMaterial().getMiningSpeedMultiplier() / 2 : super.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return isValidMaterial(state);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
        return this.harvest(itemstack, player.world, pos, player);
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage + this.getMaterial().getAttackDamage();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    @Override
    public int getRadius(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean playBreakEffects() {
        return false;
    }

    private boolean harvest(ItemStack stack, World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient())
            return true;

        var state = world.getBlockState(pos);
        var hardness = state.getHardness(world, pos);

        BlockHelper.harvestBlock(stack, world, (ServerPlayerEntity) player, pos);

        if (this.range > 0 && isValidMaterial(state)) {
            BlockPos.iterate(pos.add(-this.range, -this.range, -this.range), pos.add(this.range, this.range, this.range)).forEach(aoePos -> {
                if (stack.isEmpty())
                    return;

                if (aoePos != pos) {
                    var aoeState = world.getBlockState(aoePos);

                    if (!isValidMaterial(aoeState))
                        return;

                    var aoeHardness = aoeState.getHardness(world, aoePos);

                    if (aoeHardness > hardness + 5.0F)
                        return;

                    var harvested = BlockHelper.harvestAOEBlock(stack, world, (ServerPlayerEntity) player, aoePos.toImmutable());

                    if (harvested && !player.getAbilities().creativeMode && aoeHardness <= 0.0F && Math.random() < 0.33) {
                        stack.damage(1, player, entity -> {
                            entity.sendToolBreakStatus(player.getActiveHand());
                        });
                    }
                }
            });
        }

        return true;
    }

    private static boolean isValidMaterial(BlockState state) {
        var material = state.getMaterial();
        return state.isIn(ModTags.MINEABLE_WITH_SICKLE) || material == Material.LEAVES || material == Material.PLANT || material == Material.REPLACEABLE_PLANT;
    }
}
