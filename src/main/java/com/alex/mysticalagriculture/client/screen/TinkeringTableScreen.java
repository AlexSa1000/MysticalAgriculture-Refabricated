package com.alex.mysticalagriculture.client.screen;

import com.alex.mysticalagriculture.container.TinkeringTableContainer;
import com.alex.cucumber.client.screen.BaseContainerScreen;
import com.alex.cucumber.iface.ToggleableSlot;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class TinkeringTableScreen extends BaseContainerScreen<TinkeringTableContainer> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(MOD_ID, "textures/gui/tinkering_table.png");

    public TinkeringTableScreen(TinkeringTableContainer container, Inventory inv, Component title) {
        super(container, inv, title, BACKGROUND, 176, 197);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        var title = this.getTitle().getString();

        gfx.drawString(this.font, title, (this.imageWidth / 2 - this.font.width(title) / 2), 6, 4210752, false);
        gfx.drawString(this.font, this.playerInventoryTitle, 8, (this.imageHeight - 96 + 2), 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        this.renderDefaultBg(gfx, partialTicks, mouseX, mouseY);

        int x = this.leftPos;
        int y = this.topPos;

        for (var slot : this.menu.slots) {
            if (slot.isActive() && slot instanceof ToggleableSlot) {
                gfx.blit(BACKGROUND, x + slot.x, y + slot.y, 8, 115, 16, 16);
            }
        }
    }
}