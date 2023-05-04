package com.alex.mysticalagriculture.api.registry;

import com.alex.mysticalagriculture.api.tinkering.Augment;
import net.minecraft.util.Identifier;

import java.util.List;

public interface AugmentRegistry {
    /**
     * Register an augment in the augment registry
     * @param augment the augment to register
     */
    void register(Augment augment);

    /**
     * Get an unmodifiable list of all the registered crops
     * @return a list of registered crops
     */
    List<Augment> getAugments();

    /**
     * Get the crop with the specified internal name from the crop registry
     * @param id the resource location id of the crop
     * @return the crop for this id
     */
    Augment getAugmentById(Identifier id);
}
