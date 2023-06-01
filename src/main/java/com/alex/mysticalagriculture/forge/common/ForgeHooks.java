package com.alex.mysticalagriculture.forge.common;

import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.cucumber.item.tool.BaseScytheItem;
import com.alex.mysticalagriculture.forge.client.event.ForgeEventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ForgeHooks {

    public static float[] onLivingFall(LivingEntity entity, float distance, float damageMultiplier)
    {
        LivingFallEvent event = new LivingFallEvent(entity, distance, damageMultiplier);

        if (entity instanceof PlayerEntity player) {
            var world = player.getEntityWorld();

            AugmentUtils.getArmorAugments(player).forEach(a -> {
                a.onPlayerFall(world, player, event);
            });
        }

        return new float[]{event.getDistance(), event.getDamageMultiplier()};
    }

    public static class LivingFallEvent
    {
        private final LivingEntity livingEntity;
        private float distance;
        private float damageMultiplier;
        public LivingFallEvent(LivingEntity entity, float distance, float damageMultiplier)
        {
            this.livingEntity = entity;
            this.setDistance(distance);
            this.setDamageMultiplier(damageMultiplier);
        }
        public LivingEntity getEntity()
        {
            return livingEntity;
        }
        public float getDistance() { return distance; }
        public void setDistance(float distance) { this.distance = distance; }
        public float getDamageMultiplier() { return damageMultiplier; }
        public void setDamageMultiplier(float damageMultiplier) { this.damageMultiplier = damageMultiplier; }
    }


    public static boolean onPlayerAttackTarget(PlayerEntity player, Entity target)
    {
        ItemStack stack = player.getMainHandStack();
        if (stack.getItem() instanceof BaseScytheItem scytheItem)
            return stack.isEmpty() || !scytheItem.onLeftClickEntity(stack, player, target);
        return true;
    }

    public static boolean isCorrectToolForDrops(@NotNull BlockState state, @NotNull PlayerEntity player)
    {
        if (!state.isToolRequired())
            return ForgeEventFactory.doPlayerHarvestCheck(player, state, true);

        return player.canHarvest(state);
    }

    public static int onBlockBreakEvent(World world, GameMode type, ServerPlayerEntity player, BlockPos pos) {
        // Logic from tryHarvestBlock for pre-canceling the event
        boolean preCancelEvent = false;
        ItemStack itemstack = player.getMainHandStack();
        if (!itemstack.isEmpty() && !itemstack.getItem().canMine(world.getBlockState(pos), world, pos, player))
        {
            preCancelEvent = true;
        }

        if (type.isBlockBreakingRestricted())
        {
            if (type == GameMode.SPECTATOR)
                preCancelEvent = true;

            if (!player.canModifyBlocks())
            {
                if (itemstack.isEmpty() || !itemstack.canDestroy(world.getRegistryManager().get(Registry.BLOCK_KEY), new CachedBlockPosition(world, pos, false)))
                    preCancelEvent = true;
            }
        }

        // Tell client the block is gone immediately then process events
        if (world.getBlockEntity(pos) == null)
        {
            player.networkHandler.sendPacket(new BlockUpdateS2CPacket(pos, world.getFluidState(pos).getBlockState()));
        }

        // Post the block break event
        BlockState state = world.getBlockState(pos);
        com.alex.mysticalagriculture.forge.client.event.world.BlockEvent.BreakEvent event = new com.alex.mysticalagriculture.forge.client.event.world.BlockEvent.BreakEvent(world, pos, state, player);
        //event.setCanceled(preCancelEvent);
        //MinecraftForge.EVENT_BUS.post(event);

        // Handle if the event is canceled
        /*if (event.isCanceled())
        {
            // Let the client know the block still exists
            player.networkHandler.send(new ClientboundBlockUpdatePacket(world, pos));

            // Update any tile entity data for this block
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity != null)
            {
                Packet<?> pkt = blockEntity.getUpdatePacket();
                if (pkt != null)
                {
                    player.networkHandler.send(pkt);
                }
            }
        }*/
        return /*event.isCanceled() ? -1 :*/ event.getExpToDrop();
    }
}
