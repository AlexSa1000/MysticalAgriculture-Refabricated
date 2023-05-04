package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.lib.AbilityCache;
import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.mysticalagriculture.zucchini.helper.ColorHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;

public class SpeedAugment extends Augment {
    private final int amplifier;

    public SpeedAugment(Identifier id, int tier, int amplifier) {
        super(id, tier, EnumSet.of(AugmentType.LEGGINGS), getColor(0xAD524D, tier), getColor(0x240805, tier));
        this.amplifier = amplifier;
    }

    @Override
    public void onPlayerTick(World world, PlayerEntity player, AbilityCache cache) {
        var flying = player.getAbilities().flying;
        var swimming = player.isSwimming();
        var inWater = player.isTouchingWater();

        if (player.isOnGround() || flying || swimming || inWater) {
            var sneaking = player.isInSneakingPose();
            var sprinting = player.isSprinting();

            float speed = 0.1F
                    * (flying ? 0.6F : 1.0F)
                    * (sneaking ? 0.1F : 1.0F)
                    * (!sprinting ? 0.6F : 1.2F)
                    * (inWater ? 0.5F : 1.0F)
                    * (swimming ? 0.8F : 1.0F)
                    * this.amplifier;

            if (player.forwardSpeed > 0F) {
                player.updateVelocity(1F, new Vec3d(0F, 0F, speed));
            } else if (player.forwardSpeed < 0F) {
                player.updateVelocity(1F, new Vec3d(0F, 0F, -speed * 0.3F));
            }

            if (player.sidewaysSpeed != 0F) {
                player.updateVelocity(1F, new Vec3d(speed * 0.5F * Math.signum(player.sidewaysSpeed), 0F, 0F));
            }
        }
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 4, 1));
    }
}
