package com.alex.mysticalagriculture.cucumber.helper;

import com.alex.mysticalagriculture.forge.common.ForgeHooks;
import com.alex.mysticalagriculture.forge.common.extensions.ForgeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class BlockHelper {
    private static BlockHitResult rayTraceBlocks(World world, PlayerEntity player, double reach, RaycastContext.FluidHandling fluidMode) {
        var pitch = player.getPitch();
        var yaw = player.getYaw();
        var eyePos = player.getCameraPosVec(1.0F);
        var f2 = MathHelper.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        var f3 = MathHelper.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        var f4 = -MathHelper.cos(-pitch * ((float) Math.PI / 180F));
        var f5 = MathHelper.sin(-pitch * ((float) Math.PI / 180F));
        var f6 = f3 * f4;
        var f7 = f2 * f4;

        var vec3d1 = eyePos.add((double) f6 * reach, (double) f5 * reach, (double) f7 * reach);

        return world.raycast(new RaycastContext(eyePos, vec3d1, RaycastContext.ShapeType.OUTLINE, fluidMode, player));
    }

    public static BlockHitResult rayTraceBlocks(World world, PlayerEntity player) {
        return rayTraceBlocks(world, player, RaycastContext.FluidHandling.NONE);
    }

    public static BlockHitResult rayTraceBlocks(World world, PlayerEntity player, RaycastContext.FluidHandling fluidMode) {
        var attribute = player.getAttributeInstance(new ClampedEntityAttribute("generic.reachDistance", 4.5D, 0.0D, 1024.0D).setTracked(true));
        var reach = attribute != null ? attribute.getValue() : 5.0D;

        return rayTraceBlocks(world, player, reach, fluidMode);
    }

    @Deprecated
    public static boolean breakBlocksAOE(ItemStack stack, World world, PlayerEntity player, BlockPos pos) {
        return breakBlocksAOE(stack, world, player, pos, true);
    }

    @Deprecated
    public static boolean breakBlocksAOE(ItemStack stack, World world, PlayerEntity player, BlockPos pos, boolean playEvent) {
        if (world.isAir(pos))
            return false;

        if (!world.isClient() && player instanceof ServerPlayerEntity mplayer) {
            pos = pos.toImmutable();

            var state = world.getBlockState(pos);
            var block = state.getBlock();

            var event = new com.alex.mysticalagriculture.forge.client.event.world.BlockEvent.BreakEvent(world, pos, state, mplayer);
            //if (MinecraftForge.EVENT_BUS.post(event))
            //    return false;

            if (playEvent) {
                world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
            }

            boolean changed = world.setBlockState(pos, state.getFluidState().getBlockState());
            if (changed) {
                if (state.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
                    PiglinBrain.onGuardedBlockInteracted(player, false);
                }

                if (!player.getAbilities().creativeMode) {
                    var tile = world.getBlockEntity(pos);

                    block.onBroken(world, pos, state);
                    block.afterBreak(world, player, pos, state, tile, stack);
                    block.dropExperience((ServerWorld) world, pos, event.getExpToDrop());
                }

                stack.postMine(world, state, pos, player);
            }

            mplayer.networkHandler.sendPacket(new BlockUpdateS2CPacket(world, pos));

            return true;
        }

        return false;
    }

    public static boolean harvestBlock(ItemStack stack, World world, ServerPlayerEntity player, BlockPos pos) {
        return harvestBlock(stack, world, player, pos, true);
    }

    public static boolean harvestBlock(ItemStack stack, World world, ServerPlayerEntity player, BlockPos pos, boolean playEvent) {
        if (world.isClient())
            return true;

        var type = player.interactionManager.getGameMode();

        int exp = ForgeHooks.onBlockBreakEvent(world, type, player, pos);
        if (exp == -1) {
            return false;
        }

        if (player.isBlockBreakingRestricted(world, pos, type))
            return false;

        var state = world.getBlockState(pos);

        if (playEvent) {
            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
        }

        var destroyed = destroyBlock(state, world, player, pos);

        if (player.isCreative())
            return true;

        var block = state.getBlock();
        if (destroyed && /*state.canHarvestBlock(world, pos, player)*/ForgeBlock.canHarvestBlock(state, world, pos, player)) {
            block.afterBreak(world, player, pos, state, world.getBlockEntity(pos), stack);
            stack.postMine(world, state, pos, player);
        }

        if (destroyed && exp > 0) {
            block.dropExperience(player.getWorld(), pos, exp);
        }

        return true;
    }

    public static boolean harvestAOEBlock(ItemStack stack, World world, ServerPlayerEntity player, BlockPos pos) {
        if (harvestBlock(stack, world, player, pos, false)) {
            player.networkHandler.sendPacket(new BlockUpdateS2CPacket(world, pos));
            return true;
        }

        return false;
    }

    public static boolean destroyBlock(BlockState state, World world, PlayerEntity player, BlockPos pos) {
        var canHarvest = !player.isCreative() && /*state.canHarvestBlock(world, pos, player)*/ForgeBlock.canHarvestBlock(state, world, pos, player);
        var destroyed = ForgeBlock.onDestroyedByPlayer(state, world, pos, player, canHarvest, world.getFluidState(pos));

        if (destroyed) {
            state.getBlock().onBroken(world, pos, state);
        }

        return destroyed;
    }
}
