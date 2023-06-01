package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.screenhandler.HarvesterScreenHandler;
import com.alex.mysticalagriculture.screenhandler.ReprocessorScreenHandler;
import com.alex.mysticalagriculture.screenhandler.SoulExtractorScreenHandler;
import com.alex.mysticalagriculture.screenhandler.TinkeringTableScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ScreenHandlerTypes {

    public static final ExtendedScreenHandlerType<ReprocessorScreenHandler> REPROCESSOR = new ExtendedScreenHandlerType<>(ReprocessorScreenHandler::create);
    public static final ExtendedScreenHandlerType<TinkeringTableScreenHandler> TINKERING_TABLE = new ExtendedScreenHandlerType<>(TinkeringTableScreenHandler::create);
    public static final ExtendedScreenHandlerType<SoulExtractorScreenHandler> SOUL_EXTRACTOR = new ExtendedScreenHandlerType<>(SoulExtractorScreenHandler::create);
    public static final ExtendedScreenHandlerType<HarvesterScreenHandler> HARVESTER = new ExtendedScreenHandlerType<>(HarvesterScreenHandler::create);

    public static void registerScreenHandlerTypes() {
        Registry.register(Registry.SCREEN_HANDLER, "reprocessor", REPROCESSOR);
        Registry.register(Registry.SCREEN_HANDLER, "tinkering_table", TINKERING_TABLE);
        Registry.register(Registry.SCREEN_HANDLER, "soul_extractor", SOUL_EXTRACTOR);
        Registry.register(Registry.SCREEN_HANDLER, "harvester", HARVESTER);
    }

}

