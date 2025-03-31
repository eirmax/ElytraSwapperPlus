package com.eirmax.neoforge.client;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import net.neoforged.neoforge.common.util.Lazy;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = ElytraSwaperPlus.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientNeoforgeKeybindEvent {
    public static final List<Lazy<KeyMapping>> keyMappings = new ArrayList<>();

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        KeyMapping swap_elytra = new KeyMapping("key.elytraswaperplus.swap", GLFW.GLFW_KEY_R, "category.elytraswaperplus");
        KeyMapping auto_equip = new KeyMapping("key.elytraswaperplus.auto_equip", GLFW.GLFW_KEY_B, "category.elytraswaperplus");
        keyMappings.add(Lazy.of(() -> swap_elytra));
        keyMappings.add(Lazy.of(() -> auto_equip));
        for (Lazy<KeyMapping> key : keyMappings) {
            event.register(key.get());
        }
    }
}
