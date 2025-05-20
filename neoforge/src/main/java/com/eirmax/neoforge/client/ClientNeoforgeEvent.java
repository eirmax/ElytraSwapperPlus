package com.eirmax.neoforge.client;

import com.eirmax.elytraswaperplus.utils.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.util.Lazy;

@EventBusSubscriber(modid = "elytraswapperplus", bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientNeoforgeEvent {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        for (Lazy<KeyMapping> key : ClientNeoforgeKeybindEvent.keyMappings) {
            while (Minecraft.getInstance().player != null && key.get().consumeClick()) {
                if (key.get().getName().equals("key.elytraswapplus.swap")) {
                    System.out.println("Swapping chestplate!");
                    InventoryUtils.swapChestplate(Minecraft.getInstance());
                }
            }
        }
    }
}