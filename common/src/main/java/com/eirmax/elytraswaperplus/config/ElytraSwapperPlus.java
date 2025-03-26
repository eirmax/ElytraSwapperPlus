package com.eirmax.elytraswaperplus.config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ElytraSwapperPlus implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof OptionsScreen) {
                Screens.getButtons(screen).add(Button.builder(Component.translatable("button.elytraswaperplus.config"), button -> {
                    client.setScreen(ElytraSwapperConfig.createConfigScreen(screen));
                }).build());
            }
        });
    }
}