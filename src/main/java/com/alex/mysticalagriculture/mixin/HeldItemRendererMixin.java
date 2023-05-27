package com.alex.mysticalagriculture.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Shadow protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);

    @Shadow protected abstract void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress);

    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    private static final TagKey<Item> CROSSBOWS = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "crossbows"));

    @Inject(at=@At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1), method = "renderFirstPersonItem", cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    public void inject(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci, boolean bl, Arm arm) {

        if (item.isIn(CROSSBOWS)) {
            int i;
            boolean bl2 = CrossbowItem.isCharged(item);
            boolean bl3 = arm == Arm.RIGHT;
            int n = i = bl3 ? 1 : -1;
            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                this.applyEquipOffset(matrices, arm, equipProgress);
                matrices.translate((float)i * -0.4785682f, -0.094387f, 0.05731531f);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-11.935f));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)i * 65.3f));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)i * -9.785f));
                float f = (float)item.getMaxUseTime() - ((float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0f);
                float g = f / (float)CrossbowItem.getPullTime(item);
                if (g > 1.0f) {
                    g = 1.0f;
                }
                if (g > 0.1f) {
                    float h = MathHelper.sin((f - 0.1f) * 1.3f);
                    float j = g - 0.1f;
                    float k = h * j;
                    matrices.translate(k * 0.0f, k * 0.004f, k * 0.0f);
                }
                matrices.translate(g * 0.0f, g * 0.0f, g * 0.04f);
                matrices.scale(1.0f, 1.0f, 1.0f + g * 0.2f);
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)i * 45.0f));
            } else {
                float f = -0.4f * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float)Math.PI);
                float g = 0.2f * MathHelper.sin(MathHelper.sqrt(swingProgress) * ((float)Math.PI * 2));
                float h = -0.2f * MathHelper.sin(swingProgress * (float)Math.PI);
                matrices.translate((float)i * f, g, h);
                this.applyEquipOffset(matrices, arm, equipProgress);
                this.applySwingOffset(matrices, arm, swingProgress);
                if (bl2 && swingProgress < 0.001f && bl) {
                    matrices.translate((float)i * -0.641864f, 0.0f, 0.0f);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)i * 10.0f));
                }
            }
            this.renderItem(player, item, bl3 ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND, !bl3, matrices, vertexConsumers, light);
            ci.cancel();
        }
    }
}
