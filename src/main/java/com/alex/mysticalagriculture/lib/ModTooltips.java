package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.util.util.Tooltip;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModTooltips {
    public static final Tooltip EMPTY = new Tooltip("tooltip.mysticalagriculture.empty");
    public static final Tooltip FILLED = new Tooltip("tooltip.mysticalagriculture.filled");
    public static final Tooltip TIER = new Tooltip("tooltip.mysticalagriculture.tier");
    public static final Tooltip MST_ID = new Tooltip("tooltip.mysticalagriculture.mst_id");
    public static final Tooltip WATERING_CAN_AREA = new Tooltip("tooltip.mysticalagriculture.watering_can_area");
    public static final Tooltip EXPERIENCE_CAPSULE = new Tooltip("tooltip.mysticalagriculture.experience_capsule");
    public static final Tooltip SOUL_JAR = new Tooltip("tooltip.mysticalagriculture.soul_jar");
    public static final Tooltip SOULIUM_DAGGER = new Tooltip("tooltip.mysticalagriculture.soulium_dagger");
    public static final Tooltip USES_LEFT = new Tooltip("tooltip.mysticalagriculture.uses_left");
    public static final Tooltip ONE_USE_LEFT = new Tooltip("tooltip.mysticalagriculture.one_use_left");
    public static final Tooltip UNLIMITED_USES = new Tooltip("tooltip.mysticalagriculture.unlimited_uses");

    public static final Tooltip COOKING_SPEED = new Tooltip("tooltip.mysticalagriculture.cooking_speed");
    public static final Tooltip FUEL_EFFICIENCY = new Tooltip("tooltip.mysticalagriculture.fuel_efficiency");

    public static final Tooltip GROWTH_ACCELERATOR = new Tooltip("tooltip.mysticalagriculture.growth_accelerator");
    public static final Tooltip GROWTH_ACCELERATOR_RANGE = new Tooltip("tooltip.mysticalagriculture.growth_accelerator_range");

    public static final Tooltip REPROCESSOR_SPEED = new Tooltip("tooltip.mysticalagriculture.reprocessor_speed");
    public static final Tooltip REPROCESSOR_FUEL_RATE = new Tooltip("tooltip.mysticalagriculture.reprocessor_fuel_rate");
    public static final Tooltip REPROCESSOR_FUEL_CAPACITY = new Tooltip("tooltip.mysticalagriculture.reprocessor_fuel_capacity");
    public static final Tooltip ACTIVATE_WITH_REDSTONE = new Tooltip("tooltip.mysticalagriculture.activate_with_redstone");

    public static final Tooltip FERTILIZED_ESSENCE_CHANCE = new Tooltip("tooltip.mysticalagriculture.fertilized_essence_chance");
    public static final Tooltip MYSTICAL_FERTILIZER = new Tooltip("tooltip.mysticalagriculture.mystical_fertilizer");

    public static Text getTooltipForTier(int tier) {
        return TIER.args(AugmentUtils.getTooltipForTier(tier)).color(Formatting.GRAY).build();
    }
}
