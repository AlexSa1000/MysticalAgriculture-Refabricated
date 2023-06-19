package com.alex.mysticalagriculture.client;

import com.alex.cucumber.forge.client.event.ModelEvent;
import com.alex.cucumber.forge.client.event.TextureStitchEvent;
import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.api.crop.CropTextures;
import com.alex.mysticalagriculture.config.ModConfigs;
import com.alex.mysticalagriculture.registry.CropRegistry;
import com.google.common.base.Stopwatch;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ModelHandler {
    private static final ResourceLocation MISSING_NO = new ResourceLocation("minecraft", "missingno");
    public static final ResourceLocation BLOCK_ATLAS = new ResourceLocation("minecraft", "textures/atlas/blocks.png");

    public static void onRegisterAdditionalModels() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            if (!ModConfigs.ANIMATED_GROWTH_ACCELERATORS.get()) {
                for (var type : new String[] { "block", "item" }) {
                    for (var tier : new String[] { "inferium", "prudentium", "tertium", "imperium", "supremium" }) {
                        out.accept(new ResourceLocation(MysticalAgriculture.MOD_ID, String.format("%s/%s_growth_accelerator_static", type, tier)));
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                out.accept(new ResourceLocation(MysticalAgriculture.MOD_ID, "block/mystical_resource_crop_" + i));
                out.accept(new ResourceLocation(MysticalAgriculture.MOD_ID, "block/mystical_mob_crop_" + i));
            }

            for (var type : CropRegistry.getInstance().getTypes()) {
                out.accept(new ResourceLocation(CropTextures.FLOWER_INGOT_BLANK + "_" + type.getName()));
                out.accept(new ResourceLocation(CropTextures.FLOWER_ROCK_BLANK + "_" + type.getName()));
                out.accept(new ResourceLocation(CropTextures.FLOWER_DUST_BLANK + "_" + type.getName()));
                out.accept(new ResourceLocation(CropTextures.FLOWER_FACE_BLANK + "_" + type.getName()));
            }

            out.accept(CropTextures.ESSENCE_INGOT_BLANK);
            out.accept(CropTextures.ESSENCE_ROCK_BLANK);
            out.accept(CropTextures.ESSENCE_DUST_BLANK);
            out.accept(CropTextures.ESSENCE_GEM_BLANK);
            out.accept(CropTextures.ESSENCE_TALL_GEM_BLANK);
            out.accept(CropTextures.ESSENCE_DIAMOND_BLANK);
            out.accept(CropTextures.ESSENCE_QUARTZ_BLANK);
            out.accept(CropTextures.ESSENCE_FLAME_BLANK);
            out.accept(CropTextures.ESSENCE_ROD_BLANK);

            out.accept(CropTextures.SEED_BLANK);
        });
    }

    public static void onModelBakingCompleted(ModelEvent.BakingCompleted event) {
        var stopwatch = Stopwatch.createStarted();

        var registry = event.getModels();
        var bakery = event.getModelBakery();

        if (!ModConfigs.ANIMATED_GROWTH_ACCELERATORS.get()) {
            for (var tier : new String[] { "inferium", "prudentium", "tertium", "imperium", "supremium" }) {
                var loc = String.format("%s_growth_accelerator", tier);
                var blockModel = registry.get(new ResourceLocation(MysticalAgriculture.MOD_ID, "block/" + loc + "_static"));
                var itemModel = registry.get(new ResourceLocation(MysticalAgriculture.MOD_ID, "item/" + loc + "_static"));

                registry.replace(new ModelResourceLocation(MysticalAgriculture.MOD_ID + ":" + loc), blockModel);
                registry.replace(new ModelResourceLocation(MysticalAgriculture.MOD_ID + ":" + loc, "inventory"), itemModel);
            }
        }

        /*var cropModels = new HashMap<ResourceLocation, BakedModel[]>();
        var cropModelsGrown = new HashMap<ResourceLocation, RetextureableBlockModelWrapper>();

        for (var cropType : CropRegistry.getInstance().getTypes()) {
            cropModels.put(cropType.getId(), IntStream.range(0, 7)
                    .mapToObj(i -> registry.get(new ResourceLocation(cropType.getStemModel() + "_" + i)))
                    .toArray(BakedModel[]::new));

            var model = bakery.getModel(new ResourceLocation(cropType.getStemModel() + "_7"));
            var modelWrapper = new RetextureableBlockModelWrapper((BlockModel) model);

            cropModelsGrown.put(cropType.getId(), modelWrapper);
        }

        Function<Material, TextureAtlasSprite> getSprite = bakery.atlasSet::getSprite;
        var generator = new ItemModelGenerator();

        var essenceModel = bakery.getModel(new ResourceLocation(MysticalAgriculture.MOD_ID, "item/mystical_essence"));
        var essenceModelWrapper = new RetextureableItemModelWrapper((BlockModel) essenceModel);
        var seedsModel = bakery.getModel(new ResourceLocation(MysticalAgriculture.MOD_ID, "item/mystical_seeds"));
        var seedsModelWrapper = new RetextureableItemModelWrapper((BlockModel) seedsModel);

        for (var crop : CropRegistry.getInstance().getCrops()) {
            var textures = crop.getTextures();
            var crops = crop.getCropBlock();
            var cropId = Registry.BLOCK.getKey(crops);

            if (cropId != null) {
                for (int i = 0; i < 7; i++) {
                    var location = new ModelResourceLocation(cropId, "age=" + i);
                    var bakedModel = registry.get(location);

                    if (bakedModel == null || bakedModel.getParticleIcon().getName().equals(MISSING_NO)) {
                        var type = crop.getType().getId();
                        registry.replace(location, cropModels.get(type)[i]);
                    }
                }

                var location = new ModelResourceLocation(cropId, "age=7");
                var bakedModel = registry.get(location);

                if (bakedModel == null || bakedModel.getParticleIcon().getName().equals(MISSING_NO)) {
                    var texture = crop.getTextures().getFlowerTexture();
                    var cropRetexturedModel = cropModelsGrown.get(crop.getType().getId()).retexture(ImmutableMap.of("flower", texture.toString()));
                    var cropBakedModel = cropRetexturedModel.bake(bakery, getSprite, BlockModelRotation.X0_Y0, location);

                    registry.replace(location, cropBakedModel);
                }
            }

            var essence = crop.getEssenceItem();
            var essenceId = Registry.ITEM.getKey(essence);

            if (essenceId != null) {
                var location = new ModelResourceLocation(essenceId, "inventory");
                var bakedModel = registry.get(location);

                if (bakedModel == null || bakedModel.getParticleIcon().getName().equals(MISSING_NO)) {
                    var texture = textures.getEssenceTexture();
                    var retexture = essenceModelWrapper.retexture(ImmutableMap.of("layer0", texture.toString()));
                    var generated = generator.generateBlockModel(getSprite, retexture);
                    var model = generated.bake(bakery, generated, getSprite, BlockModelRotation.X0_Y0, location, false);

                    registry.replace(location, model);
                }
            }

            var seeds = crop.getSeedsItem();
            var seedsId = Registry.ITEM.getKey(seeds);

            if (seedsId != null) {
                var location = new ModelResourceLocation(seedsId, "inventory");
                var bakedModel = registry.get(location);

                if (bakedModel == null || bakedModel.getParticleIcon().getName().equals(MISSING_NO)) {
                    var texture = textures.getSeedTexture();
                    var retexture = seedsModelWrapper.retexture(ImmutableMap.of("layer0", texture.toString()));
                    var generated = generator.generateBlockModel(getSprite, retexture);
                    var model = generated.bake(bakery, generated, getSprite, BlockModelRotation.X0_Y0, location, false);

                    registry.replace(location, model);
                }
            }
        }*/

        stopwatch.stop();

        MysticalAgriculture.LOGGER.info("Model replacement took {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public static void onTextureStitch(TextureAtlas map, Set<ResourceLocation> resourceLocations)
    {
        var event = new TextureStitchEvent.Pre(map, resourceLocations);

        if (event.getAtlas().location().equals(ModelHandler.BLOCK_ATLAS)) {
            for (var crop : CropRegistry.getInstance().getCrops()) {
                var textures = crop.getTextures();

                event.addSprite(textures.getFlowerTexture());
                event.addSprite(textures.getEssenceTexture());
                event.addSprite(textures.getSeedTexture());
            }

            event.addSprite(new ResourceLocation(MysticalAgriculture.MOD_ID, "block/essence_vessel_contents"));
        }

        Sheets.SIGN_MATERIALS.values().stream()
                .filter(rm -> rm.atlasLocation().equals(map.location()))
                .forEach(rm -> resourceLocations.add(rm.texture()));
    }
}
