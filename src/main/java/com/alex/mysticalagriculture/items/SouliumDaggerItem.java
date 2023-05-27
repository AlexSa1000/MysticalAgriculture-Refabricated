package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.lib.ModToolMaterial;
import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.item.tool.BaseSwordItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;

public class SouliumDaggerItem extends BaseSwordItem {
    private final DaggerType type;

    public SouliumDaggerItem(ToolMaterial tier, DaggerType type) {
        super(tier, type.getDamage(), -2.4F, p -> p.maxDamageIfAbsent(type.getDurability()));
        this.type = type;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return "item.mysticalagriculture.soulium_dagger";
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        switch (this.type) {
            case PASSIVE -> {
                tooltip.add(ModTooltips.PASSIVE_ATTUNED.color(Formatting.GREEN).build());
                tooltip.add(ModTooltips.PASSIVE_SOULIUM_DAGGER.build());
            }
            case HOSTILE -> {
                tooltip.add(ModTooltips.HOSTILE_ATTUNED.color(Formatting.RED).build());
                tooltip.add(ModTooltips.HOSTILE_SOULIUM_DAGGER.build());
            }
            case CREATIVE -> {
                tooltip.add(ModTooltips.CREATIVE_ATTUNED.color(Formatting.LIGHT_PURPLE).build());
                tooltip.add(ModTooltips.CREATIVE_SOULIUM_DAGGER.build());
            }
        }
    }

    public double getSiphonAmount(ItemStack stack, LivingEntity entity) {
        return this.type.getSiphonAmount(stack, entity);
    }

    public enum DaggerType {
        BASIC(3, ModToolMaterial.SOULIUM.getDurability(), (stack, entity) -> 1.0D),
        PASSIVE(6, ModToolMaterial.SOULIUM.getDurability() * 2, (stack, entity) -> isPassive(entity) ? 1.5D : 1.0D),
        HOSTILE(6, ModToolMaterial.SOULIUM.getDurability() * 2, (stack, entity) -> !isPassive(entity) ? 1.5D : 1.0D),
        CREATIVE(65, -1, (stack, entity) -> Double.MAX_VALUE);

        private final int damage;
        private final int durability;
        private final BiFunction<ItemStack, LivingEntity, Double> siphonAmountFunc;

        DaggerType(int damage, int durability, BiFunction<ItemStack, LivingEntity, Double> siphonAmountFunc) {
            this.damage = damage;
            this.durability = durability;
            this.siphonAmountFunc = siphonAmountFunc;
        }

        public double getSiphonAmount(ItemStack stack, LivingEntity entity) {
            return this.siphonAmountFunc.apply(stack, entity);
        }

        public int getDamage() {
            return this.damage;
        }

        public int getDurability() {
            return this.durability;
        }

        private static boolean isPassive(LivingEntity entity) {
            return entity.getType().getSpawnGroup().isPeaceful();
        }
    }
}
