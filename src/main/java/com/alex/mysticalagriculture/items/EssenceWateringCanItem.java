package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.cucumber.helper.NBTHelper;
import com.google.common.base.Function;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class EssenceWateringCanItem extends WateringCanItem {
    private final Formatting textColor;

    public EssenceWateringCanItem(int range, double chance, Formatting textColor, Function<Settings, Settings> settings) {
        super(range, chance, settings.compose(p -> p.maxCount(1)));
        this.textColor = textColor;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (selected && NBTHelper.getBoolean(stack, "Active") && entity instanceof PlayerEntity player) {
            var result = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);

            if (result.getType() != HitResult.Type.MISS) {
                this.doWater(stack, world, player, result.getBlockPos(), result.getSide());
            }
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return NBTHelper.getBoolean(stack, "Active");
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        var stack = player.getStackInHand(hand);
        var trace = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);

        if (trace.getType() != HitResult.Type.BLOCK) {
            if (NBTHelper.getBoolean(stack, "Water") && player.isInSneakingPose()) {
                NBTHelper.flipBoolean(stack, "Active");
            }

            return new TypedActionResult<>(ActionResult.PASS, stack);
        }

        if (NBTHelper.getBoolean(stack, "Water")) {
            return new TypedActionResult<>(ActionResult.PASS, stack);
        }

        var pos = trace.getBlockPos();
        var direction = trace.getSide();

        if (world.canPlayerModifyAt(player, pos) && player.canPlaceOn(pos.offset(direction), direction, stack)) {
            var state = world.getBlockState(pos);

            if (state.getMaterial() == Material.WATER) {
                NBTHelper.setString(stack, "ID", UUID.randomUUID().toString());
                NBTHelper.setBoolean(stack, "Water", true);

                player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);

                return new TypedActionResult<>(ActionResult.SUCCESS, stack);
            }
        }

        return new TypedActionResult<>(ActionResult.PASS, stack);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var player = context.getPlayer();

        if (player == null)
            return ActionResult.PASS;

        if (NBTHelper.getBoolean(this.getDefaultStack(), "Active"))
            return ActionResult.PASS;

        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        var rangeString = String.valueOf(this.range);
        var rangeNumber = Text.literal(rangeString + "x" + rangeString).formatted(this.textColor);

        tooltip.add(ModTooltips.TOOL_AREA.args(rangeNumber).build());
    }
}
