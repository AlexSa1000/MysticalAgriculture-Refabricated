package com.alex.mysticalagriculture.client.blockentity;

import com.alex.mysticalagriculture.blockentities.InfusionPedestalBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class InfusionPedestalRenderer extends BlockEntityRenderer<InfusionPedestalBlockEntity> {
    public InfusionPedestalRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(InfusionPedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getStack(0);

        if (!stack.isEmpty()) {
            matrices.push();
            matrices.translate(0.5D, 1.2D, 0.5D);
            float scale = stack.getItem() instanceof BlockItem ?  0.95F : 0.75F;
            matrices.scale(scale, scale, scale);
            double tick = System.currentTimeMillis() / 800.0D;
            matrices.translate(0.0D, Math.sin(tick % (2 * Math.PI)) * 0.065D, 0.0D);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float) ((tick * 40.0D) % 360)));
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.pop();
        }
    }
}
