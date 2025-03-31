package com.eirmax.fabric;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.network.KeyPressHandler;
import com.eirmax.elytraswaperplus.utils.SwapUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

public final class ElytraSwaperPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ElytraSwaperPlus.init();
        PayloadTypeRegistry.playC2S().register(KeyPressHandler.TYPE, KeyPressHandler.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(KeyPressHandler.TYPE, (payload, context) -> {
            Player player = context.player();
            if (player != null) {
                if (payload.keyId() == GLFW.GLFW_KEY_R) {
                    SwapUtil.swap(player);
                } else if (payload.keyId()== GLFW.GLFW_KEY_B) {
                    SwapUtil.toggleAutoEquip();
                    SwapUtil.setAutoEquip(player);
                }
            }
        });
    }
}