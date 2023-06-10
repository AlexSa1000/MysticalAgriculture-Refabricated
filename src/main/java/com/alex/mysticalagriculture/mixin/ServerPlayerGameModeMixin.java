package com.alex.mysticalagriculture.mixin;

import com.alex.cucumber.forge.common.ForgeHooks;
import com.alex.cucumber.forge.common.extensions.ForgeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {


    @Shadow protected ServerLevel level;

    @Shadow private GameType gameModeForPlayer;

    @Shadow @Final protected ServerPlayer player;

    @Inject(method = "destroyBlock", at = @At(value = "HEAD"))
    private void inject(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        ForgeHooks.onBlockBreakEvent(level, gameModeForPlayer, player, pos);
    }

    @Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;blockActionRestricted(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/GameType;)Z"), cancellable = true)
    private void inject2(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        var stack = player.getMainHandItem();
        if (stack.getItem() instanceof ForgeItem item)
            if (item.onBlockStartBreak(stack, pos, player)) cir.setReturnValue(false);
    }
}
