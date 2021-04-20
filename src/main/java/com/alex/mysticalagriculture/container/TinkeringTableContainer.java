package com.alex.mysticalagriculture.container;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.api.tinkerer.AugmentProvider;
import com.alex.mysticalagriculture.api.util.AugmentUtils;
import com.alex.mysticalagriculture.blockentities.TinkeringTableBlockEntity;
import com.alex.mysticalagriculture.container.slot.AugmentSlot;
import com.alex.mysticalagriculture.container.slot.ElementSlot;
import com.alex.mysticalagriculture.container.slot.TinkerableSlot;
import com.alex.mysticalagriculture.init.ScreenHandlerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

import java.util.function.Function;

public class TinkeringTableContainer extends ScreenHandler {

    private final Function<PlayerEntity, Boolean> isUsableByPlayer;
    private final Inventory inventory;

    private TinkeringTableContainer(ScreenHandlerType<?> type, int id, PlayerInventory playerInventory) {
        this(type, id, playerInventory, p -> false, (new TinkeringTableBlockEntity()).toInventory());
    }

    private TinkeringTableContainer(ScreenHandlerType<?> type, int id, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> isUsableByPlayer, Inventory inventory) {
        super(type, id);
        this.isUsableByPlayer = isUsableByPlayer;
        this.inventory = inventory;

        this.addSlot(new TinkerableSlot(this, inventory, 0, 80, 49));
        this.addSlot(new AugmentSlot(this, inventory, 1, 140, 36, 0));
        this.addSlot(new AugmentSlot(this, inventory, 2, 140, 62, 1));

        this.addSlot(new ElementSlot(this, inventory, 3, 20, 18));
        this.addSlot(new ElementSlot(this, inventory, 4, 20, 39));
        this.addSlot(new ElementSlot(this, inventory, 5, 20, 60));
        this.addSlot(new ElementSlot(this, inventory, 6, 20, 81));

        for (int k = 0; k < 3; k++) {
            for (int i1 = 0; i1 < 9; i1++) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 115 + k * 18));
            }
        }

        for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 173));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack tinkerable = this.inventory.getStack(0);
        if (!tinkerable.isEmpty()) {
            for (int i = 0; i < 2; i++) {
                ItemStack stack = this.inventory.getStack(i + 1);
                Item item = stack.getItem();
                Augment augmentInSlot = AugmentUtils.getAugment(tinkerable, i);
                if (!stack.isEmpty() && item instanceof AugmentProvider) {
                    Augment augment = ((AugmentProvider) item).getAugment();
                    if (augment != augmentInSlot)
                        AugmentUtils.addAugment(tinkerable, augment, i);
                } else if (augmentInSlot != null) {
                    AugmentUtils.removeAugment(tinkerable, i);
                }
            }
        }

        super.onContentChanged(inventory);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.isUsableByPlayer.apply(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }

    public static TinkeringTableContainer create(int windowId, PlayerInventory playerInventory) {
        return new TinkeringTableContainer(ScreenHandlerTypes.TINKERING_TABLE, windowId, playerInventory);
    }

    public static TinkeringTableContainer create(int windowId, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> isUsableByPlayer, Inventory inventory) {
        return new TinkeringTableContainer(ScreenHandlerTypes.TINKERING_TABLE, windowId, playerInventory, isUsableByPlayer, inventory);
    }
}
