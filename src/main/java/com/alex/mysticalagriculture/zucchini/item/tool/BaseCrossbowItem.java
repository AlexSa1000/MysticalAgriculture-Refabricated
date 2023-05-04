package com.alex.mysticalagriculture.zucchini.item.tool;

import com.alex.mysticalagriculture.zucchini.iface.CustomBow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
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
}
