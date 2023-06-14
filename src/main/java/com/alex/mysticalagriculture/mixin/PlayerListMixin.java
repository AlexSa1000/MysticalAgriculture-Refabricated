package com.alex.mysticalagriculture.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.alex.mysticalagriculture.handler.AugmentHandler.ABILITY_CACHE;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(method = "remove", at = @At(value = "HEAD"))
    private void onPlayerLoggedOut(ServerPlayer player, CallbackInfo ci) {
        ABILITY_CACHE.getCachedAbilities(player).forEach(c -> {
            ABILITY_CACHE.removeQuietly(c, player);
        });
    }
}
