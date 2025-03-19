package com.eirmax.neoforge.client;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.network.KeyPressHandler;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = ElytraSwaperPlus.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientNeoforgeKeybindEvent {
    public static final List<Lazy<KeyMapping>> keyMappings = new ArrayList<>();

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        KeyMapping bind = new KeyMapping("key.elytraswapplus.swap", GLFW.GLFW_KEY_R, "category.elytraswapplus");
        keyMappings.add(Lazy.of(() -> bind));

        for (Lazy<KeyMapping> key : keyMappings) {
            event.register(key.get());
        }
    }
}
