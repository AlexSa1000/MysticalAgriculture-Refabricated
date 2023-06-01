package com.alex.mysticalagriculture.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
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

    @Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    private static final TagKey<Item> CROSSBOWS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "crossbows"));

    @Inject(at=@At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1), method = "renderFirstPersonItem", cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    public void inject(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci, boolean bl, Arm arm) {
        boolean bl2;
        float f;
        float g;
        float h;
        float j;
        if (item.isIn(CROSSBOWS)) {
            bl2 = CrossbowItem.isCharged(item);
            boolean bl3 = arm == Arm.RIGHT;
            int i = bl3 ? 1 : -1;
            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                this.applyEquipOffset(matrices, arm, equipProgress);
                matrices.translate((double)((float)i * -0.4785682F), -0.0943870022892952, 0.05731530860066414);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-11.935F));
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)i * 65.3F));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float)i * -9.785F));
                f = (float)item.getMaxUseTime() - ((float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0F);
                g = f / (float)CrossbowItem.getPullTime(item);
                if (g > 1.0F) {
                    g = 1.0F;
                }

                if (g > 0.1F) {
                    h = MathHelper.sin((f - 0.1F) * 1.3F);
                    j = g - 0.1F;
                    float k = h * j;
                    matrices.translate((double)(k * 0.0F), (double)(k * 0.004F), (double)(k * 0.0F));
                }

                matrices.translate((double)(g * 0.0F), (double)(g * 0.0F), (double)(g * 0.04F));
                matrices.scale(1.0F, 1.0F, 1.0F + g * 0.2F);
                matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion((float)i * 45.0F));
            } else {
                f = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                g = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 6.2831855F);
                h = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
                matrices.translate((double)((float)i * f), (double)g, (double)h);
                this.applyEquipOffset(matrices, arm, equipProgress);
                this.applySwingOffset(matrices, arm, swingProgress);
                if (bl2 && swingProgress < 0.001F && bl) {
                    matrices.translate((double)((float)i * -0.641864F), 0.0, 0.0);
                    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)i * 10.0F));
                }
            }

            this.renderItem(player, item, bl3 ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, !bl3, matrices, vertexConsumers, light);
            ci.cancel();
        }
    }
}
