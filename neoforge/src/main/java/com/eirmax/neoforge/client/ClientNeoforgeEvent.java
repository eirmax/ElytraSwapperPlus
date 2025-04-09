package com.eirmax.neoforge.client;

import com.eirmax.elytraswapperplus.ElytraSwapperPlus;
import com.eirmax.elytraswapperplus.network.KeyPressHandler;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = ElytraSwapperPlus.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientNeoforgeEvent {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        for (Lazy<KeyMapping> key : ClientNeoforgeKeybindEvent.keyMappings) {
            while (key.get().consumeClick()) {
                PacketDistributor.sendToServer(new KeyPressHandler(key.get().getName()));
            }
        }
    }
}
