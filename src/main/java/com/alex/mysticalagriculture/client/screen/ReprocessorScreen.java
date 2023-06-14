package com.alex.mysticalagriculture.client.screen;

import com.alex.mysticalagriculture.blockentities.ReprocessorBlockEntity;
import com.alex.mysticalagriculture.container.ReprocessorContainer;
import com.alex.cucumber.client.screen.BaseContainerScreen;
import com.alex.cucumber.client.screen.widget.EnergyBarWidget;
import com.alex.cucumber.util.Formatting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ReprocessorScreen extends BaseContainerScreen<ReprocessorContainer> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(MOD_ID, "textures/gui/reprocessor.png");
    private ReprocessorBlockEntity block;

    public ReprocessorScreen(ReprocessorContainer container, Inventory inv, Component title) {
        super(container, inv, title, BACKGROUND, 176, 194);
    }

    @Override
    protected void init() {
        super.init();

        int x = this.leftPos;
        int y = this.topPos;

        this.block = this.getBlockEntity();

        if (this.block != null) {
            this.addRenderableWidget(new EnergyBarWidget(x + 7, y + 17, this.block.getEnergy(), this));
        }
    }

    @Override
    protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
        var title = this.getTitle().getString();

        this.font.draw(stack, title, (float) (this.imageWidth / 2 - this.font.width(title) / 2), 6.0F, 4210752);
        this.font.draw(stack, this.playerInventoryTitle, 8.0F, (float) (this.imageHeight - 96 + 2), 4210752);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        this.renderDefaultBg(stack, partialTicks, mouseX, mouseY);

        int x = this.leftPos;
        int y = this.topPos;

        if (this.getFuelItemValue() > 0) {
            int lol = this.getBurnLeftScaled(13);
            this.blit(stack, x + 31, y + 52 - lol, 176, 12 - lol, 14, lol + 1);
        }

        if (this.getProgress() > 0) {
            int i2 = this.getProgressScaled(24);
            this.blit(stack, x + 98, y + 51, 176, 14, i2 + 1, 16);
        }
    }

    @Override
    protected void renderTooltip(PoseStack stack, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        super.renderTooltip(stack, mouseX, mouseY);

        if (this.getFuelLeft() > 0 && mouseX > x + 30 && mouseX < x + 45 && mouseY > y + 39 && mouseY < y + 53) {
            var text = Component.literal(number(this.getFuelLeft()) + " FE");
            this.renderTooltip(stack, text, mouseX, mouseY);
        }
    }

    private ReprocessorBlockEntity getBlockEntity() {
        var level = this.minecraft.level;

        if (level != null) {
            var block = level.getBlockEntity(this.getMenu().getBlockPos());

            if (block instanceof ReprocessorBlockEntity reprocessor) {
                return reprocessor;
            }
        }

        return null;
    }

    public int getProgress() {
        if (this.block == null)
            return 0;

        return this.block.getProgress();
    }

    public int getOperationTime() {
        if (this.block == null)
            return 0;

        return this.block.getTier().getOperationTime();
    }

    public int getFuelLeft() {
        if (this.block == null)
            return 0;

        return this.block.getFuelLeft();
    }

    public int getFuelItemValue() {
        if (this.block == null)
            return 0;

        return this.block.getFuelItemValue();
    }

    public int getProgressScaled(int pixels) {
        int i = this.getProgress();
        int j = this.getOperationTime();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    public int getBurnLeftScaled(int pixels) {
        int i = this.getFuelLeft();
        int j = this.getFuelItemValue();
        return (int) (j != 0 && i != 0 ? (long) i * pixels / j : 0);
    }
}