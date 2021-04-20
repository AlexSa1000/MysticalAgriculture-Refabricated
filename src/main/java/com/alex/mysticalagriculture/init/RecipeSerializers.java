package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.crafting.recipe.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipeSerializers {

    public static final RecipeSerializer<FarmlandTillRecipe> CRAFTING_FARMLAND_TILL = new FarmlandTillRecipe.Serializer();
    public static final RecipeSerializer<InfusionRecipe> INFUSION = new InfusionRecipe.InfusionRecipeSerializer();
    public static final RecipeSerializer<ReprocessorRecipe> REPROCESSOR = new ReprocessorRecipe.Serializer();
    public static final RecipeSerializer<SoulJarEmptyRecipe> CRAFTING_SOUL_JAR_EMPTY = new SoulJarEmptyRecipe.Serializer();
    public static final RecipeSerializer<ShapedRecipe> INFUSION_CRYSTAL = new InfusionCrystalRecipe.InfusionCrystalRecipeSerializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_NO_MIRROR = new ShapedNoMirrorRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_TRANSFER_DAMAGE = new ShapedTransferDamageRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_TAG = new TagRecipe.Serializer();

    public static void registerRecipeSerializers() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:farmland_till"), CRAFTING_FARMLAND_TILL);
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:infusion"), INFUSION);
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:reprocessor"), REPROCESSOR);
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:soul_jar_empty"), CRAFTING_SOUL_JAR_EMPTY);
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:infusion_crystal"), INFUSION_CRYSTAL);
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:shaped_no_mirror"), CRAFTING_SHAPED_NO_MIRROR);
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:shaped_transfer_damage"), CRAFTING_SHAPED_TRANSFER_DAMAGE);
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:tag"), CRAFTING_TAG);

    }
}
