package com.alex.mysticalagriculture.client.blockentity;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.crop.ICropProvider;
import com.alex.mysticalagriculture.blockentities.EssenceVesselBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class EssenceVesselRenderer implements BlockEntityRenderer<EssenceVesselBlockEntity> {
    private static final Identifier VESSEL_CONTENT_TEXTURE = new Identifier(MysticalAgriculture.MOD_ID, "block/essence_vessel_contents");

    public EssenceVesselRenderer(BlockEntityRendererFactory.Context context) { }
    
    @Override
    public void render(EssenceVesselBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var inventory = entity.getInventory();
        var stack = inventory.getStack(0);

        if (!stack.isEmpty() && stack.getItem() instanceof ICropProvider provider) {
            var builder = vertexConsumers.getBuffer(RenderLayer.getSolid());
            var sprite = MinecraftClient.getInstance()
                    .getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
                    .apply(VESSEL_CONTENT_TEXTURE);

            float filledAmount = 0.4f * ((float) stack.getCount() / (float) inventory.getSlotLimit(0));

            matrices.push();

            matrices.translate(.5, .5, .5);
            matrices.translate(-.5, -.5, -.5);

            var color = provider.getCrop().getEssenceColor();

            // top
            addVertex(builder, matrices, 0.2f, 0.75f + filledAmount, 0.8f, sprite.getMinU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.8f, 0.75f + filledAmount, 0.8f, sprite.getMaxU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.8f, 0.75f + filledAmount, 0.2f, sprite.getMaxU(), sprite.getMinV(), color, light);
            addVertex(builder, matrices, 0.2f, 0.75f + filledAmount, 0.2f, sprite.getMinU(), sprite.getMinV(), color, light);

            matrices.push();
            matrices.translate(0, 1, 1);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

            // bottom
            addVertex(builder, matrices, 0.2f, 0.25f, 0.8f, sprite.getMinU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.8f, 0.25f, 0.8f, sprite.getMaxU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.8f, 0.25f, 0.2f, sprite.getMaxU(), sprite.getMinV(), color, light);
            addVertex(builder, matrices, 0.2f, 0.25f, 0.2f, sprite.getMinU(), sprite.getMinV(), color, light);

            matrices.pop();

            matrices.push();

            matrices.translate(1.2, 0.55, 0);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));

            // west
            addVertex(builder, matrices, 0.2f, 1, 0.8f, sprite.getMinU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.2f + filledAmount, 1, 0.8f, sprite.getMaxU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.2f + filledAmount, 1, 0.2f, sprite.getMaxU(), sprite.getMinV(), color, light);
            addVertex(builder, matrices, 0.2f, 1, 0.2f, sprite.getMinU(), sprite.getMinV(), color, light);

            matrices.pop();
            matrices.push();

            matrices.translate(-0.2, 0.55, 1);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(270));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));

            // east
            addVertex(builder, matrices, 0.2f, 1, 0.8f, sprite.getMinU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.2f + filledAmount, 1, 0.8f, sprite.getMaxU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.2f + filledAmount, 1, 0.2f, sprite.getMaxU(), sprite.getMinV(), color, light);
            addVertex(builder, matrices, 0.2f, 1, 0.2f, sprite.getMinU(), sprite.getMinV(), color, light);

            matrices.pop();
            matrices.push();

            matrices.translate(1, 0.55, -0.2);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));

            // south
            addVertex(builder, matrices, 0.2f, 1, 0.2f + filledAmount, sprite.getMinU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.8f, 1, 0.2f + filledAmount, sprite.getMaxU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.8f, 1, 0.2f, sprite.getMaxU(), sprite.getMinV(), color, light);
            addVertex(builder, matrices, 0.2f, 1, 0.2f, sprite.getMinU(), sprite.getMinV(), color, light);

            matrices.pop();
            matrices.push();

            matrices.translate(0, 0.55, 1.2);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));

            // north
            addVertex(builder, matrices, 0.2f, 1, 0.2f + filledAmount, sprite.getMinU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.8f, 1, 0.2f + filledAmount, sprite.getMaxU(), sprite.getMaxV(), color, light);
            addVertex(builder, matrices, 0.8f, 1, 0.2f, sprite.getMaxU(), sprite.getMinV(), color, light);
            addVertex(builder, matrices, 0.2f, 1, 0.2f, sprite.getMinU(), sprite.getMinV(), color, light);

            matrices.pop();

            matrices.pop();

            sprite.getContents().close();
        }
    }

    private static void addVertex(VertexConsumer renderer, MatrixStack stack, float x, float y, float z, float u, float v, int color, int light) {
        renderer.vertex(stack.peek().getPositionMatrix(), x, y, z)
                .color(color)
                .texture(u, v)
                .light(light)
                .normal(1, 0, 0)
                .next();
    }
}
