package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.forge.client.event.ForgeEventFactory;
import com.alex.mysticalagriculture.forge.common.ForgeHooks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Shadow @Final private PlayerInventory inventory;

    @Inject(method = "attack", at = @At(value = "HEAD"), cancellable = true)
    private void inject(Entity target, CallbackInfo ci) {
        if (!ForgeHooks.onPlayerAttackTarget((PlayerEntity) ((Object) this), target)) ci.cancel();
    }

    @Inject(method = "canHarvest", at = @At(value = "HEAD"), cancellable = true)
    private void inject(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ForgeEventFactory.doPlayerHarvestCheck((PlayerEntity) ((Object) this), state, !state.isToolRequired() || this.inventory.getMainHandStack().isSuitableFor(state)));
    }
}
