package com.alex.mysticalagriculture.blockentities;

import com.alex.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.mysticalagriculture.init.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AwakeningPedestalBlockEntity extends BaseInventoryBlockEntity {
    private final BaseItemStackHandler inventory;

    public AwakeningPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.AWAKENING_PEDESTAL, pos, state);
        this.inventory = BaseItemStackHandler.create(1, this::markDirtyAndDispatch, handler -> {
            handler.setDefaultSlotLimit(1);
        });
    }

    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }
}
