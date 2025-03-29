package com.eirmax.elytraswaperplus.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ElytraSwapperConfig {
    private static final Path CONFIG_FILE = Paths.get("config/elytraswaperplus.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static class ConfigData {
        public double heightFalling = 3.0;
        public boolean enableMixin = true;
    }

    public static ConfigData CONFIG = new ConfigData();

    public static void loadConfig() {
        if (Files.exists(CONFIG_FILE)) {
            try (FileReader reader = new FileReader(CONFIG_FILE.toFile())) {
                CONFIG = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            saveConfig();
        }
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE.toFile())) {
            GSON.toJson(CONFIG, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("title.elytraswaperplus.config"))
                .setSavingRunnable(ElytraSwapperConfig::saveConfig);

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.elytraswaperplus.general"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        general.addEntry(entryBuilder.startDoubleField(Component.translatable("option.elytraswaperplus.height_falling"), CONFIG.heightFalling)
                .setDefaultValue(3.0)
                .setTooltip(Component.translatable("tooltip.elytraswaperplus.height_falling"))
                .setSaveConsumer(newValue -> CONFIG.heightFalling = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.elytraswaperplus.enable_mixin"), CONFIG.enableMixin)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("tooltip.elytraswaperplus.enable_mixin"))
                .setSaveConsumer(newValue -> CONFIG.enableMixin = newValue)
                .build());

        return builder.build();
    }
}