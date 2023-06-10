package com.alex.mysticalagriculture.mixin;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

@Mixin(MushroomCow.class)
public abstract class MushroomCowMixin {

    @Shadow public abstract boolean readyForShearing();

    @Shadow public abstract void shear(SoundSource soundSource);

    private static final TagKey<Item> SHEARS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MOD_ID, "shears"));

    @Inject(method = "mobInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 1),  cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void inject(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir, ItemStack itemStack) {
        if (itemStack.is(SHEARS) && this.readyForShearing()) {
            this.shear(SoundSource.PLAYERS);
            ((Entity) ((Object) this)).gameEvent(GameEvent.SHEAR, player);
            if (!((Entity) ((Object) this)).level.isClientSide) {
                itemStack.hurtAndBreak(1, player, (playerx) -> {
                    playerx.broadcastBreakEvent(interactionHand);
                });
            }
            cir.setReturnValue(InteractionResult.sidedSuccess(((Entity) ((Object) this)).level.isClientSide));
        }
    }
}
