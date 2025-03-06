package com.eirmax.fabric.client;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.ModPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import java.util.function.Consumer;

public final class ElytraSwaperPlusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ElytraSwaperPlus.entrypoint(new FabricPlatform());
    }
    public static class FabricPlatform extends ModPlatform {

        @Override
        public String getModloader() {
            return "Fabric";
        }

        @Override
        public boolean isModLoaded(String modloader) {
            return FabricLoader.getInstance().isModLoaded(modloader);
        }

        @Override
        public void registerClientTickEvent(Consumer<MinecraftClient> o) {
            ClientTickEvents.END_CLIENT_TICK.register(o::accept);
        }

        @Override
        public KeyBinding registerKeyBind(String translationKeyName, int defaultKeyId, String category) {
            var bind = new KeyBinding(translationKeyName,defaultKeyId,category);
            KeyBindingHelper.registerKeyBinding(bind);
            return bind;
        }
    }
}
