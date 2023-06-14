package com.alex.mysticalagriculture.handler;

import com.alex.cucumber.forge.event.entity.player.PlayerXpEvent;
import com.alex.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.alex.mysticalagriculture.items.ExperienceCapsuleItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ExperienceCapsuleHandler {
    public static boolean onPlayerPickupXp(PlayerXpEvent.PickupXp event) {
        var orb = event.getOrb();
        var player = event.getEntity();

        if (player != null) {
            var capsules = getExperienceCapsules(player);

            if (!capsules.isEmpty()) {
                for (var stack : capsules) {
                    int remaining = ExperienceCapsuleUtils.addExperienceToCapsule(stack, orb.getValue());

                    orb.value = remaining;

                    if (remaining == 0) {
                        orb.discard();

                        //NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ExperienceCapsulePickupMessage());

                        return true;
                    }
                }
            }
        }
        return false;
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