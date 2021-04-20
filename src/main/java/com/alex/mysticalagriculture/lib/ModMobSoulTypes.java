package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.google.common.collect.Sets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ModMobSoulTypes {
    private static final List<MobSoulType> mobSoulTypes = new ArrayList<>();

    private static final Set<Identifier> FISH_IDS = Sets.newHashSet(new Identifier("minecraft:cod"), new Identifier("minecraft:salmon"), new Identifier("minecraft:tropical_fish"), new Identifier
            ("minecraft:pufferfish"));
    private static final Set<Identifier> ZOMBIE_IDS = Sets.newHashSet(new Identifier("minecraft:zombie"), new Identifier("minecraft:zombie_villager"));
    private static final Set<Identifier> SPIDER_IDS = Sets.newHashSet(new Identifier("minecraft:spider"), new Identifier("minecraft:cave_spider"));


    public static final MobSoulType PIG = new MobSoulType(new Identifier(MOD_ID, "pig"), new Identifier("minecraft:pig"), 8, 15771042);
    public static final MobSoulType CHICKEN = new MobSoulType(new Identifier(MOD_ID, "chicken"), new Identifier("minecraft:chicken"), 8, 10592673);
    public static final MobSoulType COW = new MobSoulType(new Identifier(MOD_ID, "cow"), new Identifier("minecraft:cow"), 8, 4470310);
    public static final MobSoulType SHEEP = new MobSoulType(new Identifier(MOD_ID, "sheep"), new Identifier("minecraft:sheep"), 8, 15198183);
    public static final MobSoulType SQUID = new MobSoulType(new Identifier(MOD_ID, "squid"), new Identifier("minecraft:squid"), 6, 2243405);
    public static final MobSoulType FISH = new MobSoulType(new Identifier(MOD_ID, "fish"), FISH_IDS, "Fish", 6, 12691306);
    public static final MobSoulType SLIME = new MobSoulType(new Identifier(MOD_ID, "slime"), new Identifier("minecraft:slime"), 12, 5349438);
    public static final MobSoulType TURTLE = new MobSoulType(new Identifier(MOD_ID, "turtle"), new Identifier("minecraft:turtle"), 6, 44975);
    public static final MobSoulType ZOMBIE = new MobSoulType(new Identifier(MOD_ID, "zombie"), ZOMBIE_IDS, "Zombie", 10, 7969893);
    public static final MobSoulType SKELETON = new MobSoulType(new Identifier(MOD_ID, "skeleton"), new Identifier("minecraft:skeleton"), 10, 12698049);
    public static final MobSoulType CREEPER = new MobSoulType(new Identifier(MOD_ID, "creeper"), new Identifier("minecraft:creeper"), 10, 894731);
    public static final MobSoulType SPIDER = new MobSoulType(new Identifier(MOD_ID, "spider"), SPIDER_IDS, "Spider", 10, 3419431);
    public static final MobSoulType RABBIT = new MobSoulType(new Identifier(MOD_ID, "rabbit"), new Identifier("minecraft:rabbit"), 6, 10051392);
    public static final MobSoulType BLAZE = new MobSoulType(new Identifier(MOD_ID, "blaze"), new Identifier("minecraft:blaze"), 10, 16167425);
    public static final MobSoulType GHAST = new MobSoulType(new Identifier(MOD_ID, "ghast"), new Identifier("minecraft:ghast"), 4, 16382457);
    public static final MobSoulType ENDERMAN = new MobSoulType(new Identifier(MOD_ID, "enderman"), new Identifier("minecraft:enderman"), 8, 1447446);
    public static final MobSoulType WITHER = new MobSoulType(new Identifier(MOD_ID, "wither_skeleton"), new Identifier("minecraft:wither_skeleton"), 8, 1315860);

    public static void register() {
        mobSoulTypes.add(PIG);
        mobSoulTypes.add(CHICKEN);
        mobSoulTypes.add(COW);
        mobSoulTypes.add(SHEEP);
        mobSoulTypes.add(SQUID);
        mobSoulTypes.add(FISH);
        mobSoulTypes.add(SLIME);
        mobSoulTypes.add(TURTLE);
        mobSoulTypes.add(ZOMBIE);
        mobSoulTypes.add(SKELETON);
        mobSoulTypes.add(CREEPER);
        mobSoulTypes.add(SPIDER);
        mobSoulTypes.add(RABBIT);
        mobSoulTypes.add(BLAZE);
        mobSoulTypes.add(GHAST);
        mobSoulTypes.add(ENDERMAN);
        mobSoulTypes.add(WITHER);
    }

    public static List<MobSoulType> getMobSoulTypes() {
        return mobSoulTypes;
    }

    public static MobSoulType getMobSoulTypeById(Identifier id) {
        return mobSoulTypes.stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
    }

    public static MobSoulType getMobSoulTypeByEntity(LivingEntity entity) {
        return mobSoulTypes.stream().filter(t -> t.isEntityApplicable(entity)).findFirst().orElse(null);
    }
}
