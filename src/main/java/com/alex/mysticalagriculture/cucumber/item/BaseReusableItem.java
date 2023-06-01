package com.alex.mysticalagriculture.cucumber.item;

import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.util.FabricRecipeRemainder;
import com.alex.mysticalagriculture.cucumber.util.Utils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class BaseReusableItem extends BaseItem implements FabricRecipeRemainder {
    private final boolean damage;
    private final boolean tooltip;

    public BaseReusableItem(Function<Settings, Settings> settings) {
        this(true, settings);
    }

    public BaseReusableItem(boolean tooltip, Function<Item.Settings, Item.Settings> properties) {
        this(0, tooltip, properties);
    }

    public BaseReusableItem(int uses, Function<Item.Settings, Item.Settings> properties) {
        this(uses, true, properties);
    }

    public BaseReusableItem(int uses, boolean tooltip, Function<Item.Settings, Item.Settings> properties) {
        super(properties.compose((p) -> {
            return p.maxDamageIfAbsent(Math.max(uses - 1, 0));
        }));
        this.damage = uses > 0;
        this.tooltip = tooltip;
    }

    @Override
    public boolean hasRecipeRemainder() {
        return true;
    }

    @Override
    public ItemStack getRemainder(ItemStack stack, CraftingInventory craftingInventory, PlayerEntity playerEntity) {
        ItemStack copy = stack.copy();
        copy.setCount(1);
        if (!this.damage) {
            return copy;
        } else {
            int unbreaking = EnchantmentHelper.getLevel(Enchantments.UNBREAKING, stack);

            for(int i = 0; i < unbreaking; ++i) {
                if (UnbreakingEnchantment.shouldPreventDamage(stack, unbreaking, Utils.RANDOM)) {
                    return copy;
                }
            }

            copy.setDamage(stack.getDamage() + 1);
            if (copy.getDamage() > stack.getMaxDamage()) {
                return ItemStack.EMPTY;
            } else {
                return copy;
            }
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
                    tooltip.add(ModTooltips.USES_LEFT.args(new Object[]{damage}).build());
                }
            } else {
                tooltip.add(ModTooltips.UNLIMITED_USES.build());
            }
        }
    }
}
