package com.alex.mysticalagriculture.lib;

import com.alex.mysticalagriculture.api.registry.MobSoulTypeRegistry;
import com.alex.mysticalagriculture.api.soul.MobSoulType;
import com.google.common.collect.Sets;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ModMobSoulTypes {
    private static final boolean DEBUG = false;

    private static final List<MobSoulType> mobSoulTypes = new ArrayList<>();

    private static final Set<ResourceLocation> FISH_IDS = Sets.newHashSet(new ResourceLocation("minecraft:cod"), new ResourceLocation("minecraft:salmon"), new ResourceLocation("minecraft:tropical_fish"), new ResourceLocation("minecraft:pufferfish"));
    private static final Set<ResourceLocation> SLIME_IDS = Sets.newHashSet(new ResourceLocation("minecraft:slime"), new ResourceLocation("tconstruct:earth_slime"));
    private static final Set<ResourceLocation> ZOMBIE_IDS = Sets.newHashSet(new ResourceLocation("minecraft:zombie"), new ResourceLocation("minecraft:zombie_villager"));
    private static final Set<ResourceLocation> SPIDER_IDS = Sets.newHashSet(new ResourceLocation("minecraft:spider"), new ResourceLocation("minecraft:cave_spider"));


    public static final MobSoulType PIG = new MobSoulType(new ResourceLocation(MOD_ID, "pig"), new ResourceLocation("minecraft:pig"), 8, 15771042);
    public static final MobSoulType CHICKEN = new MobSoulType(new ResourceLocation(MOD_ID, "chicken"), new ResourceLocation("minecraft:chicken"), 8, 10592673);
    public static final MobSoulType COW = new MobSoulType(new ResourceLocation(MOD_ID, "cow"), new ResourceLocation("minecraft:cow"), 8, 4470310);
    public static final MobSoulType SHEEP = new MobSoulType(new ResourceLocation(MOD_ID, "sheep"), new ResourceLocation("minecraft:sheep"), 8, 15198183);
    public static final MobSoulType SQUID = new MobSoulType(new ResourceLocation(MOD_ID, "squid"), new ResourceLocation("minecraft:squid"), 6, 2243405);
    public static final MobSoulType FISH = new MobSoulType(new ResourceLocation(MOD_ID, "fish"), FISH_IDS, "Fish", 6, 12691306);
    public static final MobSoulType SLIME = new MobSoulType(new ResourceLocation(MOD_ID, "slime"), new ResourceLocation("minecraft:slime"), 12, 5349438);
    public static final MobSoulType TURTLE = new MobSoulType(new ResourceLocation(MOD_ID, "turtle"), new ResourceLocation("minecraft:turtle"), 6, 44975);
    public static final MobSoulType ZOMBIE = new MobSoulType(new ResourceLocation(MOD_ID, "zombie"), ZOMBIE_IDS, "Zombie", 10, 7969893);
    public static final MobSoulType SKELETON = new MobSoulType(new ResourceLocation(MOD_ID, "skeleton"), new ResourceLocation("minecraft:skeleton"), 10, 12698049);
    public static final MobSoulType CREEPER = new MobSoulType(new ResourceLocation(MOD_ID, "creeper"), new ResourceLocation("minecraft:creeper"), 10, 894731);
    public static final MobSoulType SPIDER = new MobSoulType(new ResourceLocation(MOD_ID, "spider"), SPIDER_IDS, "Spider", 10, 3419431);
    public static final MobSoulType RABBIT = new MobSoulType(new ResourceLocation(MOD_ID, "rabbit"), new ResourceLocation("minecraft:rabbit"), 6, 10051392);
    public static final MobSoulType BLAZE = new MobSoulType(new ResourceLocation(MOD_ID, "blaze"), new ResourceLocation("minecraft:blaze"), 10, 16167425);
    public static final MobSoulType GHAST = new MobSoulType(new ResourceLocation(MOD_ID, "ghast"), new ResourceLocation("minecraft:ghast"), 4, 16382457);
    public static final MobSoulType ENDERMAN = new MobSoulType(new ResourceLocation(MOD_ID, "enderman"), new ResourceLocation("minecraft:enderman"), 8, 1447446);
    public static final MobSoulType WITHER = new MobSoulType(new ResourceLocation(MOD_ID, "wither_skeleton"), new ResourceLocation("minecraft:wither_skeleton"), 8, 1315860);

    public static void onRegisterMobSoulTypes(MobSoulTypeRegistry registry) {
        registry.register(PIG);
        registry.register(CHICKEN);
        registry.register(COW);
        registry.register(SHEEP);
        registry.register(SQUID);
        registry.register(FISH);
        registry.register(SLIME);
        registry.register(TURTLE);
        registry.register(ZOMBIE);
        registry.register(SKELETON);
        registry.register(CREEPER);
        registry.register(SPIDER);
        registry.register(RABBIT);
        registry.register(BLAZE);
        registry.register(GHAST);
        registry.register(ENDERMAN);
        registry.register(WITHER);
    }

    private static MobSoulType withRequiredMods(MobSoulType type, String... mods) {
        if (DEBUG) return type;

        boolean enabled = Arrays.stream(mods).anyMatch(FabricLoader.getInstance()::isModLoaded);
        return type.setEnabled(enabled);
    }
}
