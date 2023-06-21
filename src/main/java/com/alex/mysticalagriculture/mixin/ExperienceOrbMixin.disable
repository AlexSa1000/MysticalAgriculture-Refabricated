package com.alex.mysticalagriculture.mixin;

import com.alex.cucumber.forge.event.entity.player.PlayerXpEvent;
import com.alex.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.alex.mysticalagriculture.handler.ExperienceCapsuleHandler;
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
    @Inject(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;take(Lnet/minecraft/world/entity/Entity;I)V", shift = At.Shift.BY, by = -1), cancellable = true)
    private void injected(Player player, CallbackInfo ci) {
        if (ExperienceCapsuleHandler.onPlayerPickupXp(new PlayerXpEvent.PickupXp(player, (ExperienceOrb) ((Object) this)))) ci.cancel();
    }
}
