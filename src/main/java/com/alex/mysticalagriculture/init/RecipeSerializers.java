package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.crafting.condition.CraftingConditionsImpl;
import com.alex.mysticalagriculture.crafting.ingredient.CropComponentIngredient;
import com.alex.mysticalagriculture.crafting.ingredient.StrictNBTIngredient;
import com.alex.mysticalagriculture.crafting.recipe.*;
import com.alex.mysticalagriculture.cucumber.crafting.recipe.ShapedNoMirrorRecipe;
import com.alex.mysticalagriculture.cucumber.crafting.recipe.ShapedTagRecipe;
import com.alex.mysticalagriculture.cucumber.crafting.recipe.ShapedTransferDamageRecipe;
import com.alex.mysticalagriculture.cucumber.crafting.recipe.ShapelessTagRecipe;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RecipeSerializers {

    public static final RecipeSerializer<FarmlandTillRecipe> CRAFTING_FARMLAND_TILL = new FarmlandTillRecipe.Serializer();
    public static final RecipeSerializer<InfusionRecipe> INFUSION = new InfusionRecipe.Serializer();
    public static final RecipeSerializer<AwakeningRecipe> AWAKENING = new AwakeningRecipe.Serializer();
    public static final RecipeSerializer<ReprocessorRecipe> REPROCESSOR = new ReprocessorRecipe.Serializer();
    public static final RecipeSerializer<SoulExtractionRecipe> SOUL_EXTRACTION = new SoulExtractionRecipe.Serializer();

    public static final RecipeSerializer<SoulJarEmptyRecipe> CRAFTING_SOUL_JAR_EMPTY = new SoulJarEmptyRecipe.Serializer();
    //public static final RecipeSerializer<ShapedRecipe> INFUSION_CRYSTAL = new InfusionCrystalRecipe.InfusionCrystalRecipeSerializer();
    public static final RecipeSerializer<InfusionCrystalRecipe> INFUSION_CRYSTAL = new InfusionCrystalRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_NO_MIRROR = new ShapedNoMirrorRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_TRANSFER_DAMAGE = new ShapedTransferDamageRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_TAG = new ShapedTagRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPELESS_TAG = new ShapelessTagRecipe.Serializer();

    public static final CustomIngredientSerializer<CropComponentIngredient> CROP_COMPONENT_INGREDIENT = new CropComponentIngredient.Serializer();
    public static final CustomIngredientSerializer<StrictNBTIngredient> STRICT_NBT_INGREDIENT = new StrictNBTIngredient.Serializer();


    public static final Identifier CROP_ENABLED = new Identifier(MysticalAgriculture.MOD_ID, "crop_enabled");
    public static final Identifier CROP_HAS_MATERIAL = new Identifier(MysticalAgriculture.MOD_ID, "crop_has_material");

    public static ConditionJsonProvider cropEnabled(ConditionJsonProvider value) {
        return CraftingConditionsImpl.cropEnabled(CROP_ENABLED, value);

    }
    public static void registerRecipeSerializers() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:farmland_till"), CRAFTING_FARMLAND_TILL);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:infusion"), INFUSION);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:awakening"), AWAKENING);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:reprocessor"), REPROCESSOR);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:soul_extraction"), SOUL_EXTRACTION);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:soul_jar_empty"), CRAFTING_SOUL_JAR_EMPTY);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:infusion_crystal"), INFUSION_CRYSTAL);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:shaped_no_mirror"), CRAFTING_SHAPED_NO_MIRROR);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:shaped_transfer_damage"), CRAFTING_SHAPED_TRANSFER_DAMAGE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:shaped_tag"), CRAFTING_SHAPED_TAG);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mysticalagriculture:shapeless_tag"), CRAFTING_SHAPELESS_TAG);

        CustomIngredientSerializer.register(CROP_COMPONENT_INGREDIENT);
        CustomIngredientSerializer.register(STRICT_NBT_INGREDIENT);

        ResourceConditions.register(CROP_ENABLED, CraftingConditionsImpl::cropEnabledMatch);
        ResourceConditions.register(CROP_HAS_MATERIAL, CraftingConditionsImpl::cropHasMaterialMatch);
    }
}
