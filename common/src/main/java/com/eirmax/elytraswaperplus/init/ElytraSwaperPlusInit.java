package com.eirmax.elytraswaperplus.init;
import com.eirmax.elytraswaperplus.config.ElytraSwapperConfig;
import com.eirmax.elytraswaperplus.gui.PlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
public class ElytraSwaperPlusInit {
    public static void init(PlatformHelper helper) {
        ElytraSwapperConfig.loadConfig();
        helper.registerConfigButton(() -> {
            Screen currentScreen = Minecraft.getInstance().screen;
            Minecraft.getInstance().setScreen(ElytraSwapperConfig.createConfigScreen(currentScreen));
        });
    }
}
