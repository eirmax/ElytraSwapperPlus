package com.eirmax.fabric.fabricplatformhelper;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.config.ElytraSwapperConfig;
import com.eirmax.elytraswaperplus.gui.PlatformHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;

public class FabricPlatformHelper implements PlatformHelper {
    @Override
    public Screen createConfigScreen(Screen screen) {
        return ElytraSwapperConfig.createConfigScreen(screen);
    }

    @Override
    public void registerConfigButton(Runnable onButtonClick) {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof OptionsScreen) {
                Screens.getButtons(screen).add(Button.builder(Component.translatable("button.elytraswaperplus.config"), button -> onButtonClick.run())
                        .bounds(scaledWidth / 2 - 100, scaledHeight / 6 + 144, 200, 20)
                        .build());
            }
        });
    }
}