package com.eirmax.elytraswaperplus.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Environment(EnvType.CLIENT)
public class ClientSideSwapUtil {
    public static boolean auto_equip = false;

    public static void toggleAutoEquip() {
        auto_equip = !auto_equip;
    }

    public static void setAutoEquip(boolean value) {
        auto_equip = value;
    }

    public static ItemStack tryWearElytra(Minecraft client) {
        LocalPlayer player = client.player;
        if (player == null || client.level == null) {
            return null;
        }

        ItemStack wornItem = player.getInventory().getItem(EquipmentSlot.CHEST.getIndex());
        if (!isElytra(wornItem)) {
            if (!ArmorHelperUtil.getBestChestplate(player).isEmpty()) {
                swapElytraAndChestplate(client, ArmorHelperUtil.getBestChestplate(player));
            }
        }
        return wornItem;
    }


    private static int findElytraSlot(LocalPlayer player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (isElytra(stack)) {
                return slot;
            }
        }
        return -1;
    }

    private static int findChestplateSlot(LocalPlayer player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (isChestplate(stack)) {
                return slot;
            }
        }
        return -1;
    }


    public static void swapElytraAndChestplate(Minecraft client, ItemStack bestChestplate) {
        if (client.player == null) return;

        if (client.player.isDeadOrDying()) return;

        int elytraSlot = -1;
        int chestplateSlot = -1;

        int HOTBAR_SIZE = Inventory.getSelectionSize();
        int MAIN_SIZE = Inventory.INVENTORY_SIZE;
        int TOTAL_SIZE = MAIN_SIZE + 1;
        int[] range = new int[TOTAL_SIZE];

        for (int i = 0; i < MAIN_SIZE - HOTBAR_SIZE; i++) {
            range[i] = i + HOTBAR_SIZE;
        }

        // Off hand
        range[MAIN_SIZE - HOTBAR_SIZE] = Inventory.SLOT_OFFHAND;

        // Hotbar
        for (int i = 0; i < HOTBAR_SIZE; i++) {
            range[i + MAIN_SIZE - HOTBAR_SIZE + 1] = i;
        }

        for (int slot : range) {
            ItemStack stack = client.player.getInventory().getItem(slot);

            if (stack.isEmpty()) {
                continue;
            }

            if (isElytra(stack) && elytraSlot < 0) {
                elytraSlot = slot;
            } else if (isChestplate(stack) && chestplateSlot < 0) {
                chestplateSlot = slot;
            }
        }

        ItemStack wornItemStack = client.player.getInventory().getItem(EquipmentSlot.CHEST.getIndex());
        if (wornItemStack.isEmpty() && elytraSlot >= 0) {
            sendSwapPackets(elytraSlot, client.player);
        } else if (isElytra(wornItemStack) && chestplateSlot >= 0) {
            sendSwapPackets(chestplateSlot, client.player);
        } else if (isChestplate(wornItemStack) && elytraSlot >= 0) {
            sendSwapPackets(elytraSlot, client.player);
        }
    }


    private static void sendSwapPackets(int slot, LocalPlayer player) {
        int button = 0;
        ClickType clickType = ClickType.PICKUP;

        player.containerMenu.clicked(slot, button, clickType, player);
        player.containerMenu.clicked(EquipmentSlot.CHEST.getIndex(), button, clickType, player);
        player.containerMenu.clicked(slot, button, clickType, player);
    }

    private static boolean isElytra(ItemStack stack) {
        return stack.is(Items.ELYTRA);
    }

    private static boolean isChestplate(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getType().getSlot() == EquipmentSlot.CHEST;
    }
}