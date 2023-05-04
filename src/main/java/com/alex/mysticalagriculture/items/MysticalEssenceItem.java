package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.api.crop.Crop;
import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.zucchini.item.BaseItem;
import com.alex.mysticalagriculture.zucchini.util.Localizable;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class MysticalEssenceItem extends BaseItem implements CropProvider {
    private final Crop crop;

    public MysticalEssenceItem(Crop crop) {
        super();
        this.crop = crop;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Localizable.of("item.mysticalagriculture.mystical_essence").args(this.crop.getDisplayName()).build();

    }

    public Crop getCrop() {
        return this.crop;
    }

}
