package com.alex.mysticalagriculture;

import com.alex.mysticalagriculture.client.blockentity.*;
import com.alex.mysticalagriculture.client.handler.ColorHandler;
import com.alex.mysticalagriculture.client.screen.HarvesterScreen;
import com.alex.mysticalagriculture.client.screen.ReprocessorScreen;
import com.alex.mysticalagriculture.client.screen.SoulExtractorScreen;
import com.alex.mysticalagriculture.client.screen.TinkeringTableScreen;
import com.alex.mysticalagriculture.init.ModBlockEntities;
import com.alex.mysticalagriculture.init.ModBlocks;
import com.alex.mysticalagriculture.init.ModItems;
import com.alex.mysticalagriculture.init.ModContainerTypes;
import com.alex.mysticalagriculture.items.ExperienceCapsuleItem;
import com.alex.mysticalagriculture.items.SoulJarItem;
import com.alex.mysticalagriculture.items.tool.EssenceBowItem;
import com.alex.mysticalagriculture.items.tool.EssenceCrossbowItem;
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
        BlockEntityRendererFactories.register(ModBlockEntities.INFUSION_PEDESTAL, InfusionPedestalRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.INFUSION_ALTAR, InfusionAltarRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.TINKERING_TABLE, TinkeringTableRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.AWAKENING_PEDESTAL, AwakeningPedestalRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.AWAKENING_ALTAR, AwakeningAltarRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.ESSENCE_VESSEL, EssenceVesselRenderer::new);

        ModelPredicateProviderRegistry.register(ModItems.SOUL_JAR, new Identifier("fill"), SoulJarItem.getFillPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.EXPERIENCE_CAPSULE, new Identifier("fill"), ExperienceCapsuleItem.getFillPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.INFERIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.INFERIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.INFERIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.INFERIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.INFERIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.INFERIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        //ModelPredicateProviderRegistry.register(Items.INFERIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.PRUDENTIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.PRUDENTIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.PRUDENTIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.PRUDENTIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.PRUDENTIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.PRUDENTIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        //ModelPredicateProviderRegistry.register(Items.PRUDENTIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.TERTIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.TERTIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.TERTIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.TERTIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.TERTIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.TERTIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        //ModelPredicateProviderRegistry.register(Items.TERTIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.IMPERIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.IMPERIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.IMPERIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.IMPERIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.IMPERIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.IMPERIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        //ModelPredicateProviderRegistry.register(Items.IMPERIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.SUPREMIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.SUPREMIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.SUPREMIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.SUPREMIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.SUPREMIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.SUPREMIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        //ModelPredicateProviderRegistry.register(Items.SUPREMIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.AWAKENED_SUPREMIUM_BOW, new Identifier("pull"), EssenceBowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.AWAKENED_SUPREMIUM_BOW, new Identifier("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.AWAKENED_SUPREMIUM_CROSSBOW, new Identifier("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.AWAKENED_SUPREMIUM_CROSSBOW, new Identifier("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.AWAKENED_SUPREMIUM_CROSSBOW, new Identifier("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ModelPredicateProviderRegistry.register(ModItems.AWAKENED_SUPREMIUM_CROSSBOW, new Identifier("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        //ModelPredicateProviderRegistry.register(Items.AWAKENED_SUPREMIUM_FISHING_ROD, new Identifier("cast"), EssenceFishingRodItem.getCastPropertyGetter());

        HandledScreens.register(ModContainerTypes.REPROCESSOR, ReprocessorScreen::new);
        HandledScreens.register(ModContainerTypes.TINKERING_TABLE, TinkeringTableScreen::new);
        HandledScreens.register(ModContainerTypes.SOUL_EXTRACTOR, SoulExtractorScreen::new);
        HandledScreens.register(ModContainerTypes.HARVESTER, HarvesterScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), ModBlocks.INFERIUM_ORE, ModBlocks.DEEPSLATE_INFERIUM_ORE, ModBlocks.PROSPERITY_ORE, ModBlocks.DEEPSLATE_PROSPERITY_ORE, ModBlocks.SOULIUM_ORE);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.SOUL_GLASS, ModBlocks.WITHERPROOF_GLASS, ModBlocks.ESSENCE_VESSEL);

        ColorHandler.onBlockColors();
        ColorHandler.onItemColors();
    }
}
