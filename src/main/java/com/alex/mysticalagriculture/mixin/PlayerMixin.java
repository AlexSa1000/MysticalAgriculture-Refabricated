package com.alex.mysticalagriculture.mixin;

import com.alex.cucumber.forge.client.event.ForgeEventFactory;
import com.alex.cucumber.forge.common.ForgeHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Shadow @Final private Inventory inventory;

    @Inject(method = "attack", at = @At(value = "HEAD"), cancellable = true)
    private void inject(Entity target, CallbackInfo ci) {
        if (!ForgeHooks.onPlayerAttackTarget((Player) ((Object) this), target)) ci.cancel();
    }

    @Inject(method = "hasCorrectToolForDrops", at = @At(value = "HEAD"), cancellable = true)
    private void inject(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ForgeEventFactory.doPlayerHarvestCheck((Player) ((Object) this), state, !state.requiresCorrectToolForDrops() || this.inventory.getSelected().isCorrectToolForDrops(state)));
    }
}
