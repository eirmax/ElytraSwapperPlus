package com.eirmax.elytraswapperplus.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SwapUtil {

    public static boolean auto_equip = false;

    public static void toggleAutoEquip() {
        auto_equip = !auto_equip;
    }

    public static void setAutoEquip(boolean value) {
        auto_equip = value;
    }

    public static void tryWearElytra(Player player) {
        ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!chestItem.is(Items.ELYTRA)) {
            if (!chestItem.isEmpty()) {
                player.getInventory().add(chestItem);
            }
            ItemStack elytra = findElytra(player);

            if (!elytra.isEmpty()) {
                player.setItemSlot(EquipmentSlot.CHEST, elytra);
                player.getInventory().removeItem(elytra);
            }
            player.setItemSlot(EquipmentSlot.CHEST, elytra);
        }
    }



    public static void elytraRemoveFirstElytra(Player player) {
        ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestItem.is(Items.ELYTRA)) {
            player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
        } else {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack.is(Items.ELYTRA)) {
                    player.getInventory().removeItem(i, 1);
                    break;
                }
            }
        }
    }


    public static void swap(Player player) {
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.is(Items.ELYTRA)) {
            swapElytraToChest(player);
        } else {
            swapChestToElytra(player);
        }
    }

    public static void swapChestToElytra(Player player) {
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack elytra = findElytra(player);

        if (elytra != null) {
            if (!chest.isEmpty()) {

                if (player.getInventory().getFreeSlot() != -1) {
                    player.getInventory().add(chest);
                } else {
                    player.drop(chest, false);
                }
            }
            player.getInventory().removeItem(elytra);
            player.setItemSlot(EquipmentSlot.CHEST, elytra);
        }
    }

    public static void swapElytraToChest(Player player) {
        ItemStack currentChest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack bestChestplate = ArmorHelperUtil.getBestChestplate(player);

        if (bestChestplate != null) {
            if (currentChest.is(Items.ELYTRA)) {
                player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                if (player.getInventory().getFreeSlot() != -1) {
                    player.getInventory().add(currentChest);
                } else {
                    player.drop(currentChest, false);
                }
            }

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack.is(bestChestplate.getItem())) {
                    player.getInventory().removeItem(stack);
                    break;
                }
            }

            player.setItemSlot(EquipmentSlot.CHEST, bestChestplate);
        }
    }
    public static ItemStack findElytra(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(Items.ELYTRA)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}