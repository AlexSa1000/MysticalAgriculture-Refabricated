package com.alex.mysticalagriculture.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow protected abstract void applyItemArmAttackTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f);

    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract void applyItemArmTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f);

    @Shadow public abstract void renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i);

    private static final TagKey<Item> CROSSBOWS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "crossbows"));

    @Inject(at=@At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 1), method = "renderArmWithItem", cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    public void inject(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, CallbackInfo ci, boolean bl, HumanoidArm humanoidArm) {
        if (itemStack.is(CROSSBOWS)) {
            int k;
            boolean bl2 = CrossbowItem.isCharged(itemStack);
            boolean bl3 = humanoidArm == HumanoidArm.RIGHT;
            int n = k = bl3 ? 1 : -1;
            if (abstractClientPlayer.isUsingItem() && abstractClientPlayer.getUseItemRemainingTicks() > 0 && abstractClientPlayer.getUsedItemHand() == interactionHand) {
                this.applyItemArmAttackTransform(poseStack, humanoidArm, i);
                poseStack.translate((float)k * -0.4785682f, -0.094387f, 0.05731531f);
                poseStack.mulPose(Axis.XP.rotationDegrees(-11.935f));
                poseStack.mulPose(Axis.YP.rotationDegrees((float)k * 65.3f));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float)k * -9.785f));
                float l = (float)itemStack.getUseDuration() - ((float)this.minecraft.player.getUseItemRemainingTicks() - f + 1.0f);
                float m = l / (float)CrossbowItem.getChargeDuration(itemStack);
                if (m > 1.0f) {
                    m = 1.0f;
                }
                if (m > 0.1f) {
                    float n2 = Mth.sin((l - 0.1f) * 1.3f);
                    float o = m - 0.1f;
                    float p = n2 * o;
                    poseStack.translate(p * 0.0f, p * 0.004f, p * 0.0f);
                }
                poseStack.translate(m * 0.0f, m * 0.0f, m * 0.04f);
                poseStack.scale(1.0f, 1.0f, 1.0f + m * 0.2f);
                poseStack.mulPose(Axis.YN.rotationDegrees((float)k * 45.0f));
            } else {
                float l = -0.4f * Mth.sin(Mth.sqrt(h) * (float)Math.PI);
                float m = 0.2f * Mth.sin(Mth.sqrt(h) * ((float)Math.PI * 2));
                float n3 = -0.2f * Mth.sin(h * (float)Math.PI);
                poseStack.translate((float)k * l, m, n3);
                this.applyItemArmTransform(poseStack, humanoidArm, i);
                this.applyItemArmAttackTransform(poseStack, humanoidArm, h);
                if (bl2 && h < 0.001f && bl) {
                    poseStack.translate((float)k * -0.641864f, 0.0f, 0.0f);
                    poseStack.mulPose(Axis.YP.rotationDegrees((float)k * 10.0f));
                }
            }
            this.renderItem(abstractClientPlayer, itemStack, bl3 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !bl3, poseStack, multiBufferSource, j);
        }
    }
}
