package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.alex.mysticalagriculture.items.ExperienceCapsuleItem;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {

    @Shadow public abstract int getExperienceAmount();

    @Shadow private int amount;

    @Inject(method = "onPlayerCollision", at = @At(value = "HEAD"), cancellable = true)
    private void injected(PlayerEntity player, CallbackInfo ci) {
        if (player != null) {
            var capsules = this.getExperienceCapsules(player);

            if (!capsules.isEmpty()) {
                for (var stack : capsules) {
                    int remaining = ExperienceCapsuleUtils.addExperienceToCapsule(stack, this.getExperienceAmount());

                    this.amount = remaining;

                    if (remaining == 0) {
                        ci.cancel();
                        return;
                    }
                }
            }
        }
    }

    private List<ItemStack> getExperienceCapsules(PlayerEntity player) {
        var items = new ArrayList<ItemStack>();

        var stack = player.getOffHandStack();
        if (stack.getItem() instanceof ExperienceCapsuleItem)
            items.add(stack);

        player.getInventory().main
                .stream()
                .filter(s -> s.getItem() instanceof ExperienceCapsuleItem)
                .forEach(items::add);

        return items;
    }
}
