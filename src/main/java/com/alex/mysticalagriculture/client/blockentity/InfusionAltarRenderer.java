package com.alex.mysticalagriculture.client.blockentity;

import com.alex.mysticalagriculture.blockentities.InfusionAltarBlockEntity;
import com.alex.cucumber.client.ModRenderTypes;
import com.alex.mysticalagriculture.init.ModBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;

public class InfusionAltarRenderer implements BlockEntityRenderer<InfusionAltarBlockEntity> {
    public InfusionAltarRenderer(BlockEntityRendererProvider.Context context) { }

    @Override
    public void render(InfusionAltarBlockEntity tile, float v, PoseStack matrix, MultiBufferSource buffer, int i, int i1) {
        var inventory = tile.getInventory();
        var minecraft = Minecraft.getInstance();
        var stack = inventory.getItem(1).isEmpty() ? inventory.getItem(0) : inventory.getItem(1);

        if (!stack.isEmpty()) {
            matrix.pushPose();
            matrix.translate(0.5D, 1.1D, 0.5D);
            float scale = stack.getItem() instanceof BlockItem ? 0.95F : 0.75F;
            matrix.scale(scale, scale, scale);
            double tick = System.currentTimeMillis() / 800.0D;
            matrix.translate(0.0D, Math.sin(tick % (2 * Math.PI)) * 0.065D, 0.0D);
            matrix.mulPose(Vector3f.YP.rotationDegrees((float) ((tick * 40.0D) % 360)));
            minecraft.getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, i, i1, matrix, buffer, 0);
            matrix.popPose();
        }

        var pos = tile.getBlockPos();
        var level = tile.getLevel();
        var builder = buffer.getBuffer(ModRenderTypes.GHOST);

        matrix.pushPose();
        matrix.translate(-pos.getX(), -pos.getY(), -pos.getZ());

        tile.getPedestalPositions().forEach(aoePos -> {
            if (level != null && level.isEmptyBlock(aoePos)) {
                matrix.pushPose();
                matrix.translate(aoePos.getX(), aoePos.getY(), aoePos.getZ());
                minecraft.getBlockRenderer().renderBatched(ModBlocks.INFUSION_PEDESTAL.defaultBlockState(), aoePos, level, matrix, builder, false, level.getRandom());
                matrix.popPose();
            }
        });

        matrix.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(InfusionAltarBlockEntity tile) {
        return true;
    }
}
