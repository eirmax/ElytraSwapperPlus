package com.eirmax.fabric.client;

import com.eirmax.elytraswaperplus.init.ElytraSwaperPlusInit;
import com.eirmax.fabric.fabricplatformhelper.FabricPlatformHelper;
import net.fabricmc.api.ClientModInitializer;

public final class ElytraSwaperPlusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBindingRegistry.init();
        ClientFabricKeybindEvent.init();
        ElytraSwaperPlusInit.init(new FabricPlatformHelper());
    }
}