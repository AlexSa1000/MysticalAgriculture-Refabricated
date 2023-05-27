package com.alex.mysticalagriculture.cucumber.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;

public class BaseScreenHandler extends ScreenHandler {

    private final BlockPos pos;

    protected BaseScreenHandler(ScreenHandlerType<?> menu, int id, BlockPos pos) {
        super(menu, id);
        this.pos = pos;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) <= 64.0;
    }

    public BlockPos getBlockPos() {
        return this.pos;
    }
}
