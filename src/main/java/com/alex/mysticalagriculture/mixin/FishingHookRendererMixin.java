package com.alex.mysticalagriculture.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

@Mixin(FishingHookRenderer.class)
public class FishingHookRendererMixin {

    private static final TagKey<Item> FISHING_RODS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MOD_ID, "fishing_rods"));

    private Player player;

    @Inject(
            method = "render(Lnet/minecraft/world/entity/projectile/FishingHook;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD")
    )
    private void storeContext(FishingHook fishingHook, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        player = fishingHook.getPlayerOwner();
    }

    @ModifyVariable(
            method = "render(Lnet/minecraft/world/entity/projectile/FishingHook;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttackAnim(F)F"),
            index = 12)
    private int inject1(int j) {
        ItemStack itemStack = player.getMainHandItem();

        if (!itemStack.is(Items.FISHING_ROD)) {
            if (itemStack.is(FISHING_RODS)) {
                return -j;
            }
        }
        return j;
    }
}
