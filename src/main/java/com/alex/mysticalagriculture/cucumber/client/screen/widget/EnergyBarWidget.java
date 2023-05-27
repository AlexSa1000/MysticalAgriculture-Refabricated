package com.alex.mysticalagriculture.cucumber.client.screen.widget;

import com.alex.mysticalagriculture.cucumber.util.Formatting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import team.reborn.energy.api.EnergyStorage;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class EnergyBarWidget extends ClickableWidget {
    private static final Identifier WIDGETS_TEXTURE = new Identifier(MOD_ID, "textures/gui/widgets.png");
    private final EnergyStorage energy;
    private final HandledScreen<?> screen;

    public EnergyBarWidget(int x, int y, EnergyStorage energy, HandledScreen<?> screen) {
        super(x, y, 14, 78, Text.literal("Energy Bar"));
        this.energy = energy;
        this.screen = screen;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        int offset = this.getEnergyBarOffset();

        drawTexture(matrices, this.getX(), this.getY(), 0, 0, this.width, this.height);
        drawTexture(matrices, this.getX(), this.getY() + this.height - offset, 14, this.height - offset, this.width, offset + 1);
        if (mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height) {
            var text = Text.literal(Formatting.number(this.energy.getAmount()).getString() + " / " + Formatting.energy(this.energy.getCapacity()).getString());

            this.screen.renderTooltip(matrices, text, mouseX, mouseY);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    private int getEnergyBarOffset() {
        long i = this.energy.getAmount();
        long j = this.energy.getCapacity();
        return (int)(j != 0 && i != 0 ? (long)i * (long)this.height / (long)j : 0L);
    }
}
