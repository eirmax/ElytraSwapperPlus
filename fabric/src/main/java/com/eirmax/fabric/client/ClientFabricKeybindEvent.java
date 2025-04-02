package com.eirmax.fabric.client;

import com.eirmax.elytraswaperplus.network.KeyPressHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;

import java.util.ArrayList;
import java.util.List;

public class ClientFabricKeybindEvent {
    public static final List<KeyMapping> keyMappings = new ArrayList<>();

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (KeyMapping key : keyMappings) {
                while (client.player != null && key.consumeClick()) {
                    ClientPlayNetworking.send(new KeyPressHandler(key.getName()));
                }
            }
        });
    }
}
