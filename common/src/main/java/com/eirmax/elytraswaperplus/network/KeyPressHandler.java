package com.eirmax.elytraswaperplus.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

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
}