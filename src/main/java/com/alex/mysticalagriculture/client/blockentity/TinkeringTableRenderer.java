package com.alex.mysticalagriculture.client.blockentity;

import com.alex.mysticalagriculture.blockentities.TinkeringTableBlockEntity;
import com.alex.mysticalagriculture.blocks.TinkeringTableBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.Vec3f;

public class TinkeringTableRenderer implements BlockEntityRenderer<TinkeringTableBlockEntity> {
    public TinkeringTableRenderer(BlockEntityRendererFactory.Context context) { }

    @Override
    public void render(TinkeringTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var world = entity.getWorld();
        if (world == null)
            return;

        var pos = entity.getPos();
        var state = world.getBlockState(pos);
        var stack = entity.getInventory().getStack(0);

        if (!stack.isEmpty() && state.contains(TinkeringTableBlock.FACING)) {
            matrices.push();
            matrices.translate(0.5D, 0.9D, 0.5D);
            float scale = 0.7F;
            matrices.scale(scale, scale, scale);
            int index = state.get(TinkeringTableBlock.FACING).getHorizontal();
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90 * index));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
