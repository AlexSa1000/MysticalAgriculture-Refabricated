package com.alex.mysticalagriculture.blockentities;

import com.alex.cucumber.util.Localizable;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.util.FurnaceTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class EssenceFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    public EssenceFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() {
        return Localizable.of(String.format("container.mysticalagriculture.%s_furnace", this.getTier().getName())).build();
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new FurnaceMenu(id, player, this, this.dataAccess);
    }

    @Override
    protected int getBurnDuration(ItemStack stack) {
        return (int) (super.getBurnDuration(stack) * this.getTier().getBurnTimeMultiplier());
    }

    public void setItem(int slot, ItemStack stack) {
        ItemStack itemstack = this.items.get(slot);
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        this.items.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (slot == 0 && !flag) {
            this.cookingTotalTime = (int) (getTotalCookTime(level, this) * this.getTier().getCookTimeMultiplier());
            this.cookingProgress = 0;
            this.setChanged();
        }

    }

    protected boolean canAcceptRecipeOutput(Recipe<?> recipe, NonNullList<ItemStack> items, int maxStackSize) {
        if (!items.get(0).isEmpty() && recipe != null) {
            ItemStack itemstack = ((Recipe<WorldlyContainer>) recipe).assemble(this);
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = items.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.sameItem(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= maxStackSize && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) {
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    protected boolean burn(Recipe<?> recipe, NonNullList<ItemStack> items, int maxStackSize) {
        if (recipe != null && this.canBurn(recipe, items, maxStackSize)) {
            ItemStack itemstack = items.get(0);
            ItemStack itemstack1 = ((Recipe<WorldlyContainer>) recipe).assemble(this);
            ItemStack itemstack2 = items.get(2);
            if (itemstack2.isEmpty()) {
                items.set(2, itemstack1.copy());
            } else if (itemstack2.is(itemstack1.getItem())) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (itemstack.is(Blocks.WET_SPONGE.asItem()) && !items.get(1).isEmpty() && items.get(1).is(Items.BUCKET)) {
                items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EssenceFurnaceBlockEntity block) {
        var flag = block.isLit();
        var flag1 = false;

        if (block.isLit()) {
            --block.litTime;
        }

        var stack = block.items.get(1);
        if (block.isLit() || !stack.isEmpty() && !block.items.get(0).isEmpty()) {
            Recipe<?> recipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, block, level).orElse(null);
            int i = block.getMaxStackSize();
            if (!block.isLit() && block.canBurn(recipe, block.items, i)) {
                block.litTime = block.getBurnDuration(stack);
                block.litDuration = block.litTime;
                if (block.isLit()) {
                    flag1 = true;
                    //if (stack.hasCraftingRemainingItem())
                    //    block.items.set(1, stack.getRecipeRemainder());
                    //else
                    if (!stack.isEmpty()) {
                        stack.shrink(1);

                        if (stack.isEmpty()) {
                            block.items.set(1, stack.getRecipeRemainder());
                        }
                    }
                }
            }

            if (block.isLit() && block.canBurn(recipe, block.items, i)) {
                ++block.cookingProgress;
                if (block.cookingProgress == block.cookingTotalTime) {
                    block.cookingProgress = 0;
                    block.cookingTotalTime = (int) (getTotalCookTime(level, block) * block.getTier().getCookTimeMultiplier());
                    if (block.burn(recipe, block.items, i)) {
                        block.setRecipeUsed(recipe);
                    }

                }
            } else {
                block.cookingProgress = 0;
            }
        } else if (!block.isLit() && block.cookingProgress > 0) {
            block.cookingProgress = Mth.clamp(block.cookingProgress - 2, 0, block.cookingTotalTime);
        }

        if (flag != block.isLit()) {
            flag1 = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, block.isLit());
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }
    }

    private static int getTotalCookTime(Level p_222693_, AbstractFurnaceBlockEntity p_222694_) {
        return p_222694_.quickCheck.getRecipeFor(p_222694_, p_222693_).map(AbstractCookingRecipe::getCookingTime).orElse(200);
    }

    public abstract FurnaceTier getTier();

    public static class Inferium extends EssenceFurnaceBlockEntity {
        public Inferium(BlockPos pos, BlockState state) {
            super(BlockEntities.INFERIUM_FURNACE, pos, state);
        }

        @Override
        public FurnaceTier getTier() {
            return FurnaceTier.INFERIUM;
        }
    }

    public static class Prudentium extends EssenceFurnaceBlockEntity {
        public Prudentium(BlockPos pos, BlockState state) {
            super(BlockEntities.PRUDENTIUM_FURNACE, pos, state);
        }

        @Override
        public FurnaceTier getTier() {
            return FurnaceTier.PRUDENTIUM;
        }
    }

    public static class Tertium extends EssenceFurnaceBlockEntity {
        public Tertium(BlockPos pos, BlockState state) {
            super(BlockEntities.TERTIUM_FURNACE, pos, state);
        }

        @Override
        public FurnaceTier getTier() {
            return FurnaceTier.TERTIUM;
        }
    }

    public static class Imperium extends EssenceFurnaceBlockEntity {
        public Imperium(BlockPos pos, BlockState state) {
            super(BlockEntities.IMPERIUM_FURNACE, pos, state);
        }

        @Override
        public FurnaceTier getTier() {
            return FurnaceTier.IMPERIUM;
        }
    }

    public static class Supremium extends EssenceFurnaceBlockEntity {
        public Supremium(BlockPos pos, BlockState state) {
            super(BlockEntities.SUPREMIUM_FURNACE, pos, state);
        }

        @Override
        public FurnaceTier getTier() {
            return FurnaceTier.SUPREMIUM;
        }
    }

    public static class AwakenedSupremium extends EssenceFurnaceBlockEntity {
        public AwakenedSupremium(BlockPos pos, BlockState state) {
            super(BlockEntities.AWAKENED_SUPREMIUM_FURNACE, pos, state);
        }

        @Override
        public FurnaceTier getTier() {
            return FurnaceTier.AWAKENED_SUPREMIUM;
        }
    }
}
