package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.container.HarvesterContainer;
import com.alex.mysticalagriculture.container.ReprocessorContainer;
import com.alex.mysticalagriculture.container.SoulExtractorContainer;
import com.alex.mysticalagriculture.container.TinkeringTableContainer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;

public class ScreenHandlerTypes {

    public static final ExtendedScreenHandlerType<ReprocessorContainer> REPROCESSOR = new ExtendedScreenHandlerType<>(ReprocessorContainer::create);
    public static final ExtendedScreenHandlerType<TinkeringTableContainer> TINKERING_TABLE = new ExtendedScreenHandlerType<>(TinkeringTableContainer::create);
    public static final ExtendedScreenHandlerType<SoulExtractorContainer> SOUL_EXTRACTOR = new ExtendedScreenHandlerType<>(SoulExtractorContainer::create);
    public static final ExtendedScreenHandlerType<HarvesterContainer> HARVESTER = new ExtendedScreenHandlerType<>(HarvesterContainer::create);

    public static void registerScreenHandlerTypes() {
        Registry.register(Registry.MENU, "reprocessor", REPROCESSOR);
        Registry.register(Registry.MENU, "tinkering_table", TINKERING_TABLE);
        Registry.register(Registry.MENU, "soul_extractor", SOUL_EXTRACTOR);
        Registry.register(Registry.MENU, "harvester", HARVESTER);
    }

}

