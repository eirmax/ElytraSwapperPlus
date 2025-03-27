package com.eirmax.elytraswaperplus.gui;
import net.minecraft.client.gui.screens.Screen;

public interface PlatformHelper {
    Screen createConfigScreen(Screen screen);
    void registerConfigButton(Runnable onButtonClick);
}


















/*
public interface PlatformHelper {
    Screen createConfigScreen(Screen parent);

*/
