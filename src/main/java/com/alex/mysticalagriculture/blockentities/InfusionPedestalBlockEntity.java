package com.alex.mysticalagriculture.blockentities;

import com.alex.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class InfusionPedestalBlockEntity extends BaseInventoryBlockEntity {
    private final BaseItemStackHandler inventory;

    public InfusionPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INFUSION_PEDESTAL, pos, state);
        this.inventory = createInventoryHandler(this::markDirtyAndDispatch);
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
}

