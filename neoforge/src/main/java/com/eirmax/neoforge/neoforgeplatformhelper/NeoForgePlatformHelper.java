package com.eirmax.neoforge.neoforgeplatformhelper;


import com.eirmax.elytraswaperplus.gui.PlatformHelper;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = "elytraswaperplus")
public class NeoForgePlatformHelper implements PlatformHelper {
    @Override
    public Screen createConfigScreen(Screen screen) {
        return screen;
    }

    @Override
    public void registerConfigButton(Runnable onButtonClick) {

        GuiScreenEvent.InitGuiEvent.Post event = new GuiScreenEvent.InitGuiEvent.Post();
        event.addListener((screen, buttons) -> {
            if (screen instanceof OptionsScreen) {
                buttons.add(new Button(screen.width / 2 - 100, screen.height / 6 + 144, 200, 20,
                        Component.translatable("button.elytraswaperplus.config"), button -> onButtonClick.run()));
            }
        });
    }
}











    /*  @Override
    public Screen createConfigScreen(Screen parent) {
        return ElytraSwapperConfig.createConfigScreen(parent);

   */
