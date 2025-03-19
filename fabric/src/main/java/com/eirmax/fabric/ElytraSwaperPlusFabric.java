package com.eirmax.fabric;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.utils.SwapUtil;
import com.eirmax.elytraswaperplus.network.KeyPressHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class ElytraSwaperPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ElytraSwaperPlus.init();

        PayloadTypeRegistry.playC2S().register(KeyPressHandler.TYPE, KeyPressHandler.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(KeyPressHandler.TYPE, (payload, context) -> SwapUtil.swapChestToElytra(context.player()));
    }
}
