package com.eirmax.neoforge;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.network.KeyPressHandler;
import com.eirmax.elytraswaperplus.utils.SwapUtil;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = ElytraSwaperPlus.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CommonNeoforgeEvent {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                KeyPressHandler.TYPE,
                KeyPressHandler.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        CommonNeoforgeEvent::handleKeyPressOnServer,
                        CommonNeoforgeEvent::handleKeyPressOnServer
                )
        );
    }

    public static void handleKeyPressOnServer(final KeyPressHandler data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                if (data.keyId() == GLFW.GLFW_KEY_R) {
                    SwapUtil.swap(player);
                } else if (data.keyId() == GLFW.GLFW_KEY_B) {
                    SwapUtil.toggleAutoEquip();
                    SwapUtil.setAutoEquip(player);
                }
            }
        });
    }
}