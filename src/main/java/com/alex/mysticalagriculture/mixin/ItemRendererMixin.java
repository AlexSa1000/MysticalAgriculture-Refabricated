package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.items.InfusionCrystalItem;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At("HEAD"), argsOnly = true)
    private ItemStack disableDurabilityBar(ItemStack stack) {
        if (stack.getItem() instanceof InfusionCrystalItem) {
            ItemStack stack1 = stack.copy();
            stack1.getOrCreateTag().putBoolean("Unbreakable", true);
            return stack1;
        }
        return stack;
    }
}
