package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.blocks.EssenceFurnaceBlock;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.util.util.Localizable;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public abstract class EssenceFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    public EssenceFurnaceBlockEntity(BlockEntityType<?> type) {
        super(type, RecipeType.SMELTING);
    }

    @Override
    protected Text getContainerName() {
        return Localizable.of(String.format("container.mysticalagriculture.%s_furnace", this.getTier().getName())).build();

    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected int getFuelTime(ItemStack fuel) {
        return (int) (super.getFuelTime(fuel) * this.getTier().getBurnTimeMultiplier());
    }

    @Override
    protected int getCookTime() {
        return (int) (super.getCookTime() * this.getTier().getCookTimeMultiplier());
    }

    public abstract EssenceFurnaceBlock.FurnaceTier getTier();

    public static class Inferium extends EssenceFurnaceBlockEntity {
        public Inferium() {
            super(BlockEntities.INFERIUM_FURNACE);
        }

        @Override
        public EssenceFurnaceBlock.FurnaceTier getTier() {
            return EssenceFurnaceBlock.FurnaceTier.INFERIUM;
        }
    }

    public static class Prudentium extends EssenceFurnaceBlockEntity {
        public Prudentium() {
            super(BlockEntities.PRUDENTIUM_FURNACE);
        }

        @Override
        public EssenceFurnaceBlock.FurnaceTier getTier() {
            return EssenceFurnaceBlock.FurnaceTier.PRUDENTIUM;
        }
    }

    public static class Tertium extends EssenceFurnaceBlockEntity {
        public Tertium() {
            super(BlockEntities.TERTIUM_FURNACE);
        }

        @Override
        public EssenceFurnaceBlock.FurnaceTier getTier() {
            return EssenceFurnaceBlock.FurnaceTier.TERTIUM;
        }
    }

    public static class Imperium extends EssenceFurnaceBlockEntity {
        public Imperium() {
            super(BlockEntities.IMPERIUM_FURNACE);
        }

        @Override
        public EssenceFurnaceBlock.FurnaceTier getTier() {
            return EssenceFurnaceBlock.FurnaceTier.IMPERIUM;
        }
    }

    public static class Supremium extends EssenceFurnaceBlockEntity {
        public Supremium() {
            super(BlockEntities.SUPREMIUM_FURNACE);
        }

        @Override
        public EssenceFurnaceBlock.FurnaceTier getTier() {
            return EssenceFurnaceBlock.FurnaceTier.SUPREMIUM;
        }
    }
}
