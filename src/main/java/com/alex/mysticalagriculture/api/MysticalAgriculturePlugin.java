package com.alex.mysticalagriculture.api;

import com.alex.mysticalagriculture.api.lib.PluginConfig;
import com.alex.mysticalagriculture.api.registry.AugmentRegistry;
import com.alex.mysticalagriculture.api.registry.CropRegistry;
import com.alex.mysticalagriculture.api.registry.MobSoulTypeRegistry;

public interface MysticalAgriculturePlugin {
    /**
     * Override this method to configure plugin wide settings
     * @param config the plugin config
     */
    default void configure(PluginConfig config) { }

    /**
     * Override this method and use the supplied registry to register all of your crops
     * @param registry the crop registry
     */
    default void onRegisterCrops(CropRegistry registry) { }

    /**
     * Override this method and use the supplied registry to modify crops from the crop registry
     * @param registry the crop registry
     */
    default void onPostRegisterCrops(CropRegistry registry) { }

    /**
     * Override this method and use the supplied registry to register all of your augments
     * @param registry the augment registry
     */
    default void onRegisterAugments(AugmentRegistry registry) { }

    /**
     * Override this method and use to the supplied registry to modify augments from the augment registry
     * @param registry the augment registry
     */
    default void onPostRegisterAugments(AugmentRegistry registry) { }

    /**
     * Override this method and use the supplied registry to register all of your mob soul types
     * @param registry the mob soul type registry
     */
    default void onRegisterMobSoulTypes(MobSoulTypeRegistry registry) { }

    /**
     * Override this method and ise the supplied registry to modify mob soul types from the mob soul type registry
     * @param registry the mob soul type registry
     */
    default void onPostRegisterMobSoulTypes(MobSoulTypeRegistry registry) { }
}
