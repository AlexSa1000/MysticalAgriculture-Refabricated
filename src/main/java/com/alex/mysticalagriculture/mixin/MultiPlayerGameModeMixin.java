package com.alex.mysticalagriculture.mixin;

import com.alex.cucumber.forge.common.extensions.ForgeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "destroyBlock", at = @At(value = "HEAD"), cancellable = true)
    private void inject(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        var stack = minecraft.player.getMainHandItem();
        if (stack.getItem() instanceof ForgeItem item)
            if (item.onBlockStartBreak(stack, pos, minecraft.player)) cir.setReturnValue(false);
    }
}
