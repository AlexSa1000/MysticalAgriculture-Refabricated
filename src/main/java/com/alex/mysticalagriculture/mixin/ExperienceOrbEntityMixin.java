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

import java.util.List;
import java.util.stream.Collectors;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {

    @Shadow public abstract int getExperienceAmount();

    @Shadow private int amount;

    @Inject(method = "onPlayerCollision", at = @At(value = "HEAD"), cancellable = true)
    private void injected(PlayerEntity player, CallbackInfo ci) {
        if (player != null) {
            List<ItemStack> capsules = this.getExperienceCapsules(player);
            if (!capsules.isEmpty()) {
                for (ItemStack stack : capsules) {
                    int remaining = ExperienceCapsuleUtils.addExperienceToCapsule(stack, this.getExperienceAmount());
                    if (remaining == 0) {
                        this.amount = 0;
                        return;
                    }
                    this.amount = remaining;
                }
            }
        }
    }

    private List<ItemStack> getExperienceCapsules(PlayerEntity player) {
        return player.inventory.main.stream()
                .filter(s -> s.getItem() instanceof ExperienceCapsuleItem)
                .collect(Collectors.toList());
    }
}
