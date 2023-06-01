package com.alex.mysticalagriculture.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin {
    @Shadow public abstract boolean isShearable();

    @Shadow public abstract void sheared(SoundCategory shearedSoundCategory);

    private static final TagKey<Item> SHEARS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "shears"));

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void inject(PlayerEntity player2, Hand hand, CallbackInfoReturnable<ActionResult> cir, ItemStack itemStack) {
        if (itemStack.isIn(SHEARS) && this.isShearable()) {
            this.sheared(SoundCategory.PLAYERS);
            ((Entity) ((Object) this)).emitGameEvent(GameEvent.SHEAR, player2);
            if (!((Entity) ((Object) this)).world.isClient) {
                itemStack.damage(1, player2, player -> player.sendToolBreakStatus(hand));
            }
            cir.setReturnValue(ActionResult.success(((Entity) ((Object) this)).world.isClient));
        }
    }
}
