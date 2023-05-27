package com.alex.mysticalagriculture.forge.client.event;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.ApiStatus;

public class ComputeFovModifierEvent {
    private final PlayerEntity player;
    private final float fovModifier;
    private float newFovModifier;

    @ApiStatus.Internal
    public ComputeFovModifierEvent(PlayerEntity player, float fovModifier)
    {
        this.player = player;
        this.fovModifier = fovModifier;
        this.setNewFovModifier((float) MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue(), 1.0F, fovModifier));
    }

    /**
     * {@return the player affected by this event}
     */
    public PlayerEntity getPlayerEntity()
    {
        return player;
    }

    /**
     * {@return the original field of vision (FOV) of the player, before any modifications or interpolation}
     */
    public float getFovModifier()
    {
        return fovModifier;
    }

    /**
     * {@return the current field of vision (FOV) of the player}
     */
    public float getNewFovModifier()
    {
        return newFovModifier;
    }

    /**
     * Sets the new field of vision (FOV) of the player.
     *
     * @param newFovModifier the new field of vision (FOV)
     */
    public void setNewFovModifier(float newFovModifier)
    {
        this.newFovModifier = newFovModifier;
    }
}
