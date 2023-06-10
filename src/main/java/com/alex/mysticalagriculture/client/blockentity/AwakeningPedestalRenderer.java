package com.alex.mysticalagriculture.client.blockentity;

import com.alex.mysticalagriculture.blockentities.AwakeningPedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;

public class AwakeningPedestalRenderer implements BlockEntityRenderer<AwakeningPedestalBlockEntity> {
    public AwakeningPedestalRenderer(BlockEntityRendererProvider.Context context) { }

    @Override
    public void render(AwakeningPedestalBlockEntity tile, float v, PoseStack matrix, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        var stack = tile.getInventory().getItem(0);

        if (!stack.isEmpty()) {
            matrix.pushPose();
            matrix.translate(0.5D, 1.2D, 0.5D);
            float scale = stack.getItem() instanceof BlockItem ? 0.95F : 0.75F;
            matrix.scale(scale, scale, scale);
            double tick = System.currentTimeMillis() / 800.0D;
            matrix.translate(0.0D, Math.sin(tick % (2 * Math.PI)) * 0.065D, 0.0D);
            matrix.mulPose(Vector3f.YP.rotationDegrees((float) ((tick * 40.0D) % 360)));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, combinedLight, combinedOverlay, matrix, buffer, 0);
            matrix.popPose();
        }
    }
}
