package com.eirmax.elytraswaperplus.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


public class SwapUtil {
    public static void tryWearElytra(Player player) {
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack elytra = ArmorHelperUtil.findElytra(player);

        if (elytra != null && !chestplate.is(Items.ELYTRA)) {
            if (player.getInventory().getFreeSlot() != -1 || chestplate.isEmpty()) {
                player.getInventory().add(chestplate);
            }
            player.setItemSlot(EquipmentSlot.CHEST, elytra);
        }
    }

    public static ItemStack findElytra(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(Items.ELYTRA)) {
                return stack;
            }
        }
        return null;
    }


    public static void swapChestToElytra(Player player) {
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack elytra = findElytra(player);

        if (elytra != null) {
            player.getInventory().add(chest);
            player.setItemSlot(EquipmentSlot.CHEST, elytra);
            player.getInventory().removeItem(elytra);
        }
    }
    public static void swapToBestChestplate(Player player) {
        ItemStack currentChest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack bestChestplate = ArmorHelperUtil.getBestChestplate(player);

        if (bestChestplate != null && !currentChest.is(bestChestplate.getItem())) {
            if (player.getInventory().getFreeSlot() != -1 || currentChest.isEmpty()) {
                player.getInventory().add(currentChest);
            }
            player.setItemSlot(EquipmentSlot.CHEST, bestChestplate);
        }
    }
}