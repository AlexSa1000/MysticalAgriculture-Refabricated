package com.alex.mysticalagriculture.client.screen;

import com.alex.mysticalagriculture.container.TinkeringTableContainer;
import com.alex.mysticalagriculture.util.BaseHandledScreen;
import com.alex.mysticalagriculture.util.iface.ToggleableSlot;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class TinkeringTableScreen extends BaseHandledScreen<TinkeringTableContainer> {
    private static final Identifier BACKGROUND = new Identifier(MOD_ID, "textures/gui/tinkering_table.png");

    public TinkeringTableScreen(TinkeringTableContainer container, PlayerInventory inv, Text title) {
        super(container, inv, title, BACKGROUND, 176, 197);
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

        for (Slot slot : this.handler.slots) {
            if (slot.doDrawHoveringEffect() && slot instanceof ToggleableSlot) {
                this.drawTexture(matrices, x + slot.x, y + slot.y, 8, 115, 16, 16);
            }
        }
    }
}
