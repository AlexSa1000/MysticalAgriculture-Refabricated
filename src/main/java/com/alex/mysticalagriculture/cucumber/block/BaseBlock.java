package com.alex.mysticalagriculture.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Function;

public class BaseBlock extends Block {
    public BaseBlock(Material material, Function<Settings, Settings> settings) {
        super(settings.apply(Settings.of(material)));
    }

    public BaseBlock(Material material, BlockSoundGroup sound, float hardness, float resistance) {
        super(Settings.of(material).sounds(sound).strength(hardness, resistance));
    }

    public BaseBlock(Material material, BlockSoundGroup sound, float hardness, float resistance, boolean tool) {
        super(Settings.of(material).sounds(sound).strength(hardness, resistance).requiresTool());
    }
}
