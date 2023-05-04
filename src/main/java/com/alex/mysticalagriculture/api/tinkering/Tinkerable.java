package com.alex.mysticalagriculture.api.tinkering;

import java.util.EnumSet;

public interface Tinkerable {

    int getAugmentSlots();

    EnumSet<AugmentType> getAugmentTypes();

    int getTinkerableTier();

    default boolean canApplyAugment(Augment augment) {
        return augment.getAugmentTypes().stream().anyMatch(t -> this.getAugmentTypes().contains(t)) && augment.getTier() <= this.getTinkerableTier();
    }
}
