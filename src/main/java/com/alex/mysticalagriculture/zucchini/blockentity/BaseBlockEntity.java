package com.alex.mysticalagriculture.zucchini.blockentity;


import com.alex.mysticalagriculture.zucchini.block.BaseBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class BaseBlockEntity extends BaseBlock implements BlockEntityProvider {

    public BaseBlockEntity(Material material, BlockSoundGroup sound, float hardness, float resistance, boolean tool) {
        super(material, sound, hardness, resistance, tool);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? this.getClientTicker(world, state, type) : this.getServerTicker(world, state, type);
    }

    protected <T extends BlockEntity> BlockEntityTicker<T> getClientTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }
    protected <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    @Contract(value = "_,_,null->null",pure = true)
    protected static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>)ticker : null;
    }
}
