package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.forge.common.ForgeHooks;
import com.alex.mysticalagriculture.forge.common.extensions.ForgeItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    @Shadow
    protected ServerWorld world;

    @Shadow
    @Final
    protected ServerPlayerEntity player;

    @Shadow
    private GameMode gameMode;

    @Inject(method = "tryBreakBlock", at = @At(value = "HEAD"))
    private void inject(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        ForgeHooks.onBlockBreakEvent(world, gameMode, player, pos);
    }

    @Inject(method = "tryBreakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isBlockBreakingRestricted(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/GameMode;)Z"), cancellable = true)
    private void inject2(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        var stack = player.getMainHandStack();
        if (stack.getItem() instanceof ForgeItem item)
            if (item.onBlockStartBreak(stack, pos, player)) cir.setReturnValue(false);
    }
}
