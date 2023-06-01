package com.alex.mysticalagriculture.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

@Mixin(FishingBobberEntityRenderer.class)
public class FishingBobberEntityRendererMixin {

    private static final TagKey<Item> FISHING_RODS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "fishing_rods"));

    private PlayerEntity playerEntity;

    @Inject(
            method = "render(Lnet/minecraft/entity/projectile/FishingBobberEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At("HEAD")
    )
    private void storeContext(FishingBobberEntity fishingBobberEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        playerEntity = fishingBobberEntity.getPlayerOwner();
    }

    @ModifyVariable(
            method = "render(Lnet/minecraft/entity/projectile/FishingBobberEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getHandSwingProgress(F)F"),
            index = 12)
    private int inject1(int j) {
        ItemStack itemStack = playerEntity.getMainHandStack();

        if (!itemStack.isOf(Items.FISHING_ROD)) {
            if (itemStack.isIn(FISHING_RODS)) {
                return -j;
            }
        }
        return j;
    }
}
