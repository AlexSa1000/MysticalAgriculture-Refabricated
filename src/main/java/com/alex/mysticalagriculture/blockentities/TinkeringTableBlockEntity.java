package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.api.tinkering.AugmentProvider;
import com.alex.mysticalagriculture.api.tinkering.Tinkerable;
import com.alex.mysticalagriculture.lib.ModCrops;
import com.alex.mysticalagriculture.screenhandler.TinkeringTableScreenHandler;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.cucumber.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.cucumber.util.Localizable;
import com.alex.mysticalagriculture.cucumber.inventory.BaseItemStackHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TinkeringTableBlockEntity extends BaseInventoryBlockEntity implements ExtendedScreenHandlerFactory {
    private final BaseItemStackHandler inventory;

    public TinkeringTableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.TINKERING_TABLE, pos, state);
        this.inventory = createInventoryHandler(() -> {
            if (this.getWorld() != null && !this.getWorld().isClient()) {
                this.markDirty();
            }
        });
    }

    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Localizable.of("container.mysticalagriculture.tinkering_table").build();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return TinkeringTableScreenHandler.create(syncId, inv, this.inventory, this.getPos());
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
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
                    case 0 -> item instanceof Tinkerable;
                    case 1, 2 -> item instanceof AugmentProvider;
                    case 3 -> item == ModCrops.AIR.getEssenceItem();
                    case 4 -> item == ModCrops.EARTH.getEssenceItem();
                    case 5 -> item == ModCrops.WATER.getEssenceItem();
                    case 6 -> item == ModCrops.FIRE.getEssenceItem();
                    default -> true;
                };
            });
        });
    }

    /*public static Inventory createInventory() {
        return new SimpleInventory(7);
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }*/
}
