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
import com.alex.mysticalagriculture.items.tool.EssenceBowItem;
import com.alex.mysticalagriculture.items.tool.EssenceCrossbowItem;
import com.alex.mysticalagriculture.items.tool.EssenceFishingRodItem;
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
        ModelPredicateProviderRegistry.register(Items.INFERIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.INFERIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.INFERIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.INFERIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.INFERIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.INFERIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.INFERIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.PRUDENTIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.PRUDENTIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.PRUDENTIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.PRUDENTIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.PRUDENTIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.PRUDENTIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.PRUDENTIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.TERTIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.TERTIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.TERTIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.TERTIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.TERTIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.TERTIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.TERTIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.IMPERIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.IMPERIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.IMPERIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.IMPERIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.IMPERIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.IMPERIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.IMPERIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.SUPREMIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.SUPREMIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.SUPREMIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.SUPREMIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.SUPREMIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.SUPREMIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.SUPREMIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.AWAKENED_SUPREMIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.AWAKENED_SUPREMIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.AWAKENED_SUPREMIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.AWAKENED_SUPREMIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.AWAKENED_SUPREMIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.AWAKENED_SUPREMIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ModelPredicateProviderRegistry.register(Items.AWAKENED_SUPREMIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());

        HandledScreens.register(ScreenHandlerTypes.REPROCESSOR, ReprocessorScreen::new);
        HandledScreens.register(ScreenHandlerTypes.TINKERING_TABLE, TinkeringTableScreen::new);
        HandledScreens.register(ScreenHandlerTypes.SOUL_EXTRACTOR, SoulExtractorScreen::new);
        HandledScreens.register(ScreenHandlerTypes.HARVESTER, HarvesterScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), Blocks.INFERIUM_ORE, Blocks.DEEPSLATE_INFERIUM_ORE, Blocks.PROSPERITY_ORE, Blocks.DEEPSLATE_PROSPERITY_ORE, Blocks.SOULIUM_ORE);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), Blocks.SOUL_GLASS, Blocks.WITHERPROOF_GLASS, Blocks.ESSENCE_VESSEL);

        ColorHandler.onBlockColors();
        ColorHandler.onItemColors();
    }
}
