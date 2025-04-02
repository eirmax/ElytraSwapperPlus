package com.eirmax.neoforge;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.network.KeyPressHandler;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

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
            if (context.player() instanceof ServerPlayer serverPlayer) KeyPressHandler.handle(data, serverPlayer);
        });
    }
}