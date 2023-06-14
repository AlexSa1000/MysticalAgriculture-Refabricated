package com.alex.mysticalagriculture.client.blockentity;

import com.alex.mysticalagriculture.blockentities.TinkeringTableBlockEntity;
import com.alex.mysticalagriculture.blocks.TinkeringTableBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;

public class TinkeringTableRenderer implements BlockEntityRenderer<TinkeringTableBlockEntity> {
    public TinkeringTableRenderer(BlockEntityRendererProvider.Context context) { }

    @Override
    public void render(TinkeringTableBlockEntity entity, float v, PoseStack matrix, MultiBufferSource buffer, int i, int i1) {
        var level = entity.getLevel();
        if (level == null)
            return;

        var pos = entity.getBlockPos();
        var state = level.getBlockState(pos);
        var stack = entity.getInventory().getItem(0);

        if (!stack.isEmpty() && state.hasProperty(TinkeringTableBlock.FACING)) {
            matrix.pushPose();
            matrix.translate(0.5D, 0.9D, 0.5D);
            float scale = 0.7F;
            matrix.scale(scale, scale, scale);
            int index = state.getValue(TinkeringTableBlock.FACING).get2DDataValue();
            matrix.mulPose(Axis.YP.rotationDegrees(-90 * index));
            matrix.mulPose(Axis.XP.rotationDegrees(90));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, i, i1, matrix, buffer, level, 0);
            matrix.popPose();
        }
    }
}
