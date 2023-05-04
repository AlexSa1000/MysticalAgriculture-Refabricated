package com.alex.mysticalagriculture.api.farmland;

import com.alex.mysticalagriculture.api.crop.CropTier;

/**
 * Implement this interface on Farmland that should allow crops to drop extra seeds
 * and Inferium crops to drop more essence
 */
public interface EssenceFarmLand {
    /**
     * Get the tier/group this farmland belongs to
     * @return the tier of this farmland
     */
    CropTier getTier();
}
