package com.alex.mysticalagriculture.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
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
    @Shadow protected abstract void applyItemArmTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f);

    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract void applyItemArmAttackTransform(PoseStack poseStack, HumanoidArm humanoidArm, float f);

    @Shadow public abstract void renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i);

    private static final TagKey<Item> CROSSBOWS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MOD_ID, "crossbows"));

    @Inject(at=@At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 1), method = "renderArmWithItem", cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    public void inject(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, CallbackInfo ci, boolean bl, HumanoidArm humanoidArm) {
        boolean bl2;
        float l;
        float m;
        float n;
        float o;
        if (itemStack.is(CROSSBOWS)) {
            bl2 = CrossbowItem.isCharged(itemStack);
            boolean bl3 = humanoidArm == HumanoidArm.RIGHT;
            int k = bl3 ? 1 : -1;
            if (abstractClientPlayer.isUsingItem() && abstractClientPlayer.getUseItemRemainingTicks() > 0 && abstractClientPlayer.getUsedItemHand() == interactionHand) {
                this.applyItemArmTransform(poseStack, humanoidArm, i);
                poseStack.translate((double) ((float) k * -0.4785682F), -0.0943870022892952, 0.05731530860066414);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(-11.935F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees((float) k * 65.3F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees((float) k * -9.785F));
                l = (float) itemStack.getUseDuration() - ((float) this.minecraft.player.getUseItemRemainingTicks() - f + 1.0F);
                m = l / (float) CrossbowItem.getChargeDuration(itemStack);
                if (m > 1.0F) {
                    m = 1.0F;
                }

                if (m > 0.1F) {
                    n = Mth.sin((l - 0.1F) * 1.3F);
                    o = m - 0.1F;
                    float p = n * o;
                    poseStack.translate((double) (p * 0.0F), (double) (p * 0.004F), (double) (p * 0.0F));
                }

                poseStack.translate((double) (m * 0.0F), (double) (m * 0.0F), (double) (m * 0.04F));
                poseStack.scale(1.0F, 1.0F, 1.0F + m * 0.2F);
                poseStack.mulPose(Vector3f.YN.rotationDegrees((float) k * 45.0F));
            } else {
                l = -0.4F * Mth.sin(Mth.sqrt(h) * 3.1415927F);
                m = 0.2F * Mth.sin(Mth.sqrt(h) * 6.2831855F);
                n = -0.2F * Mth.sin(h * 3.1415927F);
                poseStack.translate((double) ((float) k * l), (double) m, (double) n);
                this.applyItemArmTransform(poseStack, humanoidArm, i);
                this.applyItemArmAttackTransform(poseStack, humanoidArm, h);
                if (bl2 && h < 0.001F && bl) {
                    poseStack.translate((double) ((float) k * -0.641864F), 0.0, 0.0);
                    poseStack.mulPose(Vector3f.YP.rotationDegrees((float) k * 10.0F));
                }
            }

            this.renderItem(abstractClientPlayer, itemStack, bl3 ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !bl3, poseStack, multiBufferSource, j);
            ci.cancel();
        }
    }
}
