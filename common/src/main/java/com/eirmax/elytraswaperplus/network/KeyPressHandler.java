package com.eirmax.elytraswaperplus.network;

import com.eirmax.elytraswaperplus.utils.SwapUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record KeyPressHandler(String component) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<KeyPressHandler> TYPE = new CustomPacketPayload.Type<>(NetworkManager.KEYBIND_PACKET_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, KeyPressHandler> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull KeyPressHandler decode(RegistryFriendlyByteBuf buf) {
            return new KeyPressHandler(buf.readUtf());
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, KeyPressHandler packet) {
            buf.writeUtf(packet.component);
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(KeyPressHandler payload, ServerPlayer player) {
        if (player != null) {
            if (payload.component.equals("key.elytraswapplus.swap")) {
                SwapUtil.swap(player);
            } else if (payload.component.equals("key.elytraswapplus.auto_swap")) {
                SwapUtil.toggleAutoEquip();
                SwapUtil.setAutoEquip(SwapUtil.auto_equip);

                //Send message to player
                Component message = Component.translatable(
                        "msg.elytraswapplus.auto_swap." + (SwapUtil.auto_equip ? "enabled" : "disabled")
                );
                player.displayClientMessage(message, false);
            }
        }
    }
}