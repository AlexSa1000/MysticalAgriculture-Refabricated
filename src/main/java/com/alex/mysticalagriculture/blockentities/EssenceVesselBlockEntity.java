package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.api.crop.CropProvider;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.util.EssenceVesselType;
import com.alex.mysticalagriculture.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class EssenceVesselBlockEntity extends BaseInventoryBlockEntity {
    private static final int MAX_STACK_SIZE = 40;
    private final BaseItemStackHandler inventory;

    public EssenceVesselBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.ESSENCE_VESSEL, pos, state);
        this.inventory = BaseItemStackHandler.create(1, this::markDirty, handler -> {
            handler.setDefaultSlotLimit(MAX_STACK_SIZE);
            handler.setCanInsert((slot, stack) -> canInsertStack(stack));
        });
    }

    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    public ItemStack insert(ItemStack stack) {
        return this.inventory.insertItem(0, stack);
    }

    public ItemStack extract(int amount) {
        return this.inventory.extractItem(0, amount, false);
    }

    private static boolean canInsertStack(ItemStack stack) {
        if (stack.getItem() instanceof CropProvider provider) {
            return EssenceVesselType.fromCrop(provider) != null;
        }

        return false;
    }
}

