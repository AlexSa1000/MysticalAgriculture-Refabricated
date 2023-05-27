package com.alex.mysticalagriculture.blockentities;

import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.util.FurnaceTier;
import com.alex.mysticalagriculture.cucumber.util.Localizable;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class EssenceFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    public EssenceFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, RecipeType.SMELTING);
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
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areNbtEqual(stack, itemStack);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        if (slot == 0 && !bl) {
            this.cookTimeTotal = (int) (getCookTime(world, this) * this.getTier().getCookTimeMultiplier());
            this.cookTime = 0;
            this.markDirty();
        }
    }

    protected boolean canAcceptRecipeOutput(Recipe<?> recipe, DefaultedList<ItemStack> items, int maxStackSize) {
        if (!items.get(0).isEmpty() && recipe != null) {
            ItemStack itemstack = ((Recipe<SidedInventory>) recipe).craft(this, this.world.getRegistryManager());
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = items.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= maxStackSize && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxCount()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxCount(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    protected boolean craftRecipe(Recipe<?> recipe, DefaultedList<ItemStack> items, int maxStackSize) {
        if (recipe != null && this.canAcceptRecipeOutput(recipe, items, maxStackSize)) {
            ItemStack itemstack = items.get(0);
            ItemStack itemstack1 = ((Recipe<SidedInventory>) recipe).craft(this, this.world.getRegistryManager());
            ItemStack itemstack2 = items.get(2);
            if (itemstack2.isEmpty()) {
                items.set(2, itemstack1.copy());
            } else if (itemstack2.isOf(itemstack1.getItem())) {
                itemstack2.increment(itemstack1.getCount());
            }

            if (itemstack.isOf(Blocks.WET_SPONGE.asItem()) && !items.get(1).isEmpty() && items.get(1).isOf(Items.BUCKET)) {
                items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.decrement(1);
            return true;
        } else {
            return false;
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, EssenceFurnaceBlockEntity block) {
        var flag = block.isBurning();
        var flag1 = false;

        if (block.isBurning()) {
            --block.burnTime;
        }
        var stack = block.inventory.get(1);
        if (block.isBurning() || !stack.isEmpty() && !block.inventory.get(0).isEmpty()) {
            Recipe<?> recipe = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, block, world).orElse(null);
            int i = block.getMaxCountPerStack();
            if (!block.isBurning() && block.canAcceptRecipeOutput(recipe, block.inventory, i)) {
                block.burnTime = block.getFuelTime(stack);
                block.fuelTime = block.burnTime;
                if (block.isBurning()) {
                    flag1 = true;

                    if (!stack.isEmpty()) {
                        stack.decrement(1);

                        if (stack.isEmpty()) {
                            block.inventory.set(1, stack.getRecipeRemainder());
                        }
                    }
                }
            }

            if (block.isBurning() && block.canAcceptRecipeOutput(recipe, block.inventory, i)) {
                ++block.cookTime;
                if (block.cookTime == block.cookTimeTotal) {
                    block.cookTime = 0;
                    block.cookTimeTotal = (int) (getCookTime(world, block) * block.getTier().getCookTimeMultiplier());
                    if (block.craftRecipe(recipe, block.inventory, i)) {
                        block.setLastRecipe(recipe);
                    }

                }
            } else {
                block.cookTime = 0;
            }
        } else if (!block.isBurning() && block.cookTime > 0) {
            block.cookTime = MathHelper.clamp(block.cookTime - 2, 0, block.cookTimeTotal);
        }

        if (flag != block.isBurning()) {
            flag1 = true;
            state = state.with(AbstractFurnaceBlock.LIT, block.isBurning());
            world.setBlockState(pos, state, 3);
        }

        if (flag1) {
            markDirty(world, pos, state);
        }
    }

    private static int getCookTime(World world, AbstractFurnaceBlockEntity furnace) {
        return furnace.matchGetter.getFirstMatch(furnace, world).map(AbstractCookingRecipe::getCookTime).orElse(200);
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
