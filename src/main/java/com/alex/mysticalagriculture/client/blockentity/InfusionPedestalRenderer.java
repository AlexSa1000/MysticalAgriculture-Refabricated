package com.alex.mysticalagriculture.client.blockentity;

import com.alex.mysticalagriculture.blockentities.InfusionPedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;

public class InfusionPedestalRenderer implements BlockEntityRenderer<InfusionPedestalBlockEntity> {
    public InfusionPedestalRenderer(BlockEntityRendererProvider.Context context) { }

    @Override
    public void render(InfusionPedestalBlockEntity entity, float v, PoseStack matrix, MultiBufferSource buffer, int i, int i1) {
        var stack = entity.getInventory().getItem(0);
        var minecraft = Minecraft.getInstance();

        if (!stack.isEmpty()) {
            matrix.pushPose();
            matrix.translate(0.5D, 1.2D, 0.5D);
            float scale = stack.getItem() instanceof BlockItem ? 0.95F : 0.75F;
            matrix.scale(scale, scale, scale);
            double tick = System.currentTimeMillis() / 800.0D;
            matrix.translate(0.0D, Math.sin(tick % (2 * Math.PI)) * 0.065D, 0.0D);
            matrix.mulPose(Axis.YP.rotationDegrees((float) ((tick * 40.0D) % 360)));
            minecraft.getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, i, i1, matrix, buffer, minecraft.level, 0);
            matrix.popPose();
        }
    }
}
