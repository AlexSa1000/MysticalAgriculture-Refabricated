package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.item.tool.BaseSwordItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class SouliumDaggerItem extends BaseSwordItem {
    public SouliumDaggerItem(ToolMaterial tier, Function<Settings, Settings> properties) {
        super(tier, 3, -2.0F, properties);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(ModTooltips.SOULIUM_DAGGER.build());
    }

    public double getSiphonAmount(LivingEntity entity) {
        boolean isPeaceful = entity.getType().getSpawnGroup().isPeaceful();
        return isPeaceful ? 1.5D : 1.0D;
    }
}
