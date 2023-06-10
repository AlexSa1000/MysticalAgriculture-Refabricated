package com.alex.mysticalagriculture;

import com.alex.mysticalagriculture.client.ModelHandler;
import com.alex.mysticalagriculture.client.blockentity.*;
import com.alex.mysticalagriculture.client.handler.ColorHandler;
import com.alex.mysticalagriculture.client.handler.GuiOverlayHandler;
import com.alex.mysticalagriculture.client.screen.HarvesterScreen;
import com.alex.mysticalagriculture.client.screen.ReprocessorScreen;
import com.alex.mysticalagriculture.client.screen.SoulExtractorScreen;
import com.alex.mysticalagriculture.client.screen.TinkeringTableScreen;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.init.BlockEntities;
import com.alex.mysticalagriculture.init.Blocks;
import com.alex.mysticalagriculture.init.Items;
import com.alex.mysticalagriculture.init.ScreenHandlerTypes;
import com.alex.mysticalagriculture.items.ExperienceCapsuleItem;
import com.alex.mysticalagriculture.items.SoulJarItem;
import com.alex.mysticalagriculture.items.tool.EssenceBowItem;
import com.alex.mysticalagriculture.items.tool.EssenceCrossbowItem;
import com.alex.mysticalagriculture.items.tool.EssenceFishingRodItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class MysticalAgricultureClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(GuiOverlayHandler::setAltarOverlay);
        HudRenderCallback.EVENT.register(GuiOverlayHandler::setEssenceVesselOverlay);

        ModelHandler.onRegisterAdditionalModels();

        /*ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> (modelId, context) -> {
            if (resourceManager.getResource()) {

            }
            if (!ModConfigs.ANIMATED_GROWTH_ACCELERATORS.get()) {
                for (var tier : new String[] { "inferium", "prudentium", "tertium", "imperium", "supremium" }) {
                    var loc = String.format("%s_growth_accelerator", tier);
                    var blockModel = resourceManager.getResource((new ResourceLocation(MysticalAgriculture.MOD_ID, "block/" + loc + "_static"));
                    var itemModel = resourceManager.getResource((new ResourceLocation(MysticalAgriculture.MOD_ID, "item/" + loc + "_static"));

                    if (modelId.equals(blockModel))
                        return new ModelResourceLocation(MysticalAgriculture.MOD_ID + ":" + loc), blockModel
                    registry.replace(new ModelResourceLocation(MysticalAgriculture.MOD_ID + ":" + loc), blockModel);
                    registry.replace(new ModelResourceLocation(MysticalAgriculture.MOD_ID + ":" + loc, "inventory"), itemModel);
                }
            }
        });

        ClientSpriteRegistryCallback*/

        BlockEntityRenderers.register(BlockEntities.INFUSION_PEDESTAL, InfusionPedestalRenderer::new);
        BlockEntityRenderers.register(BlockEntities.INFUSION_ALTAR, InfusionAltarRenderer::new);
        BlockEntityRenderers.register(BlockEntities.TINKERING_TABLE, TinkeringTableRenderer::new);
        BlockEntityRenderers.register(BlockEntities.AWAKENING_PEDESTAL, AwakeningPedestalRenderer::new);
        BlockEntityRenderers.register(BlockEntities.AWAKENING_ALTAR, AwakeningAltarRenderer::new);
        BlockEntityRenderers.register(BlockEntities.ESSENCE_VESSEL, EssenceVesselRenderer::new);

        ItemProperties.register(Items.SOUL_JAR, new ResourceLocation("fill"), SoulJarItem.getFillPropertyGetter());
        ItemProperties.register(Items.EXPERIENCE_CAPSULE, new ResourceLocation("fill"), ExperienceCapsuleItem.getFillPropertyGetter());
        ItemProperties.register(Items.INFERIUM_BOW, new ResourceLocation("pull"), EssenceBowItem.getPullPropertyGetter());
        ItemProperties.register(Items.INFERIUM_BOW, new ResourceLocation("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.INFERIUM_CROSSBOW, new ResourceLocation("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ItemProperties.register(Items.INFERIUM_CROSSBOW, new ResourceLocation("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.INFERIUM_CROSSBOW, new ResourceLocation("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ItemProperties.register(Items.INFERIUM_CROSSBOW, new ResourceLocation("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ItemProperties.register(Items.INFERIUM_FISHING_ROD, new ResourceLocation("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ItemProperties.register(Items.PRUDENTIUM_BOW, new ResourceLocation("pull"), EssenceBowItem.getPullPropertyGetter());
        ItemProperties.register(Items.PRUDENTIUM_BOW, new ResourceLocation("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.PRUDENTIUM_CROSSBOW, new ResourceLocation("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ItemProperties.register(Items.PRUDENTIUM_CROSSBOW, new ResourceLocation("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.PRUDENTIUM_CROSSBOW, new ResourceLocation("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ItemProperties.register(Items.PRUDENTIUM_CROSSBOW, new ResourceLocation("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ItemProperties.register(Items.PRUDENTIUM_FISHING_ROD, new ResourceLocation("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ItemProperties.register(Items.TERTIUM_BOW, new ResourceLocation("pull"), EssenceBowItem.getPullPropertyGetter());
        ItemProperties.register(Items.TERTIUM_BOW, new ResourceLocation("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.TERTIUM_CROSSBOW, new ResourceLocation("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ItemProperties.register(Items.TERTIUM_CROSSBOW, new ResourceLocation("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.TERTIUM_CROSSBOW, new ResourceLocation("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ItemProperties.register(Items.TERTIUM_CROSSBOW, new ResourceLocation("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ItemProperties.register(Items.TERTIUM_FISHING_ROD, new ResourceLocation("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ItemProperties.register(Items.IMPERIUM_BOW, new ResourceLocation("pull"), EssenceBowItem.getPullPropertyGetter());
        ItemProperties.register(Items.IMPERIUM_BOW, new ResourceLocation("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.IMPERIUM_CROSSBOW, new ResourceLocation("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ItemProperties.register(Items.IMPERIUM_CROSSBOW, new ResourceLocation("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.IMPERIUM_CROSSBOW, new ResourceLocation("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ItemProperties.register(Items.IMPERIUM_CROSSBOW, new ResourceLocation("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ItemProperties.register(Items.IMPERIUM_FISHING_ROD, new ResourceLocation("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ItemProperties.register(Items.SUPREMIUM_BOW, new ResourceLocation("pull"), EssenceBowItem.getPullPropertyGetter());
        ItemProperties.register(Items.SUPREMIUM_BOW, new ResourceLocation("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.SUPREMIUM_CROSSBOW, new ResourceLocation("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ItemProperties.register(Items.SUPREMIUM_CROSSBOW, new ResourceLocation("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.SUPREMIUM_CROSSBOW, new ResourceLocation("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ItemProperties.register(Items.SUPREMIUM_CROSSBOW, new ResourceLocation("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ItemProperties.register(Items.SUPREMIUM_FISHING_ROD, new ResourceLocation("cast"), EssenceFishingRodItem.getCastPropertyGetter());
        ItemProperties.register(Items.AWAKENED_SUPREMIUM_BOW, new ResourceLocation("pull"), EssenceBowItem.getPullPropertyGetter());
        ItemProperties.register(Items.AWAKENED_SUPREMIUM_BOW, new ResourceLocation("pulling"), EssenceBowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.AWAKENED_SUPREMIUM_CROSSBOW, new ResourceLocation("pull"), EssenceCrossbowItem.getPullPropertyGetter());
        ItemProperties.register(Items.AWAKENED_SUPREMIUM_CROSSBOW, new ResourceLocation("pulling"), EssenceCrossbowItem.getPullingPropertyGetter());
        ItemProperties.register(Items.AWAKENED_SUPREMIUM_CROSSBOW, new ResourceLocation("charged"), EssenceCrossbowItem.getChargedPropertyGetter());
        ItemProperties.register(Items.AWAKENED_SUPREMIUM_CROSSBOW, new ResourceLocation("firework"), EssenceCrossbowItem.getFireworkPropertyGetter());
        ItemProperties.register(Items.AWAKENED_SUPREMIUM_FISHING_ROD, new ResourceLocation("cast"), EssenceFishingRodItem.getCastPropertyGetter());

        MenuScreens.register(ScreenHandlerTypes.REPROCESSOR, ReprocessorScreen::new);
        MenuScreens.register(ScreenHandlerTypes.TINKERING_TABLE, TinkeringTableScreen::new);
        MenuScreens.register(ScreenHandlerTypes.SOUL_EXTRACTOR, SoulExtractorScreen::new);
        MenuScreens.register(ScreenHandlerTypes.HARVESTER, HarvesterScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutoutMipped(), Blocks.INFERIUM_ORE, Blocks.DEEPSLATE_INFERIUM_ORE, Blocks.PROSPERITY_ORE, Blocks.DEEPSLATE_PROSPERITY_ORE, Blocks.SOULIUM_ORE);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(), Blocks.SOUL_GLASS, Blocks.WITHERPROOF_GLASS, Blocks.ESSENCE_VESSEL);

        ColorHandler.onBlockColors();
        ColorHandler.onItemColors();
    }
}
