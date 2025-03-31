package com.eirmax.elytraswaperplus.network;

import com.eirmax.elytraswaperplus.network.NetworkManager;
import com.eirmax.elytraswaperplus.utils.SwapUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public record KeyPressHandler(Integer keyId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<KeyPressHandler> TYPE = new CustomPacketPayload.Type<>(NetworkManager.KEYBIND_PACKET_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, KeyPressHandler> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull KeyPressHandler decode(RegistryFriendlyByteBuf buf) {
            return new KeyPressHandler(buf.readInt());
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, KeyPressHandler packet) {
            buf.writeInt(packet.keyId);
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(KeyPressHandler payload, ServerPlayer player) {
        if (player != null) {
            if (payload.keyId == GLFW.GLFW_KEY_R) {
                SwapUtil.swap(player);
            } else if (payload.keyId == GLFW.GLFW_KEY_B) {
                SwapUtil.toggleAutoEquip();
                SwapUtil.setAutoEquip(player);
            }
        }
    }
}