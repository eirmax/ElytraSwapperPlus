package com.eirmax.elytraswaperplus.utils;


import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


public class ClientSideSwapUtil {
    public static boolean auto_equip = false;

    public static void toggleAutoEquip() {
        auto_equip = !auto_equip;
    }

    public static void setAutoEquip(boolean value) {
        auto_equip = value;
    }
   public static Inventory PlayerInventory;
    public static void swap(Minecraft client) {
        LocalPlayer player = client.player;
        if (player == null) return;

        int elytraSlot = -1;
        int chestplateSlot = -1;

        int HOTBAR_SIZE = PlayerInventory.getContainerSize();
        int MAIN_SIZE = PlayerInventory.getSelectionSize();
        int TOTAL_SIZE = MAIN_SIZE + 1;


        int[] range = new int[TOTAL_SIZE];

        for (int i = 0; i < MAIN_SIZE - HOTBAR_SIZE; i++) {
            range[i] = i + HOTBAR_SIZE;
        }

        range[MAIN_SIZE - HOTBAR_SIZE] = PlayerInventory.offhand.size();
        for (int i = 0; i < HOTBAR_SIZE; i++) {
            range[i + MAIN_SIZE - HOTBAR_SIZE + 1] = i;
        }
        for (int slot : range) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.isEmpty()) continue;

            if (isElytra(stack) && elytraSlot < 0) {
                elytraSlot = slot;
            } else if (isChestplate(stack) && chestplateSlot < 0) {
                chestplateSlot = slot;
            }
        }

        ItemStack wornItem = player.getInventory().getItem(EquipmentSlot.CHEST.getIndex());
        if (wornItem.isEmpty() && elytraSlot >= 0) {
            sendSwapPackets(elytraSlot, client);
        } else if (isElytra(wornItem) && chestplateSlot >= 0) {
            sendSwapPackets(chestplateSlot, client);
        } else if (isChestplate(wornItem) && elytraSlot >= 0) {
            sendSwapPackets(elytraSlot, client);
        }
    }
    public static void sendSwapPackets(int slot, Minecraft client) {
        int sentSlot = slot;
        if (sentSlot == PlayerInventory.offhand.size()) sentSlot += 5;

        clickSlot(client, sentSlot);

        clickSlot(client, EquipmentSlot.CHEST.getIndex());

        clickSlot(client, sentSlot);
    }

    private static void clickSlot(Minecraft client, int slot) {
        ClientPacketListener connection = client.getConnection();
        if (connection != null) {
            connection.send(new ServerboundContainerClickPacket(client.player.containerMenu.containerId, client.player.containerMenu.getStateId(), slot, 0, ClickType.PICKUP, ItemStack.EMPTY, null));
        }
    }

    private static boolean isElytra(ItemStack stack) {
        return stack.is(Items.ELYTRA);
    }

    private static boolean isChestplate(ItemStack stack) {
        boolean isChestplate = false;
        if (stack.getItem() instanceof ArmorItem) {
            ArmorItem armorItem = (ArmorItem)stack.getItem();
            if (armorItem.getType().getSlot() == EquipmentSlot.CHEST) {
                isChestplate = true;
            }
        }
        return isChestplate;
    }
    }
