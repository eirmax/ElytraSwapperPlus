package com.eirmax.fabric.client;

import net.fabricmc.api.ClientModInitializer;

public final class ElytraSwapperPlusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBindingRegistry.init();
        ClientFabricKeybindEvent.init();
    }
}