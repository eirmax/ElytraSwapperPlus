package com.eirmax.neoforge.client;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = "elytraswapperplus", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientNeoforgeKeybindEvent {
    public static final List<Lazy<KeyMapping>> keyMappings = new ArrayList<>();

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        keyMappings.add(Lazy.of(() -> new KeyMapping("key.elytraswapplus.swap", GLFW.GLFW_KEY_R, "category.elytraswapplus")));
        keyMappings.add(Lazy.of(() -> new KeyMapping("key.elytraswapplus.auto_swap", GLFW.GLFW_KEY_B, "category.elytraswapplus")));

        for (Lazy<KeyMapping> key : keyMappings) {
            event.register(key.get());
        }
    }
}