package com.alex.mysticalagriculture.client;

import com.alex.cucumber.helper.ParsingHelper;
import com.alex.mysticalagriculture.MysticalAgriculture;
import com.google.common.base.Stopwatch;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class EssenceVesselColorManager implements IdentifiableResourceReloadListener {
    public static final EssenceVesselColorManager INSTANCE = new EssenceVesselColorManager();

    private final HashMap<String, Integer> colors = new HashMap<>();

    @Override
    public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        return CompletableFuture.runAsync(() -> {
            if (FabricLoader.getInstance().isModLoaded(MysticalAgriculture.MOD_ID)) {
                this.load(manager);
            }
        }, backgroundExecutor).thenCompose(barrier::wait);
    }

    public int getColor(ItemStack stack) {
        var id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (id == null) {
            return 0xFFFFFF;
        }

        return this.colors.getOrDefault(id.toString(), 0xFFFFFF);
    }

    private void load(ResourceManager manager) {
        var stopwatch = Stopwatch.createStarted();
        var resources = manager.listResources("essence_vessel_colors.json", s -> s.getPath().endsWith(".json"));

        this.colors.clear();

        for (var resource : resources.entrySet()) {
            try (var reader = resource.getValue().openAsReader()) {
                var json = JsonParser.parseReader(reader).getAsJsonObject();

                for (var entry : json.entrySet()) {
                    var item = entry.getKey();
                    var color = ParsingHelper.parseHex(entry.getValue().getAsString(), item);

                    this.colors.put(item, color);
                }
            } catch (IOException e) {
                MysticalAgriculture.LOGGER.error("Failed to load {}", resource.getKey(), e);
            }
        }

        MysticalAgriculture.LOGGER.info("Loaded {} essence vessel colors in {} ms", this.colors.size(), stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(MysticalAgriculture.MOD_ID, "essence_vessel_color_manager");
    }
}
