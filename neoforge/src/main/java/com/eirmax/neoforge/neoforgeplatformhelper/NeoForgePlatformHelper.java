package com.eirmax.neoforge.neoforgeplatformhelper;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.config.ElytraSwapperConfig;
import com.eirmax.elytraswaperplus.gui.PlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = ElytraSwaperPlus.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NeoForgePlatformHelper implements PlatformHelper {

    @Override
    public Screen createConfigScreen(Screen screen) {
        return ElytraSwapperConfig.createConfigScreen(screen);
    }

    @Override
    public void registerConfigButton(Runnable onButtonClick) {
    }

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof PauseScreen) {
            int buttonWidth = 120;
            int buttonHeight = 20;
            int x = 10;
            int y = event.getScreen().height / 4;

            Button configButton = Button.builder(Component.translatable("button.elytraswaperplus.config"), button -> {
                Screen currentScreen = Minecraft.getInstance().screen;
                Minecraft.getInstance().setScreen(ElytraSwapperConfig.createConfigScreen(currentScreen));
            }).bounds(x, y, buttonWidth, buttonHeight).build();

            event.addListener(configButton);
        }
    }
}

    /*  @Override
    public Screen createConfigScreen(Screen parent) {
        return ElytraSwapperConfig.createConfigScreen(parent);

   */
