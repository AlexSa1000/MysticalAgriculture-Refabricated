package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.init.RecipeTypes;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    @Inject(method = "getGroupForRecipe", at = @At(value = "HEAD"), cancellable = true)
    private static void addRecipeGroups(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        if (recipe.getType() == RecipeTypes.INFUSION || recipe.getType() == RecipeTypes.REPROCESSOR || recipe.getType() == RecipeTypes.INFUSION_CRYSTAL) {
            cir.setReturnValue(RecipeBookGroup.UNKNOWN);
        }
    }
}
