package com.alex.mysticalagriculture.cucumber.item.tool;

import com.alex.mysticalagriculture.cucumber.iface.CustomBow;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.function.Function;

public class BaseCrossbowItem extends CrossbowItem implements CustomBow {
    public BaseCrossbowItem(Function<Settings, Settings> settings) {
        super(settings.apply(new Settings()));
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int i = (int) ((this.getMaxUseTime(stack) - remainingUseTicks) * this.getDrawSpeedMulti(stack));
        float f = CrossbowItem.getPullProgress(i, stack);
        if (f >= 1.0f && !CrossbowItem.isCharged(stack) && CrossbowItem.loadProjectiles(user, stack)) {
            CrossbowItem.setCharged(stack, true);
            SoundCategory soundCategory = user instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, soundCategory, 1.0f, 1.0f / (world.getRandom().nextFloat() * 0.5f + 1.0f) + 0.2f);
        }
    }

    @Override
    public boolean hasFOVChange() {
        return false;
    }

    private static float getPowerForTime(int power, ItemStack stack) {
        float f = (float)power / (float)getPullTime(stack);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    private static boolean tryLoadProjectiles(LivingEntity entity, ItemStack stack) {
        int i = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, stack);
        int j = i == 0 ? 1 : 3;
        boolean flag = entity instanceof PlayerEntity && ((PlayerEntity)entity).getAbilities().creativeMode;
        ItemStack itemstack = entity.getProjectileType(stack);
        ItemStack itemstack1 = itemstack.copy();

        for(int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!loadProjectile(entity, stack, itemstack, k > 0, flag)) {
                return false;
            }
        }

        return true;
    }

    private static boolean loadProjectile(LivingEntity entity, ItemStack bow, ItemStack arrow, boolean p_40866_, boolean p_40867_) {
        if (arrow.isEmpty()) {
            return false;
        } else {
            boolean flag = p_40867_ && arrow.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            if (!flag && !p_40867_ && !p_40866_) {
                itemstack = arrow.split(1);
                if (arrow.isEmpty() && entity instanceof PlayerEntity) {
                    ((PlayerEntity)entity).getInventory().removeOne(arrow);
                }
            } else {
                itemstack = arrow.copy();
            }

            addChargedProjectile(bow, itemstack);
            return true;
        }
    }

    private static void addChargedProjectile(ItemStack p_40929_, ItemStack p_40930_) {
        NbtCompound compoundtag = p_40929_.getOrCreateNbt();
        NbtList listtag;
        if (compoundtag.contains("ChargedProjectiles", 9)) {
            listtag = compoundtag.getList("ChargedProjectiles", 10);
        } else {
            listtag = new NbtList();
        }

        NbtCompound compoundtag1 = new NbtCompound();
        p_40930_.writeNbt(compoundtag1);
        listtag.add(compoundtag1);
        compoundtag.put("ChargedProjectiles", listtag);
    }

    public static ClampedModelPredicateProvider getPullPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return isCharged(stack) ? 0.0F : (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) * ((CustomBow)stack.getItem()).getDrawSpeedMulti(stack) / (float)getPullTime(stack);
            }
        };
    }

    public static ClampedModelPredicateProvider getPullingPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
        };
    }

    public static ClampedModelPredicateProvider getChargedPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            return entity != null && isCharged(stack) ? 1.0F : 0.0F;
        };
    }

    public static ClampedModelPredicateProvider getFireworkPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            return entity != null && isCharged(stack) && hasProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        };
    }
}
