package com.alex.mysticalagriculture.client.blockentity;

import com.alex.mysticalagriculture.blockentities.TinkeringTableBlockEntity;
import com.alex.mysticalagriculture.blocks.TinkeringTableBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TinkeringTableRenderer extends BlockEntityRenderer<TinkeringTableBlockEntity> {
    public TinkeringTableRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(TinkeringTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        if (world == null)
            return;

        BlockPos pos = entity.getPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = entity.getStack(0);

        if (!stack.isEmpty()) {
            matrices.push();
            matrices.translate(0.5D, 0.9D, 0.5D);
            float scale = 0.7F;
            matrices.scale(scale, scale, scale);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
            int index = state.get(TinkeringTableBlock.FACING).getHorizontal();
            if (index == 0) {
                matrices.multiply(Vector3f.NEGATIVE_Z.getDegreesQuaternion(270));
                matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(90));
            } else if (index == 1) {
                matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(90));
            } else if (index == 2) {
                matrices.multiply(Vector3f.NEGATIVE_Z.getDegreesQuaternion(90));
                matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(270));
            } else if (index == 3) {
                matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(270));
            }

            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers);
            matrices.pop();
        }
    }
}
