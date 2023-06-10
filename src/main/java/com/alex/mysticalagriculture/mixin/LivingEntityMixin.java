package com.alex.mysticalagriculture.mixin;

import com.alex.cucumber.forge.common.ForgeHooks;
import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.handler.AugmentHandler;
import com.alex.mysticalagriculture.items.SoulJarItem;
import com.alex.mysticalagriculture.items.SouliumDaggerItem;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Collectors;

import static com.alex.mysticalagriculture.handler.AugmentHandler.ABILITY_CACHE;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void onPlayerUpdate(CallbackInfo ci) {
        if (((Object) this) instanceof Player player) {
            var world = player.getCommandSenderWorld();
            var augments = AugmentUtils.getArmorAugments(player);

            augments.forEach(a -> {
                a.onPlayerTick(world, player, ABILITY_CACHE);
            });

            ABILITY_CACHE.getCachedAbilities(player).forEach(c -> {
                if (augments.stream().noneMatch(a -> c.equals(a.getId().toString()))) {
                    ABILITY_CACHE.remove(c, player);
                }
            });
        }
    }

    public float[] ret;

    @Inject(method = "causeFallDamage", at = @At(value = "HEAD"), cancellable = true)
    private void onLivingFall(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        ret = AugmentHandler.onLivingFall((LivingEntity) ((Object) this), fallDistance, damageMultiplier);
        if (ret == null) cir.setReturnValue(false);
    }

    @ModifyVariable(method = "causeFallDamage", at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
    private float onLivingFall1(float value) {
        return ret[0];
    }

    @ModifyVariable(method = "causeFallDamage", at = @At(value = "HEAD"), ordinal = 1, argsOnly = true)
    private float onLivingFall2(float value) {
        return ret[1];
    }

    @Inject(method = "die", at = @At(value = "HEAD"))
    private void onLivingDrops(DamageSource source, CallbackInfo ci) {
        var entity = source.getEntity();

        if (entity instanceof Player player) {
            var held = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (held.getItem() instanceof SouliumDaggerItem siphoner) {
                var livingEntity = (LivingEntity) ((Object) this);
                var type = MobSoulTypeRegistry.getInstance().getMobSoulTypeByEntity(livingEntity);

                if (type == null || !type.isEnabled()) {
                    return;
                }

                var jars = getValidSoulJars(player, type);
                System.out.println(jars);

                if (!jars.isEmpty()) {
                    double remaining = siphoner.getSiphonAmount(held, livingEntity);
                    System.out.println(remaining);

                    for (ItemStack jar : jars) {
                        remaining = MobSoulUtils.addSoulsToJar(jar, type, remaining);
                        if (remaining <= 0)
                            break;
                    }
                }
            }
        }
    }

    private static List<ItemStack> getValidSoulJars(Player player, MobSoulType type) {
        return player.getInventory().items.stream()
                .filter(s -> s.getItem() instanceof SoulJarItem)
                .filter(s -> MobSoulUtils.canAddTypeToJar(s, type))
                .sorted((a, b) -> MobSoulUtils.getType(a) != null ? -1 : MobSoulUtils.getType(b) != null ? 0 : 1)
                .collect(Collectors.toList());
    }
}
