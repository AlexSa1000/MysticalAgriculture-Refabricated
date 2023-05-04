package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.zucchini.item.BaseItem;
import com.alex.mysticalagriculture.zucchini.util.Utils;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ExperienceDropletItem extends BaseItem {
    public ExperienceDropletItem() {
        super();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        int used = 0;

        if (!world.isClient()) {
            if (player.isInSneakingPose()) {
                int xp = 0;
                for (int i = 0; i < stack.getCount(); i++) {
                    xp += Utils.randInt(8, 12);
                }

                var orb = new ExperienceOrbEntity(world, player.getX(), player.getY(), player.getZ(), xp);

                world.spawnEntity(orb);

                used = stack.getCount();
            } else {
                int xp = Utils.randInt(8, 12);
                var orb = new ExperienceOrbEntity(world, player.getX(), player.getY(), player.getZ(), xp);

                world.spawnEntity(orb);

                used = 1;
            }
        }

        if (!player.isCreative())
            stack.decrement(used);

        return new TypedActionResult<>(ActionResult.SUCCESS, stack);
    }
}
