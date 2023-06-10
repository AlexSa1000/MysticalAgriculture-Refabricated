package com.alex.mysticalagriculture.mixin;

import com.alex.cucumber.helper.RecipeHelper;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin {

    @Inject(method = "loadResources", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/SimpleReloadInstance;create(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/server/packs/resources/ReloadInstance;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void injectMethod(ResourceManager resourceManager, RegistryAccess.Frozen frozen, Commands.CommandSelection commandSelection, int i, Executor executor, Executor executor2, CallbackInfoReturnable<CompletableFuture<ReloadableServerResources>> cir, ReloadableServerResources reloadableServerResources) {
        RecipeHelper.recipeManager = reloadableServerResources.getRecipeManager();
    }
}
