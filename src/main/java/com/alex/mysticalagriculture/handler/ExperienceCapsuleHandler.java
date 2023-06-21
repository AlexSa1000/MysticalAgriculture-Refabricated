package com.alex.mysticalagriculture.handler;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.alex.mysticalagriculture.items.ExperienceCapsuleItem;
import io.github.fabricators_of_create.porting_lib.event.common.PlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ExperienceCapsuleHandler {
    public static ResourceLocation EXPERIENCE_CAPSULE_PICKUP = new ResourceLocation(MysticalAgriculture.MOD_ID, "experience_capsule_pickup");

    public static void onPlayerPickupXp(PlayerEvents.PickupXp event) {
        var orb = event.getOrb();
        var player = event.getPlayer();

        if (player != null) {
            var capsules = getExperienceCapsules(player);

            if (!capsules.isEmpty()) {
                for (var stack : capsules) {
                    int remaining = ExperienceCapsuleUtils.addExperienceToCapsule(stack, orb.getValue());

                    orb.value = remaining;

                    if (remaining == 0) {
                        orb.discard();

                        //NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ExperienceCapsulePickupMessage());
                        ServerPlayNetworking.send((ServerPlayer) player, EXPERIENCE_CAPSULE_PICKUP, PacketByteBufs.empty());

                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }
    }

    private static List<ItemStack> getExperienceCapsules(Player player) {
        var items = new ArrayList<ItemStack>();

        var stack = player.getOffhandItem();
        if (stack.getItem() instanceof ExperienceCapsuleItem)
            items.add(stack);

        player.getInventory().items
                .stream()
                .filter(s -> s.getItem() instanceof ExperienceCapsuleItem)
                .forEach(items::add);

        return items;
    }
}
