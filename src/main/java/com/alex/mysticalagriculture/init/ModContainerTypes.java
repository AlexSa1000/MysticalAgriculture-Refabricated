package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.container.HarvesterContainer;
import com.alex.mysticalagriculture.container.ReprocessorContainer;
import com.alex.mysticalagriculture.container.SoulExtractorContainer;
import com.alex.mysticalagriculture.container.TinkeringTableContainer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModContainerTypes {

    public static final ExtendedScreenHandlerType<ReprocessorContainer> REPROCESSOR = new ExtendedScreenHandlerType<>(ReprocessorContainer::create);
    public static final ExtendedScreenHandlerType<TinkeringTableContainer> TINKERING_TABLE = new ExtendedScreenHandlerType<>(TinkeringTableContainer::create);
    public static final ExtendedScreenHandlerType<SoulExtractorContainer> SOUL_EXTRACTOR = new ExtendedScreenHandlerType<>(SoulExtractorContainer::create);
    public static final ExtendedScreenHandlerType<HarvesterContainer> HARVESTER = new ExtendedScreenHandlerType<>(HarvesterContainer::create);

    public static void registerModContainerTypes() {
        Registry.register(BuiltInRegistries.MENU, "reprocessor", REPROCESSOR);
        Registry.register(BuiltInRegistries.MENU, "tinkering_table", TINKERING_TABLE);
        Registry.register(BuiltInRegistries.MENU, "soul_extractor", SOUL_EXTRACTOR);
        Registry.register(BuiltInRegistries.MENU, "harvester", HARVESTER);
    }

}

