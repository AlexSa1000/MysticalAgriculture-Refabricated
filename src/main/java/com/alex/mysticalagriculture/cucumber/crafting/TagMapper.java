package com.alex.mysticalagriculture.cucumber.crafting;

import com.alex.mysticalagriculture.MysticalAgriculture;
import com.alex.mysticalagriculture.cucumber.compat.AlmostUnifiedAdapter;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TagMapper {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Map<String, String> TAG_TO_ITEM_MAP = new HashMap<>();

    public static void reloadTagMappings() {
        var stopwatch = Stopwatch.createStarted();
        var dir = FabricLoader.getInstance().getConfigDir().toFile();

        if (dir.exists() && dir.isDirectory()) {
            var file = FabricLoader.getInstance().getConfigDir().resolve("mysticalagriculture-tags.json").toFile();

            if (file.exists() && file.isFile()) {
                JsonObject json;
                FileReader reader = null;

                try {
                    var parser = new JsonParser();
                    reader = new FileReader(file);
                    json = parser.parse(reader).getAsJsonObject();

                    json.entrySet().stream().filter(e -> {
                        String value = e.getValue().getAsString();
                        return !"__comment".equalsIgnoreCase(e.getKey()) && !value.isEmpty() && !"null".equalsIgnoreCase(value);
                    }).forEach(entry -> {
                        var tagId = entry.getKey();
                        var itemId = entry.getValue().getAsString();

                        TAG_TO_ITEM_MAP.put(tagId, itemId);

                        // if auto refresh tag entries is enabled, we check any entries that contain an item ID to see
                        // if they are still present. if not we just refresh the entry
                        if (/*ModConfigs.AUTO_REFRESH_TAG_ENTRIES.get()*/true) {
                            if (!itemId.isEmpty() && !"null".equalsIgnoreCase(itemId)) {
                                var item = Registries.ITEM.get(new Identifier(itemId));
                                if (item == null || item == Items.AIR) {
                                    addTagToFile(tagId, json, file, false);
                                }
                            }
                        }
                    });

                    // save changes to disk if refresh is enabled
                    if (/*ModConfigs.AUTO_REFRESH_TAG_ENTRIES.get()*/true)
                        saveToFile(json, file);

                    reader.close();
                } catch (Exception e) {
                    MysticalAgriculture.LOGGER.error("An error occurred while reading mysticalagriculture-tags.json", e);
                } finally {
                    IOUtils.closeQuietly(reader);
                }
            } else {
                /*try (Writer writer = new FileWriter(file)) {
                    JsonObject object = new JsonObject();
                    object.addProperty("Instructions", "This file is a simple list of key-value pairs. The key being the tag and the value being the item that recipes using that tag should output. " +
                            "This file will automatically populate with any tags used by this system during recipe loading. If there is no entry for a tag, or the output item is \"null\", an entry will attempt to be added for one of the items in that tag. " +
                            "There should be no need to add an entry manually. Once you load into a world, all applicable entries should be generated. You can then change the item ids to the ones you want to use.");
                    GSON.toJson(object, writer);
                } catch (IOException e) {
                    LOGGER.error("An error occurred while creating mystagri-tags.json", e);
                }*/
                generateNewConfig(file);
            }
        }

        stopwatch.stop();
        MysticalAgriculture.LOGGER.info("Loaded mysticalagriculture-tags.json in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public static Item getItemForTag(String tagId) {
        var preferredItem = AlmostUnifiedAdapter.getPreferredItemForTag(tagId);

        if (preferredItem != null) {
            return preferredItem;

        } else if (TAG_TO_ITEM_MAP.containsKey(tagId)) {
            var id = TAG_TO_ITEM_MAP.get(tagId);
            return Registries.ITEM.get(new Identifier(id));
        } else {
            //File file = FMLPaths.CONFIGDIR.get().resolve("cucumber-tags.json").toFile();
            var file = FabricLoader.getInstance().getConfigDir().resolve("mysticalagriculture-tags.json").toFile();
            if (!file.exists()) {
                generateNewConfig(file);
            }

            if (file.isFile()) {
                JsonObject json = null;
                FileReader reader = null;

                try {
                    JsonParser parser = new JsonParser();
                    reader = new FileReader(file);
                    json = parser.parse(reader).getAsJsonObject();
                } catch (Exception e) {
                    MysticalAgriculture.LOGGER.error("An error occurred while reading mysticalagriculture-tags.json", e);
                } finally {
                    IOUtils.closeQuietly(reader);
                }

                if (json != null) {
                    if (json.has(tagId)) {
                        String itemId = json.get(tagId).getAsString();
                        if (!itemId.isEmpty() && !"null".equalsIgnoreCase(itemId)) {
                            TAG_TO_ITEM_MAP.put(tagId, itemId);
                            return Registries.ITEM.get(new Identifier(itemId));
                        }

                        return addTagToFile(tagId, json, file);
                    }

                    return addTagToFile(tagId, json, file);
                }
            }

            return Items.AIR;
        }
    }

    public static ItemStack getItemStackForTag(String tagId, int size) {
        Item item = getItemForTag(tagId);
        return item != null && item != Items.AIR ? new ItemStack(item, size) : ItemStack.EMPTY;
    }

    private static Item addTagToFile(String tagId, JsonObject json, File file) {
        return addTagToFile(tagId, json, file, true);
    }

    private static Item addTagToFile(String tagId, JsonObject json, File file, boolean save) {
        var mods = Lists.newArrayList("techreborn", "appliedenergistics2");
        var key = ItemTags.of(tagId);
        var tags = Registries.ITEM.streamEntries().filter(entry -> entry.isIn(key));

        //assert tags != null;

        var item = tags.min((item1, item2) -> {
            var id1 = item1.getKey().orElse(null);
            var index1 = id1 != null ? mods.indexOf(id1.getValue().getNamespace()) : -1;

            var id2 = item2.getKey().orElse(null);
            var index2 = id2 != null ? mods.indexOf(id2.getValue().getNamespace()) : -1;

            return index1 > index2 ? 1 : index1 == -1 ? 0 : -1;
        }).orElse(Items.AIR.getRegistryEntry());

        var itemId = item.value().equals(Items.AIR) ? "null" : item.getKey().orElse(RegistryKey.of(RegistryKeys.ITEM, new Identifier("air"))).getValue().toString();

        json.addProperty(tagId, itemId);
        TAG_TO_ITEM_MAP.put(tagId, itemId);

        if (save) {
            saveToFile(json, file);
        }

        return item.value();
    }

    private static void saveToFile(JsonObject json, File file) {
        try (var writer = new FileWriter(file)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            MysticalAgriculture.LOGGER.error("An error occurred while writing to mysticalagriculture-tags.json", e);
        }
    }

    private static void generateNewConfig(File file) {
        try (var writer = new FileWriter(file)) {
            var object = new JsonObject();
            object.addProperty("__comment", "Instructions: https://blakesmods.com/docs/cucumber/tags-config");

            GSON.toJson(object, writer);
        } catch (IOException e) {
            MysticalAgriculture.LOGGER.error("An error occurred while creating mysticalagriculture-tags.json", e);
        }
    }
}
