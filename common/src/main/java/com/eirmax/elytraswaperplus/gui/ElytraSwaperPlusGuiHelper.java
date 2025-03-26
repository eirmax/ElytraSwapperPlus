package com.eirmax.elytraswaperplus.gui;

import com.eirmax.elytraswaperplus.config.ElytraSwapperConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ElytraSwaperPlusGuiHelper {

    public static void initialize(PlatformHelper helper) {
        ElytraSwapperConfig.loadConfig();
        helper.registerConfigButton(() -> {
            Screen currentScreen = Minecraft.getInstance().screen;
            Minecraft.getInstance().setScreen(ElytraSwapperConfig.createConfigScreen(currentScreen));
        });
    }
}