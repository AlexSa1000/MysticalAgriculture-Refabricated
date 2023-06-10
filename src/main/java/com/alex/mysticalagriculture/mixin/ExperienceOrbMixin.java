package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.alex.mysticalagriculture.items.ExperienceCapsuleItem;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin {

    @Shadow public abstract int getValue();

    @Shadow private int value;

    @Inject(method = "playerTouch", at = @At(value = "HEAD"), cancellable = true)
    private void injected(Player player, CallbackInfo ci) {
        if (player != null) {
            var capsules = this.getExperienceCapsules(player);

            if (!capsules.isEmpty()) {
                for (var stack : capsules) {
                    int remaining = ExperienceCapsuleUtils.addExperienceToCapsule(stack, this.getValue());

                    this.value = remaining;

                    if (remaining == 0) {
                        ci.cancel();
                        return;
                    }
                }
            }
        }
    }

    private List<ItemStack> getExperienceCapsules(Player player) {
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
