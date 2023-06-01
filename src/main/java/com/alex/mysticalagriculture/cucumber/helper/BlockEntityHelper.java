package com.alex.mysticalagriculture.cucumber.helper;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public final class BlockEntityHelper {
    public BlockEntityHelper() {
    }

    public static void dispatchToNearbyPlayers(BlockEntity block) {
        World world = block.getWorld();
        if (world != null) {
            Packet<ClientPlayPacketListener> packet = block.toUpdatePacket();
            if (packet != null) {
                List<? extends PlayerEntity> players = world.getPlayers();
                BlockPos pos = block.getPos();
                Iterator var5 = players.iterator();

                while(var5.hasNext()) {
                    PlayerEntity player = (PlayerEntity)var5.next();
                    if (player instanceof ServerPlayerEntity) {
                        ServerPlayerEntity mPlayer = (ServerPlayerEntity)player;
                        if (isPlayerNearby(mPlayer.getX(), mPlayer.getZ(), (double)pos.getX() + 0.5, (double)pos.getZ() + 0.5)) {
                            mPlayer.networkHandler.sendPacket(packet);
                        }
                    }
                }

            }
        }
    }

    public static void dispatchToNearbyPlayers(World world, int x, int y, int z) {
        BlockEntity block = world.getBlockEntity(new BlockPos(x, y, z));
        if (block != null) {
            dispatchToNearbyPlayers(block);
        }

    }

    private static boolean isPlayerNearby(double x1, double z1, double x2, double z2) {
        return Math.hypot(x1 - x2, z1 - z2) < 64.0;
    }
}
