package com.eirmax.elytraswaperplus.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ElytraSwapperConfig {
    private static final File CONFIG_FILE = new File("config/elytraswaperplus.properties");
    public static double HEIGHT_FALLING = 3.0;


    public static void loadConfig() {
        Properties properties = new Properties();
        if (CONFIG_FILE.exists()) {
            try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
                properties.load(input);
                HEIGHT_FALLING = Double.parseDouble(properties.getProperty("height_falling", "3.0"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveConfig() {
        Properties properties = new Properties();
        properties.setProperty("height_falling", String.valueOf(HEIGHT_FALLING));

        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "ElytraSwapperPlus Config");
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

        general.addEntry(entryBuilder.startDoubleField(Component.translatable("option.elytraswaperplus.height_falling"), HEIGHT_FALLING)
                .setDefaultValue(3.0)
                .setTooltip(Component.translatable("tooltip.elytraswaperplus.height_falling"))
                .setSaveConsumer(newValue -> HEIGHT_FALLING = newValue)
                .build());

        return builder.build();
    }
}