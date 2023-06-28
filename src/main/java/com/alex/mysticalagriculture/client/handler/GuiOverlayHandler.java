package com.alex.mysticalagriculture.client.handler;

import com.alex.mysticalagriculture.blockentities.AwakeningAltarBlockEntity;
import com.alex.mysticalagriculture.blockentities.EssenceVesselBlockEntity;
import com.alex.mysticalagriculture.blockentities.InfusionAltarBlockEntity;
import com.alex.mysticalagriculture.crafting.recipe.AwakeningRecipe;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

public final class GuiOverlayHandler {
    public static void setAltarOverlay(GuiGraphics gfx, float partialTicks) {
        var mc = Minecraft.getInstance();

        if (mc.level == null)
            return;

        var level = mc.level;

        if (mc.hitResult instanceof BlockHitResult result) {
            var pos = result.getBlockPos();
            var tile = level.getBlockEntity(pos);
            var stack = ItemStack.EMPTY;

            if (tile instanceof InfusionAltarBlockEntity altar) {
                var recipe = altar.getActiveRecipe();
                if (recipe != null) {
                    stack = recipe.getResultItem(level.registryAccess());
                }
            }

            if (tile instanceof AwakeningAltarBlockEntity altar) {
                var recipe = altar.getActiveRecipe();
                if (recipe != null) {
                    stack = recipe.getResultItem(level.registryAccess());

                    drawEssenceRequirements(gfx, recipe, altar);
                }
            }

            if (!stack.isEmpty()) {
                int x = mc.getWindow().getGuiScaledWidth() / 2 - 11;
                int y = mc.getWindow().getGuiScaledHeight() / 2 - 8;

                gfx.renderItem(stack, x + 26, y);
                gfx.renderItemDecorations(mc.font, stack, x + 26, y);
                gfx.drawString(mc.font, stack.getHoverName(), x + 48, y + 5, 16383998);
            }
        }
    }

    public static void setEssenceVesselOverlay(GuiGraphics gfx, float partialTicks) {
        var mc = Minecraft.getInstance();

        if (mc.level == null)
            return;

        if (mc.hitResult instanceof BlockHitResult result) {
            var pos = result.getBlockPos();
            var tile = mc.level.getBlockEntity(pos);

            if (tile instanceof EssenceVesselBlockEntity vessel) {
                var stack = vessel.getInventory().getItem(0);

                if (!stack.isEmpty()) {
                    int x = mc.getWindow().getGuiScaledWidth() / 2 - 11;
                    int y = mc.getWindow().getGuiScaledHeight() / 2 - 8;

                    gfx.renderItem(stack, x + 26, y);
                    gfx.renderItemDecorations(mc.font, stack, x + 26, y);
                    gfx.drawString(mc.font, stack.getHoverName(), x + 48, y + 5, 16383998);
                }
            }
        }
    }

    private static void drawEssenceRequirements(GuiGraphics gfx, AwakeningRecipe recipe, AwakeningAltarBlockEntity altar) {
        var mc = Minecraft.getInstance();

        int x = mc.getWindow().getGuiScaledWidth() / 2 - 11;
        int y = mc.getWindow().getGuiScaledHeight() / 2 - 4;
        var lineHeight = mc.font.lineHeight + 6;

        var hasMissingEssences = false;
        var xOffset = 0;

        var missingEssences = recipe.getMissingEssences(altar.getEssenceItems());

        for (var essence : missingEssences.entrySet()) {
            gfx.renderItem(essence.getKey(), x + 26 + xOffset, y + 2 * lineHeight);
            gfx.drawString(mc.font, getEssenceDisplayName(essence.getKey(), essence.getValue()), x + 48 + xOffset, y + 5 + 2 * lineHeight, 16383998);
            xOffset += 56;
            hasMissingEssences = true;
        }

        if (hasMissingEssences) {
            gfx.drawString(mc.font, ModTooltips.MISSING_ESSENCES.build(), x + 28, y + 5 + lineHeight, 16383998);
        }
    }

    private static String getEssenceDisplayName(ItemStack stack, int missing) {
        var required = stack.getCount();
        return required - missing + "/" + required;
    }
}