package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.api.tinkering.IAugmentProvider;
import com.alex.mysticalagriculture.api.tinkering.ITinkerable;
import com.alex.mysticalagriculture.container.TinkeringTableContainer;
import com.alex.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import com.alex.cucumber.util.Localizable;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import com.alex.mysticalagriculture.lib.ModCrops;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class TinkeringTableBlockEntity extends BaseInventoryBlockEntity implements ExtendedScreenHandlerFactory {
    private final BaseItemStackHandler inventory;

    public TinkeringTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TINKERING_TABLE, pos, state);
        this.inventory = createInventoryHandler(() -> {
            if (this.getLevel() != null && !this.getLevel().isClientSide()) {
                this.markDirtyAndDispatch();
            }
        });
    }

    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public Component getDisplayName() {
        return Localizable.of("container.mysticalagriculture.tinkering_table").build();
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return TinkeringTableContainer.create(windowId, playerInventory, this.inventory, this.getBlockPos());
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeBlockPos(worldPosition);
    }

    public static BaseItemStackHandler createInventoryHandler() {
        return createInventoryHandler(null);
    }

    public static BaseItemStackHandler createInventoryHandler(Runnable onContentsChanged) {
        return BaseItemStackHandler.create(7, onContentsChanged, builder -> {
            builder.setDefaultSlotLimit(1);
            builder.setCanInsert((slot, stack) -> {
                var item = stack.getItem();
                return switch (slot) {
                    case 0 -> item instanceof ITinkerable;
                    case 1, 2 -> item instanceof IAugmentProvider;
                    case 3 -> item == ModCrops.AIR.getEssenceItem();
                    case 4 -> item == ModCrops.EARTH.getEssenceItem();
                    case 5 -> item == ModCrops.WATER.getEssenceItem();
                    case 6 -> item == ModCrops.FIRE.getEssenceItem();
                    default -> true;
                };
            });
        });
    }
}
