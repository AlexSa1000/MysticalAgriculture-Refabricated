package com.alex.mysticalagriculture.mixin;

import com.google.gson.Gson;
import com.alex.mysticalagriculture.crafting.data.ConditionalRecipeManager;
import net.minecraft.resource.JsonDataLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(JsonDataLoader.class)
public class JsonDataLoaderMixin {

    @Shadow @Final @Mutable private String dataType;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void modifyS(Gson gson, String dataType, CallbackInfo ci) {
        if((Object) this instanceof ConditionalRecipeManager) {
            this.dataType = "conditionalrecipes";
        }
    }
}
