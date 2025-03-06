package com.eirmax.elytraswaperplus.event;

import com.eirmax.elytraswaperplus.network.KeyBind;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import static com.eirmax.elytraswaperplus.Utils.SwapUtil.findElytra;

public class Events {
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyBind.swapKey.wasPressed()) {
                PlayerEntity player = MinecraftClient.getInstance().player;
                if (player != null) {
                    swapChestToElytra(player);
                }
            }
        });
    }

    private static void swapChestToElytra(PlayerEntity player) {
        ItemStack chest = player.getInventory().getArmorStack(2);
        ItemStack elytra = findElytra((ClientPlayerEntity) player);

        if (elytra != null) {
            player.getInventory().setStack(2, elytra);
            player.getInventory().insertStack(chest);
        }
    }
}