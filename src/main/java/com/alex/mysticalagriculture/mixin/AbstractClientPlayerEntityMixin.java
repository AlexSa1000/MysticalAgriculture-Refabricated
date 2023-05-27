package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.forge.client.ForgeHooksClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {

    @Inject(at = @At("TAIL"), method = "getFovMultiplier", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void inject(CallbackInfoReturnable<Float> cir, float f) {
        cir.setReturnValue(ForgeHooksClient.getFieldOfViewModifier((PlayerEntity) ((Object) this), f));
    }
}
