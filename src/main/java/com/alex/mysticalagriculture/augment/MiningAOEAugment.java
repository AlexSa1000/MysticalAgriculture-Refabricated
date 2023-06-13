package com.alex.mysticalagriculture.augment;

import com.alex.cucumber.helper.ColorHelper;
import com.alex.mysticalagriculture.api.tinkering.Augment;
import com.alex.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumSet;

public class MiningAOEAugment extends Augment {
    private final int range;

    public MiningAOEAugment(ResourceLocation id, int tier, int range) {
        super(id, tier, EnumSet.of(AugmentType.PICKAXE, AugmentType.AXE, AugmentType.SHOVEL), getColor(0xD5FFF6, tier), getColor(0x0EBABD, tier));
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 5, 1));
    }

}
