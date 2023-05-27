package com.alex.mysticalagriculture.cucumber.blockentity;

import com.alex.mysticalagriculture.cucumber.helper.BlockEntityHelper;
import com.alex.mysticalagriculture.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public abstract class BaseInventoryBlockEntity extends BlockEntity {

    public BaseInventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract BaseItemStackHandler getInventory();

    @Override
    public void markDirty() {
        super.markDirty();
        BlockEntityHelper.dispatchToNearbyPlayers(this);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.getInventory().deserializeNBT(nbt);
        /*if (this instanceof SoulExtractorBlockEntity || this instanceof InfusionPedestalBlockEntity || this instanceof InfusionAltarBlockEntity || this instanceof ReprocessorBlockEntity) {
            super.readNbt(nbt);
            this.getInventory().deserializeNBT(nbt);
        } else {
            Inventories.readNbt(nbt, stacks);
        }*/
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.copyFrom(this.getInventory().serializeNBT());
        /*if (this instanceof SoulExtractorBlockEntity || this instanceof InfusionPedestalBlockEntity || this instanceof InfusionAltarBlockEntity || this instanceof ReprocessorBlockEntity) {
            nbt.copyFrom(this.getInventory().serializeNBT());
        }
        else {
            NbtList nbtList = new NbtList();
            for (int i = 0; i < stacks.size(); ++i) {
                ItemStack itemStack = stacks.get(i);
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte)i);
                itemStack.writeNbt(nbtCompound);
                nbtList.add(nbtCompound);
            }
            nbt.put("Items", nbtList);
            //Inventories.writeNbt(nbt, stacks);
            //nbt.copyFrom(Inventories.writeNbt(nbt, stacks));
        }*/
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this, BlockEntity::createNbtWithIdentifyingData);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbtWithIdentifyingData();
    }

    public boolean isUsableByPlayer(PlayerEntity player) {
        BlockPos pos = this.getPos();
        return player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }
}
