package com.alex.mysticalagriculture.items;

import com.alex.mysticalagriculture.lib.ModTooltips;
import com.alex.mysticalagriculture.util.helper.NBTHelper;
import com.alex.mysticalagriculture.util.item.BaseItem;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class WateringCanItem extends BaseItem {
    private static final Map<String, Long> THROTTLES = new HashMap<>();
    protected final int range;
    protected final double chance;

    public WateringCanItem(Function<Settings, Settings> settings) {
        this(3, 0.25, settings);
    }

    public WateringCanItem(int range, double chance, Function<Settings, Settings> settings) {
        super(settings.compose(p -> p.maxCount(1)));
        this.range = range;
        this.chance = chance;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
        if (this.isIn(group)) {
            ItemStack stack = new ItemStack(this);
            NBTHelper.setBoolean(stack, "Water", false);
            items.add(stack);
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (NBTHelper.getBoolean(stack, "Water")) {
            return new TypedActionResult<>(ActionResult.PASS, stack);
        }

        BlockHitResult trace = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (trace.getType() != HitResult.Type.BLOCK) {
            return new TypedActionResult<>(ActionResult.PASS, stack);
        }

        BlockPos pos = trace.getBlockPos();
        Direction direction = trace.getSide();
        if (world.canPlayerModifyAt(player, pos) && player.canPlaceOn(pos.offset(direction), direction, stack)) {
            BlockState state = world.getBlockState(pos);
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
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (player == null)
            return ActionResult.FAIL;

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Direction direction = context.getSide();

        if (!player.canPlaceOn(pos.offset(direction), direction, stack)) {
            return ActionResult.FAIL;
        }

        if (!NBTHelper.getBoolean(stack, "Water")) {
            return ActionResult.PASS;
        }

        return this.doWater(stack, world, player, pos, direction);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (NBTHelper.getBoolean(stack, "Water")) {
            tooltip.add(ModTooltips.FILLED.build());
        } else {
            tooltip.add(ModTooltips.EMPTY.build());
        }
    }

    protected ActionResult doWater(ItemStack stack, World world, PlayerEntity player, BlockPos pos, Direction direction) {
        if (player == null)
            return ActionResult.FAIL;

        if (!player.canPlaceOn(pos.offset(direction), direction, stack))
            return ActionResult.FAIL;

        if (!NBTHelper.getBoolean(stack, "Water"))
            return ActionResult.PASS;

        if (!world.isClient()) {
            String id = getID(stack);
            long throttle = THROTTLES.getOrDefault(id, 0L);
            if (world.getTime() - throttle < 5L)
                return ActionResult.PASS;

            THROTTLES.put(id, world.getTime());
        }

        int range = (this.range - 1) / 2;
        Stream<BlockPos> blocks = BlockPos.stream(pos.add(-range, -range, -range), pos.add(range, range, range));
        blocks.forEach(aoePos -> {
            BlockState aoeState = world.getBlockState(aoePos);
            if (aoeState.getBlock() instanceof FarmlandBlock) {
                int moisture = aoeState.get(FarmlandBlock.MOISTURE);
                if (moisture < 7) {
                    world.setBlockState(aoePos, aoeState.with(FarmlandBlock.MOISTURE, 7), 3);
                }
            }
        });

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                double d0 = pos.add(x, 0, z).getX() + world.getRandom().nextFloat();
                double d1 = pos.add(x, 0, z).getY() + 1.0D;
                double d2 = pos.add(x, 0, z).getZ() + world.getRandom().nextFloat();

                BlockState state = world.getBlockState(pos);
                if (state.isOpaque() || state.getBlock() instanceof FarmlandBlock)
                    d1 += 0.3D;

                world.addParticle(ParticleTypes.RAIN, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

        if (!world.isClient()) {
            if (Math.random() <= this.chance) {
                blocks = BlockPos.stream(pos.add(-range, -range, -range), pos.add(range, range, range));
                blocks.forEach(aoePos -> {
                    BlockState state = world.getBlockState(aoePos);
                    Block plantBlock = state.getBlock();
                    if (plantBlock instanceof Fertilizable || plantBlock instanceof PlantBlock || plantBlock instanceof CactusBlock || plantBlock instanceof SugarCaneBlock || plantBlock == Blocks.MYCELIUM || plantBlock == Blocks.CHORUS_FLOWER) {
                        state.randomTick((ServerWorld) world, aoePos, new Random());
                    }
                });

                return ActionResult.PASS;
            }
        }
        return ActionResult.PASS;
    }

    private static String getID(ItemStack stack) {
        if (!NBTHelper.hasKey(stack, "ID")) {
            NBTHelper.setString(stack, "ID", UUID.randomUUID().toString());
        }

        return NBTHelper.getString(stack, "ID");
    }
}
