package com.eirmax.fabric.client;

import com.eirmax.elytraswaperplus.utils.InventoryUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import java.util.ArrayList;
import java.util.List;

public class ClientFabricKeybindEvent {
    public static final List<KeyMapping> keyMappings = new ArrayList<>();

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (KeyMapping key : keyMappings) {
                while (client.player != null && key.consumeClick()) {
                    if (key.getName().equals("key.elytraswapplus.swap")) {
                        System.out.println("Swapping chestplate!");
                        InventoryUtils.swapChestplate(Minecraft.getInstance());
                    }
                }
            }
        });
    }
}