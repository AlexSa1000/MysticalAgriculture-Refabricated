package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.util.blockentity.BaseInventoryBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class InfusionPedestalBlockEntity extends BaseInventoryBlockEntity {

    public InfusionPedestalBlockEntity() {
        super(BlockEntities.INFUSION_PEDESTAL, 1);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[]{0};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.getStack(0).isEmpty();
    }

}

