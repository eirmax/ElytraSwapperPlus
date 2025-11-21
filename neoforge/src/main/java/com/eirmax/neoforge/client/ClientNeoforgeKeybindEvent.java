package com.eirmax.neoforge.client;

import com.eirmax.elytraswaperplus.ElytraSwapperPlus;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = ElytraSwapperPlus.MODID, value = Dist.CLIENT)
@SuppressWarnings("all")
public class ClientNeoforgeKeybindEvent {
    public static final List<Lazy<KeyMapping>> keyMappings = new ArrayList<>();
    public static final KeyMapping.Category ELYTRA_SWAP_PLUS = new KeyMapping.Category(ResourceLocation.fromNamespaceAndPath(ElytraSwapperPlus.MODID, "category.elytraswapplus"));


    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        keyMappings.add(Lazy.of(() -> new KeyMapping("key.elytraswapplus.swap", GLFW.GLFW_KEY_R, ELYTRA_SWAP_PLUS)));
        keyMappings.add(Lazy.of(() -> new KeyMapping("key.elytraswapplus.auto_swap", GLFW.GLFW_KEY_B, ELYTRA_SWAP_PLUS)));

        for (Lazy<KeyMapping> key : keyMappings) {
            event.register(key.get());
        }
    }
}