package com.eirmax.elytraswaperplus.network;

import com.eirmax.elytraswaperplus.utils.ArmorHelperUtil;
import com.eirmax.elytraswaperplus.utils.ClientSideSwapUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
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

    public static void handle(KeyPressHandler payload, Minecraft client) {
        if (client != null) {
            if (client.player instanceof LocalPlayer player) {
                if (payload.component.equals("key.elytraswapplus.swap")) {
                    ItemStack bestChestplate = ArmorHelperUtil.getBestChestplate(player);
                    if (!bestChestplate.isEmpty()) {
                        ClientSideSwapUtil.swapElytraAndChestplate(client, bestChestplate);
                    }
                } else if (payload.component.equals("key.elytraswapplus.auto_swap")) {
                    ClientSideSwapUtil.toggleAutoEquip();
                    ClientSideSwapUtil.setAutoEquip(ClientSideSwapUtil.auto_equip);
                }
            }
        }
    }
}