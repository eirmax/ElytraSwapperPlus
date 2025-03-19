package com.eirmax.elytraswaperplus.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


public class SwapUtil {
    public static void tryWearElytra(Player player) {
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack elytra = findElytra(player);
        if (elytra != null && !chestplate.is(Items.ELYTRA)) {
            player.setItemSlot(EquipmentSlot.CHEST, elytra);
            player.getInventory().add(chestplate);
        }
    }

    public static ItemStack findElytra(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(Items.ELYTRA)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public static void swapChestToElytra(Player player) {
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack elytra = findElytra(player);

        if (elytra != null && !elytra.isEmpty()) {
            player.setItemSlot(EquipmentSlot.CHEST, elytra.copy());
            player.getInventory().add(chest.copy());
            player.getInventory().removeItem(elytra);
        }
    }
}