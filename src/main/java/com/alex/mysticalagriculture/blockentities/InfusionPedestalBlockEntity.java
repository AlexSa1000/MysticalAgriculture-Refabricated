package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.zucchini.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.zzz.BaseItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class InfusionPedestalBlockEntity extends BaseInventoryBlockEntity {
    private final BaseItemStackHandler inventory;

    public InfusionPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.INFUSION_PEDESTAL, pos, state);
        this.inventory = createInventoryHandler(this::markDirty);
    }

    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    public static BaseItemStackHandler createInventoryHandler(Runnable onContentsChanged) {
        return BaseItemStackHandler.create(1, onContentsChanged, builder -> {
            builder.setDefaultSlotLimit(1);
        });
    }

    /*@Override
    public int[] getAvailableSlots(Direction side) {
        return new int[]{0};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.getStack(0).isEmpty();
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }*/
}

