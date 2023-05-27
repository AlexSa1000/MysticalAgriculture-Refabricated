package com.alex.mysticalagriculture.mixin;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.alex.mysticalagriculture.handler.AugmentHandler.ABILITY_CACHE;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(method = "remove", at = @At(value = "HEAD"))
    private void onPlayerLoggedOut(ServerPlayerEntity player, CallbackInfo ci) {
        ABILITY_CACHE.getCachedAbilities(player).forEach(c -> {
            ABILITY_CACHE.removeQuietly(c, player);
        });
    }
}
