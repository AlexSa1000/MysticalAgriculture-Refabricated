package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.crafting.condition.CraftingConditionsImpl;
import com.alex.mysticalagriculture.crafting.ingredient.CropComponentIngredient;
import com.alex.mysticalagriculture.crafting.ingredient.StrictNBTIngredient;
import com.alex.mysticalagriculture.crafting.recipe.*;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {

    public static final RecipeSerializer<FarmlandTillRecipe> CRAFTING_FARMLAND_TILL = new FarmlandTillRecipe.Serializer();
    public static final RecipeSerializer<InfusionRecipe> INFUSION = new InfusionRecipe.Serializer();
    public static final RecipeSerializer<AwakeningRecipe> AWAKENING = new AwakeningRecipe.Serializer();
    public static final RecipeSerializer<ReprocessorRecipe> REPROCESSOR = new ReprocessorRecipe.Serializer();
    public static final RecipeSerializer<SoulExtractionRecipe> SOUL_EXTRACTION = new SoulExtractionRecipe.Serializer();
    public static final RecipeSerializer<SoulJarEmptyRecipe> CRAFTING_SOUL_JAR_EMPTY = new SoulJarEmptyRecipe.Serializer();

    public static final CustomIngredientSerializer<CropComponentIngredient> CROP_COMPONENT_INGREDIENT = new CropComponentIngredient.Serializer();
    public static final CustomIngredientSerializer<StrictNBTIngredient> STRICT_NBT_INGREDIENT = new StrictNBTIngredient.Serializer();

    public static final ResourceLocation CROP_ENABLED = new ResourceLocation(MysticalAgriculture.MOD_ID, "crop_enabled");
    public static final ResourceLocation CROP_HAS_MATERIAL = new ResourceLocation(MysticalAgriculture.MOD_ID, "crop_has_material");

    public static void registerRecipeSerializers() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("mysticalagriculture:farmland_till"), CRAFTING_FARMLAND_TILL);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("mysticalagriculture:infusion"), INFUSION);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("mysticalagriculture:awakening"), AWAKENING);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("mysticalagriculture:reprocessor"), REPROCESSOR);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("mysticalagriculture:soul_extraction"), SOUL_EXTRACTION);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("mysticalagriculture:soul_jar_empty"), CRAFTING_SOUL_JAR_EMPTY);

        CustomIngredientSerializer.register(CROP_COMPONENT_INGREDIENT);
        CustomIngredientSerializer.register(STRICT_NBT_INGREDIENT);

        ResourceConditions.register(CROP_ENABLED, CraftingConditionsImpl::cropEnabledMatch);
        ResourceConditions.register(CROP_HAS_MATERIAL, CraftingConditionsImpl::cropHasMaterialMatch);
    }
}