package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.api.tinkerer.Augment;
import com.alex.mysticalagriculture.augment.*;
import com.alex.mysticalagriculture.items.AugmentItem;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static com.alex.mysticalagriculture.MysticalAgriculture.ITEM_GROUP;
import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ModAugments {
    private static final List<Augment> augments = new ArrayList<>();


    public static final Augment ABSORPTION_I = new AbsorptionAugment(new Identifier(MOD_ID, "absorption_i"), 1, 0);
    public static final Augment HEALTH_BOOST_I = new HealthBoostAugment(new Identifier(MOD_ID, "health_boost_i"), 1, 1);
    public static final Augment PATHING_AOE_I = new PathingAOEAugment(new Identifier(MOD_ID, "pathing_aoe_i"), 1, 1);

    public static final Augment NIGHT_VISION = new NightVisionAugment(new Identifier(MOD_ID, "night_vision"), 2);
    public static final Augment WATER_BREATHING = new WaterBreathingAugment(new Identifier(MOD_ID, "water_breathing"), 2);
    public static final Augment ABSORPTION_II = new AbsorptionAugment(new Identifier(MOD_ID, "absorption_ii"), 2,  1);
    public static final Augment JUMP_BOOST_I = new JumpBoostAugment(new Identifier(MOD_ID, "jump_boost_i"), 2, 0);
    public static final Augment HEALTH_BOOST_II = new HealthBoostAugment(new Identifier(MOD_ID, "health_boost_ii"), 2, 2);
    public static final Augment SPEED_I = new SpeedAugment(new Identifier(MOD_ID, "speed_i"), 2, 1);
    public static final Augment MINING_AOE_I = new MiningAOEAugment(new Identifier(MOD_ID, "mining_aoe_i"), 2, 1);
    public static final Augment TILLING_AOE_I = new TillingAOEAugment(new Identifier(MOD_ID, "tilling_aoe_i"), 2, 1);
    public static final Augment PATHING_AOE_II = new PathingAOEAugment(new Identifier(MOD_ID, "pathing_aoe_ii"), 2, 2);

    public static final Augment ABSORPTION_III = new AbsorptionAugment(new Identifier(MOD_ID, "absorption_iii"), 3, 2);
    public static final Augment FIRE_RESISTANCE = new FireResistanceAugment(new Identifier(MOD_ID, "fire_resistance"), 3);
    public static final Augment JUMP_BOOST_II = new JumpBoostAugment(new Identifier(MOD_ID, "jump_boost_ii"), 3, 1);
    public static final Augment STEP_ASSIST = new StepAssistAugment(new Identifier(MOD_ID, "step_assist"), 3);
    public static final Augment HEALTH_BOOST_III = new HealthBoostAugment(new Identifier(MOD_ID, "health_boost_iii"), 3, 3);
    public static final Augment STRENGTH_I = new StrengthAugment(new Identifier(MOD_ID, "strength_i"), 3, 1);
    public static final Augment SPEED_II = new SpeedAugment(new Identifier(MOD_ID, "speed_ii"), 3, 2);
    public static final Augment NO_FALL_DAMAGE = new NoFallDamageAugment(new Identifier(MOD_ID, "no_fall_damage"), 3);
    public static final Augment MINING_AOE_II = new MiningAOEAugment(new Identifier(MOD_ID, "mining_aoe_ii"), 3, 2);
    public static final Augment ATTACK_AOE_I = new AttackAOEAugment(new Identifier(MOD_ID, "attack_aoe_i"), 3, 1);
    public static final Augment TILLING_AOE_II = new TillingAOEAugment(new Identifier(MOD_ID, "tilling_aoe_ii"), 3, 2);
    public static final Augment PATHING_AOE_III = new PathingAOEAugment(new Identifier(MOD_ID, "pathing_aoe_iii"), 3, 3);

    public static final Augment ABSORPTION_IV = new AbsorptionAugment(new Identifier(MOD_ID, "absorption_iv"), 4, 3);
    public static final Augment POISON_RESISTANCE = new PoisonResistanceAugment(new Identifier(MOD_ID, "poison_resistance"), 4);
    public static final Augment JUMP_BOOST_III = new JumpBoostAugment(new Identifier(MOD_ID, "jump_boost_iii"), 4, 2);
    public static final Augment HEALTH_BOOST_IV = new HealthBoostAugment(new Identifier(MOD_ID, "health_boost_iv"), 4, 4);
    public static final Augment STRENGTH_II = new StrengthAugment(new Identifier(MOD_ID, "strength_ii"), 4, 2);
    public static final Augment SPEED_III = new SpeedAugment(new Identifier(MOD_ID, "speed_iii"), 4, 3);
    public static final Augment MINING_AOE_III = new MiningAOEAugment(new Identifier(MOD_ID, "mining_aoe_iii"), 4, 3);
    public static final Augment ATTACK_AOE_II = new AttackAOEAugment(new Identifier(MOD_ID, "attack_aoe_ii"), 4, 2);
    public static final Augment TILLING_AOE_III = new TillingAOEAugment(new Identifier(MOD_ID, "tilling_aoe_iii"), 4, 3);
    public static final Augment PATHING_AOE_IV = new PathingAOEAugment(new Identifier(MOD_ID, "pathing_aoe_iv"), 4, 4);
    public static final Augment MINING_FATIGUE_RESISTANCE = new MiningFatigueResistanceAugment(new Identifier(MOD_ID, "mining_fatigue_resistance"), 4);

    public static final Augment ABSORPTION_V = new AbsorptionAugment(new Identifier(MOD_ID, "absorption_v"), 5, 4);
    public static final Augment WITHER_RESISTANCE = new WitherResistanceAugment(new Identifier(MOD_ID, "wither_resistance"), 5);
    public static final Augment HEALTH_BOOST_V = new HealthBoostAugment(new Identifier(MOD_ID, "health_boost_v"), 5, 5);
    public static final Augment STRENGTH_III = new StrengthAugment(new Identifier(MOD_ID, "strength_iii"), 5, 4);
    public static final Augment FLIGHT = new FlightAugment(new Identifier(MOD_ID, "flight"), 5);
    public static final Augment MINING_AOE_IV = new MiningAOEAugment(new Identifier(MOD_ID, "mining_aoe_iv"), 5, 4);
    public static final Augment ATTACK_AOE_III = new AttackAOEAugment(new Identifier(MOD_ID, "attack_aoe_iii"), 5, 3);
    public static final Augment TILLING_AOE_IV = new TillingAOEAugment(new Identifier(MOD_ID, "tilling_aoe_iv"), 5, 4);

    public static void register() {
        augments.add(ATTACK_AOE_I);
        augments.add(ATTACK_AOE_II);
        augments.add(ATTACK_AOE_III);
        augments.add(PATHING_AOE_I);
        augments.add(PATHING_AOE_II);
        augments.add(PATHING_AOE_III);
        augments.add(PATHING_AOE_IV);
        augments.add(TILLING_AOE_I);
        augments.add(TILLING_AOE_II);
        augments.add(TILLING_AOE_III);
        augments.add(TILLING_AOE_IV);
        augments.add(FLIGHT);
        augments.add(ABSORPTION_I);
        augments.add(ABSORPTION_II);
        augments.add(ABSORPTION_III);
        augments.add(ABSORPTION_IV);
        augments.add(ABSORPTION_V);
        augments.add(FIRE_RESISTANCE);
        augments.add(HEALTH_BOOST_I);
        augments.add(HEALTH_BOOST_II);
        augments.add(HEALTH_BOOST_III);
        augments.add(HEALTH_BOOST_IV);
        augments.add(HEALTH_BOOST_V);
        augments.add(JUMP_BOOST_I);
        augments.add(JUMP_BOOST_II);
        augments.add(JUMP_BOOST_III);
        augments.add(MINING_FATIGUE_RESISTANCE);
        augments.add(NIGHT_VISION);
        augments.add(POISON_RESISTANCE);
        augments.add(SPEED_I);
        augments.add(SPEED_II);
        augments.add(SPEED_III);
        augments.add(STEP_ASSIST);
        augments.add(STRENGTH_I);
        augments.add(STRENGTH_II);
        augments.add(STRENGTH_III);
        augments.add(WATER_BREATHING);
        augments.add(WITHER_RESISTANCE);
        augments.add(NO_FALL_DAMAGE);
        augments.add(MINING_AOE_I);
        augments.add(MINING_AOE_II);
        augments.add(MINING_AOE_III);
        augments.add(MINING_AOE_IV);
    }

    public static Augment getAugmentById(Identifier id) {
        return augments.stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public static void registerItems() {
        augments.forEach(a -> {
            Item item = new AugmentItem(a, p -> p.group(ITEM_GROUP));
            Registry.register(Registry.ITEM, new Identifier(MOD_ID, a.getNameWithSuffix("augment")), item);
        });
    }

    public static void onColors() {
        augments.forEach(augment -> {
            if (augment.getItem() != null)
                ColorProviderRegistry.ITEM.register((stack, tint) -> tint == 0 ? augment.getSecondaryColor() : tint == 1 ? augment.getPrimaryColor() : -1, augment.getItem());
        });
    }

}
