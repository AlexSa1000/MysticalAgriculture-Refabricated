package com.alex.mysticalagriculture.init;

import com.alex.mysticalagriculture.container.ReprocessorContainer;
import com.alex.mysticalagriculture.container.TinkeringTableContainer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static com.alex.mysticalagriculture.MysticalAgriculture.MOD_ID;

public class ScreenHandlerTypes {

    public static final ScreenHandlerType<ReprocessorContainer> REPROCESSOR = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "reprocessor"), ReprocessorContainer::create);
    public static final ScreenHandlerType<TinkeringTableContainer> TINKERING_TABLE = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "tinkering_table"), TinkeringTableContainer::create);
}
