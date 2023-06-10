package com.alex.mysticalagriculture.mixin;

import com.alex.mysticalagriculture.init.RecipeTypes;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    @Inject(method = "getCategory", at = @At(value = "HEAD"), cancellable = true)
    private static void addRecipeGroups(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookCategories> cir) {
        if (recipe.getType() == RecipeTypes.INFUSION || recipe.getType() == RecipeTypes.AWAKENING || recipe.getType() == RecipeTypes.REPROCESSOR || recipe.getType() == RecipeTypes.SOUL_EXTRACTION /*|| recipe.getType() == RecipeTypes.INFUSION_CRYSTAL*/) {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
    }
}
