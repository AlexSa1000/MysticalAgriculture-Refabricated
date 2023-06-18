package com.alex.mysticalagriculture.items.tool;

import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import com.alex.mysticalagriculture.api.tinkering.IElementalItem;
import com.alex.mysticalagriculture.api.tinkering.ITinkerable;
import com.alex.cucumber.item.BaseItem;
import com.alex.cucumber.lib.Tooltips;
import com.alex.mysticalagriculture.lib.ModTooltips;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;

public class EssenceStaffItem extends BaseItem implements ITinkerable, IElementalItem {
    private static final EnumSet<AugmentType> TYPES = EnumSet.of(AugmentType.STAFF);
    private final int tinkerableTier;
    private final int slots;

    public EssenceStaffItem(int tinkerableTier, int slots, Function<Item.Properties, Item.Properties> properties) {
        super(properties);
        this.tinkerableTier = tinkerableTier;
        this.slots = slots;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        // hidden
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(ModTooltips.getTooltipForTier(this.tinkerableTier));
        tooltip.add(Tooltips.NOT_YET_IMPLEMENTED.build());
    }

    @Override
    public int getAugmentSlots() {
        return this.slots;
    }

    @Override
    public EnumSet<AugmentType> getAugmentTypes() {
        return TYPES;
    }

    @Override
    public int getTinkerableTier() {
        return this.tinkerableTier;
    }
}
