package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.cucumber.item.tool.BaseSickleItem;
import com.alex.mysticalagriculture.forge.common.extensions.ForgeItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "breakBlock", at = @At(value = "HEAD"), cancellable = true)
    private void inject(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        var stack = client.player.getMainHandStack();
        if (stack.getItem() instanceof ForgeItem item)
            if (item.onBlockStartBreak(stack, pos, client.player)) cir.setReturnValue(false);
    }
}
