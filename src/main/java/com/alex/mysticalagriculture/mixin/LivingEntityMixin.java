package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.api.lib.AbilityCache;
import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.api.util.MobSoulUtils;
import com.alex.mysticalagriculture.items.SoulJarItem;
import com.alex.mysticalagriculture.items.SouliumDaggerItem;
import com.alex.mysticalagriculture.registry.MobSoulTypeRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    private static final AbilityCache ABILITY_CACHE = new AbilityCache();

    @Inject(method = "baseTick" , at = @At(value = "HEAD"))
    private void injected(CallbackInfo ci) {
        if (((Object) this) instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) ((Object) this);
            World world = player.getEntityWorld();
            List<Augment> augments = AugmentUtils.getArmorAugments(player);
            augments.forEach(a -> {
                a.onPlayerTick(world, player, ABILITY_CACHE);
                a.onPlayerFall(world, player);
            });

            ABILITY_CACHE.getCachedAbilities(player).forEach(c -> {
                if (augments.stream().noneMatch(a -> c.equals(a.getId().toString()))) {
                    ABILITY_CACHE.remove(c, player);
                }
            });
        }
    }

    @Inject(method = "onDeath", at = @At(value = "HEAD"), cancellable = true)
    private void injected(DamageSource source, CallbackInfo ci) {
        var entity = source.getAttacker();

        if (entity instanceof PlayerEntity player) {
            var held = player.getStackInHand(Hand.MAIN_HAND);

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

    private static List<ItemStack> getValidSoulJars(PlayerEntity player, MobSoulType type) {
        return player.getInventory().main.stream()
                .filter(s -> s.getItem() instanceof SoulJarItem)
                .filter(s -> MobSoulUtils.canAddTypeToJar(s, type))
                .sorted((a, b) -> MobSoulUtils.getType(a) != null ? -1 : MobSoulUtils.getType(b) != null ? 0 : 1)
                .collect(Collectors.toList());
    }
}
