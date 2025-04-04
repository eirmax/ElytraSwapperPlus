package com.eirmax.fabric;


import com.eirmax.elytraswaperplus.ElytraSwapperPlus;
import com.eirmax.elytraswaperplus.network.KeyPressHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class ElytraSwaperPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ElytraSwapperPlus.init();
        PayloadTypeRegistry.playC2S().register(KeyPressHandler.TYPE, KeyPressHandler.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(KeyPressHandler.TYPE, (payload, context) -> KeyPressHandler.handle(payload, context.player()));
    }
}