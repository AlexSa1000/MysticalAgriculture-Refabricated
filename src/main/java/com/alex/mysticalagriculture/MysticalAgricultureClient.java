package com.alex.mysticalagriculture;

import com.alex.mysticalagriculture.client.blockentity.*;
import com.alex.mysticalagriculture.client.handler.ColorHandler;
import com.alex.mysticalagriculture.client.screen.HarvesterScreen;
import com.alex.mysticalagriculture.client.screen.ReprocessorScreen;
import com.alex.mysticalagriculture.client.screen.SoulExtractorScreen;
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
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

public class MysticalAgricultureClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(BlockEntities.INFUSION_PEDESTAL, InfusionPedestalRenderer::new);
        BlockEntityRendererFactories.register(BlockEntities.INFUSION_ALTAR, InfusionAltarRenderer::new);
        BlockEntityRendererFactories.register(BlockEntities.TINKERING_TABLE, TinkeringTableRenderer::new);
        BlockEntityRendererFactories.register(BlockEntities.AWAKENING_PEDESTAL, AwakeningPedestalRenderer::new);
        BlockEntityRendererFactories.register(BlockEntities.AWAKENING_ALTAR, AwakeningAltarRenderer::new);
        BlockEntityRendererFactories.register(BlockEntities.ESSENCE_VESSEL, EssenceVesselRenderer::new);

        ModelPredicateProviderRegistry.register(Items.SOUL_JAR, new Identifier("fill"), SoulJarItem.getFillPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.EXPERIENCE_CAPSULE, new Identifier("fill"), ExperienceCapsuleItem.getFillPropertyGetter());
        HandledScreens.register(ScreenHandlerTypes.REPROCESSOR, ReprocessorScreen::new);
        HandledScreens.register(ScreenHandlerTypes.TINKERING_TABLE, TinkeringTableScreen::new);
        HandledScreens.register(ScreenHandlerTypes.SOUL_EXTRACTOR, SoulExtractorScreen::new);
        HandledScreens.register(ScreenHandlerTypes.HARVESTER, HarvesterScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), Blocks.INFERIUM_ORE, Blocks.DEEPSLATE_INFERIUM_ORE, Blocks.PROSPERITY_ORE, Blocks.DEEPSLATE_PROSPERITY_ORE, Blocks.SOULIUM_ORE);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), Blocks.SOUL_GLASS, Blocks.WITHERPROOF_GLASS, Blocks.ESSENCE_VESSEL);

        ColorHandler.onBlockColors();
        ColorHandler.onItemColors();
        //Blocks.onColors();
        //Items.onColors();
        //ModAugments.onColors();
        //ModCrops.onColors();
    }
}
