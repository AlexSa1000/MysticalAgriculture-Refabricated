package com.alex.mysticalagriculture.util;

import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.item.BaseItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class BaseReusableItem extends BaseItem implements FabricRecipeRemainder{
    private final boolean damage;
    private final boolean tooltip;

    public BaseReusableItem(Function<Settings, Settings> properties) {
        this(true, properties);
    }

    public BaseReusableItem(boolean tooltip, Function<Settings, Settings> properties) {
        this(0, tooltip, properties);
    }

    public BaseReusableItem(int uses, Function<Settings, Settings> properties) {
        this(uses, true, properties);
    }

    public BaseReusableItem(int uses, boolean tooltip, Function<Settings, Settings> properties) {
        super(properties.compose(p -> p.maxDamageIfAbsent(Math.max(uses - 1, 0))));
        this.damage = uses > 0;
        this.tooltip = tooltip;
    }

    @Override
    public boolean hasRecipeRemainder() {
        return true;
    }

    @Override
    public ItemStack getRemainder(ItemStack stack, CraftingInventory craftingInventory, PlayerEntity playerEntity) {
        if (stack != null && stack.isDamageable()) {
            ItemStack stackCopy = stack.copy();
            if (playerEntity instanceof ServerPlayerEntity) {
                stackCopy.damage(1, new Random(), (ServerPlayerEntity) playerEntity);
            } else {
                stackCopy.damage(1, new Random(), null);
            }
            if (stack.getDamage() >= stackCopy.getMaxDamage()) {
                return ItemStack.EMPTY;
            } else {
                return stackCopy;
            }
        } else {
            return stack;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.tooltip) {
            if (this.damage) {
                int damage = stack.getMaxDamage() - stack.getDamage() + 1;
                if (damage == 1) {
                    tooltip.add(ModTooltips.ONE_USE_LEFT.build());
                } else {
                    tooltip.add(ModTooltips.USES_LEFT.args(damage).build());
                }
            } else {
                tooltip.add(ModTooltips.UNLIMITED_USES.build());
            }
        }
    }
}
