package com.alex.mysticalagriculture.client.screen;

import com.alex.mysticalagriculture.container.ReprocessorContainer;
import com.alex.mysticalagriculture.util.BaseHandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ReprocessorScreen extends BaseHandledScreen<ReprocessorContainer> {
    private static final Identifier BACKGROUND = new Identifier(MOD_ID, "textures/gui/reprocessor.png");

    public ReprocessorScreen(ReprocessorContainer container, PlayerInventory inv, Text title) {
        super(container, inv, title, BACKGROUND, 176, 183);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        String title = this.getTitle().getString();
        this.textRenderer.draw(matrices, title, (float) (this.backgroundWidth / 2 - this.textRenderer.getWidth(title) / 2), 6.0F, 4210752);
        String inventory = this.playerInventory.getDisplayName().getString();
        this.textRenderer.draw(matrices, inventory, 8.0F, (float) (this.backgroundHeight - 96 + 2), 4210752);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);

        int x = this.x;
        int y = this.y;

        ReprocessorContainer container = this.getScreenHandler();
        int i1 = container.getFuelBarScaled(66);
        this.drawTexture(matrices, x + 20, y + 84 - i1, 176, 97 - i1, 15, i1);

        if (container.isFuelItemValuable()) {
            int lol = container.getBurnLeftScaled(13);
            this.drawTexture(matrices, x + 36, y + 33 + 12 - lol, 176, 12 - lol, 14, lol + 1);
        }

        if (container.isProgressing()) {
            int i2 = container.getCookProgressScaled(24);
            this.drawTexture(matrices, x + 98, y + 41, 176, 14, i2 + 1, 16);
        }
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack stack, int mouseX, int mouseY) {
        int x = this.x;
        int y = this.y;

        super.drawMouseoverTooltip(stack, mouseX, mouseY);

        ReprocessorContainer container = this.getScreenHandler();
        if (mouseX > x + 19 && mouseX < x + 29 && mouseY > y + 17 && mouseY < y + 84) {
            LiteralText text = new LiteralText(container.getFuel() + " / " + container.getFuelCapacity());
            this.renderTooltip(stack, text, mouseX, mouseY);
        }

        if (container.hasFuel()) {
            if (mouseX > x + 36 && mouseX < x + 50 && mouseY > y + 33 && mouseY < y + 47) {
                LiteralText text = new LiteralText(String.valueOf(container.getFuelLeft()));
                this.renderTooltip(stack, text, mouseX, mouseY);
            }
        }
    }
}
