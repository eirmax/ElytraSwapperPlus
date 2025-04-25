package com.eirmax.elytraswaperplus.utils;


import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


public class ClientSideSwapUtil {

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
        return stack.is(Items.NETHERITE_CHESTPLATE) ||
                stack.is(Items.DIAMOND_CHESTPLATE) ||
                stack.is(Items.IRON_CHESTPLATE) ||
                stack.is(Items.CHAINMAIL_CHESTPLATE) ||
                stack.is(Items.GOLDEN_CHESTPLATE) ||
                stack.is(Items.LEATHER_CHESTPLATE);
    }
}
    /*public static void sendSwapPackets(int slot, Minecraft client, Player player) {
        int sentSlot = slot;
        if (sentSlot == player.getInventory().getContainerSize() - 1) sentSlot += 5;
        if (sentSlot < player.getInventory().getSelectionSize()) sentSlot += player.getInventory().getSelectionSize();
        clickSlot(client, sentSlot);
        clickSlot(client, 6);
        clickSlot(client, sentSlot);
    }
    private static void clickSlot(Minecraft client, int slot) {
        ClientPacketListener connection = client.getConnection();
        if (connection != null) {
            connection.send(new ServerboundContainerClickPacket(
                    client.player.containerMenu.containerId, client.player.containerMenu.getStateId(), slot, 0, ClickType.PICKUP, ItemStack.EMPTY, null));
        }

    }
    public static void swap(Minecraft client) {
        LocalPlayer player = client.player;
        if (player == null) return;

        ItemStack elytra = findElytra(player);
        ItemStack bestChestplate = getBestChestplate(player);
        if (!elytra.isEmpty() && !player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA)) {
            int elytraSlot = findSlotForItem(player, elytra);
            if (elytraSlot != -1) {
                sendSwapPackets(elytraSlot, client, player);
            }
        }

        else if (!bestChestplate.isEmpty() && !player.getItemBySlot(EquipmentSlot.CHEST).is(bestChestplate.getItem())) {
            int chestplateSlot = findSlotForItem(player, bestChestplate);
            if (chestplateSlot != -1) {
                sendSwapPackets(chestplateSlot, client, player);
            }
        }
    }
    private static void swapItem(LocalPlayer player, ItemStack newItem, EquipmentSlot slot) {
        ItemStack currentItem = player.getItemBySlot(slot);
        player.setItemSlot(slot, newItem);
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (inventory.getItem(i).isEmpty()) {
                inventory.setItem(i, currentItem);
                break;
            }
        }
    }
    private static int findSlotForItem(Player player, ItemStack item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (ItemStack.isSameItemSameComponents(player.getInventory().getItem(i), item)) {
                return i;
            }
        }
        return -1;
    }
}

     */
