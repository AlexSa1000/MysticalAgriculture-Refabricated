package com.alex.mysticalagriculture.cucumber.helper;

import com.alex.mysticalagriculture.forge.common.ConfigSpec;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public final class ConfigHelper {
    public ConfigHelper() {
    }

    public static void load(ConfigSpec config, String location) {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(location);
        CommentedFileConfig data = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        data.load();
        config.setConfig(data);
    }
}
