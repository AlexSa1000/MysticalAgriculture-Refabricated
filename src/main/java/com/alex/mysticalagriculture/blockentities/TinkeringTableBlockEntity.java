package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.container.TinkeringTableContainer;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.util.blockentity.BaseInventoryBlockEntity;
import com.alex.mysticalagriculture.util.util.Localizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class TinkeringTableBlockEntity extends BaseInventoryBlockEntity implements NamedScreenHandlerFactory, SidedInventory {

    public TinkeringTableBlockEntity() {
        super(BlockEntities.TINKERING_TABLE, 7);
    }

    @Override
    public Text getDisplayName() {
        return Localizable.of("container.mysticalagriculture.tinkering_table").build();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return TinkeringTableContainer.create(syncId, inv, this::isUsableByPlayer, this);
    }
}
