package com.alex.mysticalagriculture.augment;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentType;
import com.alex.mysticalagriculture.util.helper.ColorHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;
import java.util.List;

public class AttackAOEAugment extends Augment {
    private final int amplifier;

    public AttackAOEAugment(Identifier id, int tier, int amplifier) {
        super(id, tier, EnumSet.of(AugmentType.SWORD), getColor(0xFF0000, tier), getColor(0x700000, tier));
        this.amplifier = amplifier;
    }

    @Override
    public boolean onHitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                List<LivingEntity> entities = player.getEntityWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.5D * this.amplifier, 0.25D * this.amplifier, 1.5D * this.amplifier));

                for (LivingEntity aoeEntity : entities) {
                    if (aoeEntity != player && aoeEntity != target && !player.isTeammate(target)) {
                        aoeEntity.takeKnockback(0.4F, MathHelper.sin(player.yaw * 0.017453292F), -MathHelper.cos(player.yaw * 0.017453292F));
                        aoeEntity.damage(DamageSource.player(player), 13.0F); // TODO: 1.16: should this damage value be hardcoded?
                    }
                }

                player.getEntityWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
                player.spawnSweepAttackParticles();
            }

            return true;
        }

        return false;
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 5, 1));
    }
}
