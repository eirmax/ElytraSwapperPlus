package com.eirmax.neoforge;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.ModPlatform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ElytraSwaperPlusNeoForgeClient {

    public static IEventBus MOD_EVENT_BUS;
    public static IEventBus GAME_EVENT_BUS;
        public ElytraSwaperPlusNeoForgeClient(IEventBus eventBus) {
            MOD_EVENT_BUS = eventBus;
            GAME_EVENT_BUS = NeoForge.EVENT_BUS;
            ElytraSwaperPlus.entrypoint(new NeoForgePlatform());
        }
    public static class NeoForgePlatform extends ModPlatform {

        @Override
        public String getModloader() {
            return "NeoForge";
        }

        @Override
        public boolean isModLoaded(String modId) {
            return ModList.get().isLoaded(modId);
        }


        List<Consumer<MinecraftClient>> clientEvents = new ArrayList<>();

        @Override
        public void registerClientTickEvent(Consumer<MinecraftClient> client) {
            GAME_EVENT_BUS.addListener((Consumer<ClientTickEvent.Post>) event -> client.accept(MinecraftClient.getInstance()));
        }

        @Override
        public KeyBinding registerKeyBind(String translationKeyName, int defaultKeyId, String category) {
            var keyBinding = new KeyBinding(translationKeyName, defaultKeyId, category);
            MOD_EVENT_BUS.addListener((Consumer<RegisterKeyMappingsEvent>) event -> event.register(keyBinding));
            return keyBinding;
        }
    }
}
