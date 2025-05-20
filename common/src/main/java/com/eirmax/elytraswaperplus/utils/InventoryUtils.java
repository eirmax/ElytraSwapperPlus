package com.eirmax.elytraswaperplus.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryUtils {


    public static void swapChestplate(Minecraft client) {
        if (client.player == null || client.player.isDeadOrDying()) return;

        ItemStack worn = client.player.getInventory().getItem(38);

        int bestElytraSlot = getBestElytraSlot(client);
        int bestSlot = ArmorHelperUtil.getBestChestplate(client.player);
        ItemStack bestChestplate = bestSlot != -1 ? client.player.getInventory().getItem(bestSlot) : ItemStack.EMPTY;
        int bestChestplateSlot = -1;
        for (int i = 0; i < client.player.getInventory().getContainerSize(); i++) {
            if (client.player.getInventory().getItem(i) == bestChestplate) {
                bestChestplateSlot = i;
                break;
            }
        }

        if (isElytra(worn) && bestChestplateSlot != -1) {
            swap(bestChestplateSlot, client);
        } else if (isChestplate(worn) && bestElytraSlot != -1) {
            swap(bestElytraSlot, client);
        } else if (worn.isEmpty() && bestElytraSlot != -1) {
            swap(bestElytraSlot, client);
        }
    }
    public static List<Integer> getElytraSlots(Minecraft client) {
        List<Integer> elytraSlots = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            ItemStack stack = client.player.getInventory().getItem(i);
            if (isElytra(stack)) {
                elytraSlots.add(i);
            }
        }
        return elytraSlots;
    }
    public static List<Integer> getChestplateSlots(Minecraft client) {
        List<Integer> chestplateSlots = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            ItemStack stack = client.player.getInventory().getItem(i);
            if (isChestplate(stack)) {
                chestplateSlots.add(i);
            }
        }
        return chestplateSlots;
    }

    public static int getBestElytraSlot(Minecraft client) {
        return getElytraSlots(client).stream()
                .max(Comparator.comparingInt(slot -> getElytraStat(client.player.getInventory().getItem(slot))))
                .orElse(-1);
    }
    public static int getBestChestplateSlot(Minecraft client) {
        return getChestplateSlots(client).stream()
                .max(Comparator.comparingInt(slot -> ArmorHelperUtil.calculateScore(client.player.getInventory().getItem(slot))))
                .orElse(-1);
    }
    public static boolean isElytra(ItemStack stack) {
        return stack != null && stack.getItem() == Items.ELYTRA;
    }

    public static boolean isChestplate(ItemStack stack) {
        if (stack == null) return false;
        return stack.getItem() == Items.NETHERITE_CHESTPLATE
                || stack.getItem() == Items.DIAMOND_CHESTPLATE
                || stack.getItem() == Items.IRON_CHESTPLATE
                || stack.getItem() == Items.CHAINMAIL_CHESTPLATE
                || stack.getItem() == Items.GOLDEN_CHESTPLATE
                || stack.getItem() == Items.LEATHER_CHESTPLATE;
    }

    public static int getElytraStat(ItemStack elytraItem) {
        int stat = 0;
        if (elytraItem == null) return stat;
        stat += EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) Enchantments.MENDING, elytraItem) * 3;
        stat += EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) Enchantments.UNBREAKING, elytraItem) * 2;
        return stat;
    }

    public static void swap(int slotInInventory, Minecraft client) {
        int slot2 = slotInInventory;
        if (slot2 == 40) slot2 = 45;
        if (slot2 < 9) slot2 += 36;

        try {
            client.gameMode.handleInventoryMouseClick(
                    client.player.containerMenu.containerId, slot2, 0, ClickType.PICKUP, client.player
            );
            client.gameMode.handleInventoryMouseClick(
                    client.player.containerMenu.containerId, 6, 0, ClickType.PICKUP, client.player
            );
            client.gameMode.handleInventoryMouseClick(
                    client.player.containerMenu.containerId, slot2, 0, ClickType.PICKUP, client.player
            );
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}