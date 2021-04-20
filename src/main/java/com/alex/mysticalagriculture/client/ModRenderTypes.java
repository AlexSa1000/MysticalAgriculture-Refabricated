package com.alex.mysticalagriculture.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;


public class ModRenderTypes {

    public static final RenderLayer GHOST = RenderLayer.of("ghost",
            VertexFormats.POSITION_COLOR_TEXTURE,
            GL11.GL_QUADS, 256,
            RenderLayer.MultiPhaseParameters.builder()
                    .texture(new RenderPhase.Texture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, false, false))
                    .alpha(new RenderPhase.Alpha(0.5F) {
                        @Override
                        public void startDrawing() {
                            RenderSystem.pushMatrix();
                            RenderSystem.color4f(1F, 1F, 1F, 1F);
                            GlStateManager.enableBlend();
                            GL14.glBlendColor(1.0F, 1.0F, 1.0F, 0.25F);
                            GlStateManager.blendFunc(GlStateManager.SrcFactor.CONSTANT_ALPHA.field_22545, GlStateManager.DstFactor.ONE_MINUS_CONSTANT_ALPHA.field_22528);
                        }

                        @Override
                        public void endDrawing() {
                            GL14.glBlendColor(1.0F, 1.0F, 1.0F, 1.0F);
                            GlStateManager.blendFunc(GlStateManager.SrcFactor.CONSTANT_ALPHA.field_22545, GlStateManager.DstFactor.ONE_MINUS_CONSTANT_ALPHA.field_22528);
                            RenderSystem.disableBlend();
                            RenderSystem.popMatrix();
                        }
                    })
                    .build(false));
}
