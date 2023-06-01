package com.alex.mysticalagriculture.cucumber.item.tool;

import com.alex.mysticalagriculture.cucumber.iface.Enableable;
import com.alex.mysticalagriculture.forge.common.ForgeHooks;
import com.alex.mysticalagriculture.forge.common.extensions.ForgeItem;
import com.alex.mysticalagriculture.mixin.CropBlockInvoker;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

public class BaseScytheItem extends SwordItem implements ForgeItem {
    private final float attackDamage;
    private final float attackSpeed;
    private final int range;

    public BaseScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, int range, Function<Settings, Settings> settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.apply(new Settings()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.range = range;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this instanceof Enableable enableable) {
            if (enableable.isEnabled())
                super.appendStacks(group, stacks);
        } else {
            super.appendStacks(group, stacks);
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var player = context.getPlayer();

        if (player == null)
            return ActionResult.FAIL;

        var pos = context.getBlockPos();
        var hand = context.getHand();
        var face = context.getSide();
        var stack = player.getStackInHand(hand);

        if (!player.canPlaceOn(pos.offset(face), face, stack))
            return ActionResult.FAIL;

        var world = context.getWorld();

        if (world.isClient())
            return ActionResult.SUCCESS;

        BlockPos.iterate(pos.add(-this.range, 0, -this.range), pos.add(this.range, 0, this.range)).forEach(aoePos -> {
            if (stack.isEmpty())
                return;

            var state = world.getBlockState(aoePos);
            /*var event = new ScytheHarvestCropEvent(world, aoePos, state, stack, player);

            if (MinecraftForge.EVENT_BUS.post(event))
                return;*/

            var block = state.getBlock();

            if (block instanceof CropBlock crop) {
                Item seed = (Item) ((CropBlockInvoker) block).invokerGetSeedsItem();

                if (crop.isMature(state) && seed != null) {
                    var drops = Block.getDroppedStacks(state, (ServerWorld) world, aoePos, world.getBlockEntity(aoePos));

                    for (var drop : drops) {
                        var item = drop.getItem();

                        if (!drop.isEmpty() && item == seed) {
                            drop.decrement(1);
                            break;
                        }
                    }

                    for (var drop : drops) {
                        if (!drop.isEmpty()) {
                            Block.dropStack(world, aoePos, drop);
                        }
                    }

                    stack.damage(1, player, entity -> {
                        entity.sendToolBreakStatus(player.getActiveHand());
                    });

                    world.setBlockState(aoePos, crop.withAge(0));
                }
            }
        });

        return ActionResult.SUCCESS;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (player.getAttackCooldownProgress(0.5F) >= 0.95F) {
            var world = player.world;
            var range = (this.range >= 2 ? 1.0D + (this.range - 1) * 0.25D : 1.0D);
            var entities = world.getNonSpectatingEntities(LivingEntity.class, entity.getBoundingBox().expand(range, 0.25D, range));

            for (var aoeEntity : entities) {
                if (aoeEntity != player && aoeEntity != entity && !player.isTeammate(entity)) {
                    var source = DamageSource.player(player);
                    var attackDamage = this.getAttackDamage() * 0.67F;
                    //var success = ForgeHooks.onLivingAttack(aoeEntity, source, attackDamage);

                    aoeEntity.takeKnockback(0.4F, MathHelper.sin(player.getYaw() * 0.017453292F), -MathHelper.cos(player.getYaw() * 0.017453292F));
                    aoeEntity.damage(source, attackDamage);
                }
            }

            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);

            player.spawnSweepAttackParticles();
        }

        return ForgeItem.super.onLeftClickEntity(stack, player, entity);
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getMaterial().getAttackDamage();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}
