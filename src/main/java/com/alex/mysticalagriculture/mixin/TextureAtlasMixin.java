package com.alex.mysticalagriculture.mixin;

import com.alex.cucumber.forge.client.ForgeHooksClient;
import com.alex.mysticalagriculture.client.ModelHandler;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Set;
import java.util.stream.Stream;

@Mixin(TextureAtlas.class)
public class TextureAtlasMixin {

    @Inject(method = "prepareToStitch", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlas;getBasicSpriteInfos(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/Set;)Ljava/util/Collection;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onTextureStitchedPre(ResourceManager resourceManager, Stream<ResourceLocation> stream, ProfilerFiller profilerFiller, int i, CallbackInfoReturnable<TextureAtlas.Preparations> cir, Set set) {
        ModelHandler.onTextureStitch((TextureAtlas) ((Object) this), set);
    }
}
