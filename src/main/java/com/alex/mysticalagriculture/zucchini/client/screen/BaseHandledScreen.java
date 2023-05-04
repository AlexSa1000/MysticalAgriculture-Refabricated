package com.alex.mysticalagriculture.zucchini.client.screen;

import com.alex.mysticalagriculture.zucchini.util.Localizable;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.text.NumberFormat;

public class BaseHandledScreen<T extends ScreenHandler> extends HandledScreen<T> {
    protected Identifier bgTexture;
    protected int bgWidth;
    protected int bgHeight;

    public BaseHandledScreen(T container, PlayerInventory inventory, Text title, Identifier bgTexture, int xSize, int ySize) {
        this(container, inventory, title, bgTexture, xSize, ySize, 256, 256);
    }

    public BaseHandledScreen(T container, PlayerInventory inventory, Text title, Identifier bgTexture, int xSize, int ySize, int bgWidth, int bgHeight) {
        super(container, inventory, title);
        this.backgroundWidth = xSize;
        this.backgroundHeight = ySize;
        this.bgTexture = bgTexture;
        this.bgWidth = bgWidth;
        this.bgHeight = bgHeight;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.bgTexture);
        int x = this.x;
        int y = this.y;
        drawTexture(matrices, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, this.bgWidth, this.bgHeight);
    }

    protected String text(String key, Object... args) {
        return Localizable.of(key).args(args).buildString();
    }

    protected static String number(Object number) {
        return NumberFormat.getInstance().format(number);
    }
}
