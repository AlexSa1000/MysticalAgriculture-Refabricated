package com.alex.mysticalagriculture;

import com.alex.mysticalagriculture.client.blockentity.InfusionAltarRenderer;
import com.alex.mysticalagriculture.client.blockentity.InfusionPedestalRenderer;
import com.alex.mysticalagriculture.client.blockentity.TinkeringTableRenderer;
import com.alex.mysticalagriculture.client.screen.ReprocessorScreen;
import com.alex.mysticalagriculture.client.screen.TinkeringTableScreen;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.init.Blocks;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.init.ScreenHandlerTypes;
import com.alex.mysticalagriculture.items.ExperienceCapsuleItem;
import com.alex.mysticalagriculture.items.SoulJarItem;
import com.alex.mysticalagriculture.lib.ModAugments;
import com.alex.mysticalagriculture.lib.ModCrops;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class MysticalAgricultureClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntities.INFUSION_PEDESTAL, InfusionPedestalRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntities.INFUSION_ALTAR, InfusionAltarRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntities.TINKERING_TABLE, TinkeringTableRenderer::new);

        FabricModelPredicateProviderRegistry.register(Items.SOUL_JAR, new Identifier("fill"), SoulJarItem.getFillPropertyGetter());
        FabricModelPredicateProviderRegistry.register(Items.EXPERIENCE_CAPSULE, new Identifier("fill"), ExperienceCapsuleItem.getFillPropertyGetter());
        ScreenRegistry.register(ScreenHandlerTypes.REPROCESSOR, ReprocessorScreen::new);
        ScreenRegistry.register(ScreenHandlerTypes.TINKERING_TABLE, TinkeringTableScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), Blocks.INFERIUM_ORE, Blocks.PROSPERITY_ORE, Blocks.SOULIUM_ORE);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), Blocks.SOUL_GLASS, Blocks.WITHERPROOF_GLASS);

        Blocks.onColors();
        Items.onColors();
        ModAugments.onColors();
        ModCrops.onColors();
    }
}
