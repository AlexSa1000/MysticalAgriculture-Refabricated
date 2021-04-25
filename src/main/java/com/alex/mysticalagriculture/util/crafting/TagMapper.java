package com.alex.mysticalagriculture.util.crafting;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.alex.mysticalagriculture.MysticalAgriculture.NAME;

public class TagMapper {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger(NAME);
    private static final Map<String, String> TAG_TO_ITEM_MAP = new HashMap<>();

    public static void reloadTagMappings() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        File dir = FabricLoader.getInstance().getConfigDir().toFile();

        if (dir.exists() && dir.isDirectory()) {
            File file = FabricLoader.getInstance().getConfigDir().resolve("mystagri-tags.json").toFile();
            if (file.exists() && file.isFile()) {
                JsonObject json;
                FileReader reader = null;
                try {
                    JsonParser parser = new JsonParser();
                    reader = new FileReader(file);
                    json = parser.parse(reader).getAsJsonObject();

                    json.entrySet().stream().filter(e -> {
                        String value = e.getValue().getAsString();
                        return !"__comment".equalsIgnoreCase(e.getKey()) && !value.isEmpty() && !"null".equalsIgnoreCase(value);
                    }).forEach(entry -> {
                        String tag = entry.getKey();
                        String item = entry.getValue().getAsString();

                        TAG_TO_ITEM_MAP.put(tag, item);
                    });

                    reader.close();
                } catch (Exception e) {
                    LOGGER.error("An error occurred while reading mystagri-tags.json", e);
                } finally {
                    IOUtils.closeQuietly(reader);
                }
            } else {
                try (Writer writer = new FileWriter(file)) {
                    JsonObject object = new JsonObject();
                    object.addProperty("Instructions", "This file is a simple list of key-value pairs. The key being the tag and the value being the item that recipes using that tag should output. " +
                                                                  "This file will automatically populate with any tags used by this system during recipe loading. If there is no entry for a tag, or the output item is \"null\", an entry will attempt to be added for one of the items in that tag. " +
                                                                  "There should be no need to add an entry manually. Once you load into a world, all applicable entries should be generated. You can then change the item ids to the ones you want to use.");
                    GSON.toJson(object, writer);
                } catch (IOException e) {
                    LOGGER.error("An error occurred while creating mystagri-tags.json", e);
                }
            }
        }

        stopwatch.stop();
        LOGGER.info("Loaded mystagri-tags.json in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public static Item getItemForTag(String tagId) {
        if (TAG_TO_ITEM_MAP.containsKey(tagId)) {
            String id = TAG_TO_ITEM_MAP.get(tagId);
            return Registry.ITEM.get(new Identifier(id));
        } else {
            File file = FabricLoader.getInstance().getConfigDir().resolve("mystagri-tags.json").toFile();



            if (file.exists() && file.isFile()) {
                JsonObject json = null;
                FileReader reader = null;
                try {
                    JsonParser parser = new JsonParser();
                    reader = new FileReader(file);
                    json = parser.parse(reader).getAsJsonObject();
                } catch (Exception e) {
                    LOGGER.error("An error occurred while reading mystagri-tags.json", e);
                } finally {
                    IOUtils.closeQuietly(reader);
                }

                if (json != null) {
                    if (json.has(tagId)) {
                        String itemId = json.get(tagId).getAsString();
                        if (itemId.isEmpty() || "null".equalsIgnoreCase(itemId))
                            return addTagToFile(tagId, json, file);

                        TAG_TO_ITEM_MAP.put(tagId, itemId);
                        return Registry.ITEM.get(new Identifier(itemId));

                    } else {
                        return addTagToFile(tagId, json, file);
                    }
                }
            }

            return Items.AIR;
        }
    }

    private static Item addTagToFile(String tagId, JsonObject json, File file) {
        Tag<Item> tag = ServerTagManagerHolder.getTagManager().getItems().getTag(new Identifier(tagId));
        Item item = tag == null ? Items.AIR : tag.values().stream().findFirst().orElse(Items.AIR);

        String itemId = "null";
        if (Registry.ITEM.getId(item) != null && item != Items.AIR) {
            itemId = Registry.ITEM.getId(item).toString();
        }

        json.addProperty(tagId, itemId);
        TAG_TO_ITEM_MAP.put(tagId, itemId);

        try (Writer writer = new FileWriter(file)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            LOGGER.error("An error occurred while writing to mystagri-tags.json", e);
        }

        return item;
    }
}
