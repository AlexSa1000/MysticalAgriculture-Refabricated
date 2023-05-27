package com.alex.mysticalagriculture.forge.client;

import com.alex.mysticalagriculture.cucumber.iface.CustomBow;
import com.alex.mysticalagriculture.forge.client.event.ComputeFovModifierEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class ForgeHooksClient {
    public static float getFieldOfViewModifier(PlayerEntity entity, float fovModifier)
    {
        ComputeFovModifierEvent fovModifierEvent = new ComputeFovModifierEvent(entity, fovModifier);

        var stack = entity.getActiveItem();

        if (!stack.isEmpty()) {
            var item = stack.getItem();
            if (item instanceof CustomBow bow && bow.hasFOVChange()) {
                float f = MathHelper.clamp((stack.getMaxUseTime() - entity.getItemUseTimeLeft()) * bow.getDrawSpeedMulti(stack) / 20.0F, 0, 1.0F);

                fovModifierEvent.setNewFovModifier(fovModifierEvent.getNewFovModifier() - (f * f * 0.15F));
            }
        }

        return fovModifierEvent.getNewFovModifier();
    }


}
