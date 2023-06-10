package com.alex.mysticalagriculture.mixin;

import com.alex.cucumber.forge.client.ForgeHooksClient;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {

    @Inject(at = @At("TAIL"), method = "getFieldOfViewModifier", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void inject(CallbackInfoReturnable<Float> cir, float f) {
        cir.setReturnValue(ForgeHooksClient.getFieldOfViewModifier((Player) ((Object) this), f));
    }
}
