package com.alex.mysticalagriculture.mixin;


import com.alex.mysticalagriculture.crafting.data.ConditionalRecipeManager;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.command.CommandManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("FieldCanBeLocal")
@Mixin(ServerResourceManager.class)
public class ServerResourceManagerMixin {

    @Shadow @Final private ReloadableResourceManager resourceManager;
    @Unique private ConditionalRecipeManager mysticalagriculture_cRecipeManager;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void initcRecipeManager(CommandManager.RegistrationEnvironment registrationEnvironment, int i, CallbackInfo ci) {
        mysticalagriculture_cRecipeManager = new ConditionalRecipeManager((ServerResourceManager) (Object) this);
        this.resourceManager.registerListener(this.mysticalagriculture_cRecipeManager);
    }
}
