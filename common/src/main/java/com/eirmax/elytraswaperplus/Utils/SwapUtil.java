package com.eirmax.elytraswaperplus.Utils;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;



public class SwapUtil {
    public static void tryWearElytra(ClientPlayerEntity player) {
        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack elytra = findElytra(player);
        if (elytra != null && !chestplate.isOf(Items.ELYTRA)) {
            player.equipStack(EquipmentSlot.CHEST, elytra);
            player.getInventory().insertStack(chestplate);
        }
    }

    public static ItemStack findElytra(ClientPlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(Items.ELYTRA)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}