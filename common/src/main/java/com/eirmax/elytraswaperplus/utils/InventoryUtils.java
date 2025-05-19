package com.eirmax.elytraswaperplus.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryUtils {

    public static void swapChestplate(Minecraft client) {
        if (client.player == null || client.player.isDeadOrDying()) {
            return;
        }

        int elytraSlot = -1;
        int bestChestplateSlot = -1;

        int HOTBAR_SIZE = Inventory.getSelectionSize();
        int MAIN_SIZE = Inventory.INVENTORY_SIZE;
        int TOTAL_SIZE = MAIN_SIZE + 1;

        int[] range = new int[TOTAL_SIZE];

        for (int i = 0; i < MAIN_SIZE - HOTBAR_SIZE; i++) {
            range[i] = i + HOTBAR_SIZE;
        }
        range[MAIN_SIZE - HOTBAR_SIZE] = Inventory.SLOT_OFFHAND;
        for (int i = 0; i < HOTBAR_SIZE; i++) {
            range[i + MAIN_SIZE - HOTBAR_SIZE + 1] = i;
        }

        int bestScore = -1;
        for (int slot : range) {
            ItemStack stack = client.player.getInventory().getItem(slot);
            if (!stack.isEmpty()) {
                if (isElytra(stack) && elytraSlot < 0) {
                    elytraSlot = slot;
                } else if (isChestplate(stack)) {
                    int score = calculateChestplateScore(stack);
                    if (score > bestScore) {
                        bestScore = score;
                        bestChestplateSlot = slot;
                    }
                }
            }
        }

        ItemStack wornItemStack = client.player.getInventory().getItem(38);

        if (wornItemStack.isEmpty() && elytraSlot >= 0) {
            sendSwapPackets(elytraSlot, client);
        }
        else if (isElytra(wornItemStack) && bestChestplateSlot >= 0) {
            sendSwapPackets(bestChestplateSlot, client);
        }
        else if (isChestplate(wornItemStack) && elytraSlot >= 0) {
            sendSwapPackets(elytraSlot, client);
        }
    }

    private static boolean isElytra(ItemStack stack) {
        return stack.getItem() == Items.ELYTRA;
    }

    private static boolean isChestplate(ItemStack stack) {
        return stack.getItem() == Items.NETHERITE_CHESTPLATE
                || stack.getItem() == Items.DIAMOND_CHESTPLATE
                || stack.getItem() == Items.IRON_CHESTPLATE
                || stack.getItem() == Items.CHAINMAIL_CHESTPLATE
                || stack.getItem() == Items.GOLDEN_CHESTPLATE
                || stack.getItem() == Items.LEATHER_CHESTPLATE;
    }

    private static int calculateChestplateScore(ItemStack stack) {
        AtomicInteger score = new AtomicInteger();

        if (stack.getItem() == Items.NETHERITE_CHESTPLATE) score.addAndGet(6);
        else if (stack.getItem() == Items.DIAMOND_CHESTPLATE) score.addAndGet(5);
        else if (stack.getItem() == Items.IRON_CHESTPLATE) score.addAndGet(4);
        else if (stack.getItem() == Items.CHAINMAIL_CHESTPLATE) score.addAndGet(3);
        else if (stack.getItem() == Items.GOLDEN_CHESTPLATE) score.addAndGet(2);
        else if (stack.getItem() == Items.LEATHER_CHESTPLATE) score.addAndGet(1);

        if (stack.isEnchanted()) score.addAndGet(1);
        Map<ResourceKey<Enchantment>, Integer> enchantmentPoints = new HashMap<>();
        enchantmentPoints.put(Enchantments.PROTECTION, 7);
        enchantmentPoints.put(Enchantments.UNBREAKING, 6);
        enchantmentPoints.put(Enchantments.MENDING, 5);
        enchantmentPoints.put(Enchantments.PROJECTILE_PROTECTION, 2);
        enchantmentPoints.put(Enchantments.BLAST_PROTECTION, 1);
        enchantmentPoints.put(Enchantments.FIRE_PROTECTION, 3);
        enchantmentPoints.put(Enchantments.THORNS, 4);
        stack.getEnchantments().entrySet().forEach(e -> {
            Enchantment enchantment = e.getKey().value();
            int level = e.getValue();
            Integer points = enchantmentPoints.get(enchantment);
            if (points != null) {
                score.addAndGet(points * level);
            }
        });

        return score.get();
    }


    private static void sendSwapPackets(int slot, Minecraft client) {
        int sentSlot = slot;

        if (sentSlot == Inventory.SLOT_OFFHAND) {
            sentSlot += 5;
        }
        if (sentSlot < Inventory.getSelectionSize()) {
            sentSlot += Inventory.INVENTORY_SIZE;
        }

        client.player.containerMenu.clicked(0, sentSlot, ClickType.PICKUP, client.player);
        client.player.containerMenu.clicked(0, sentSlot, ClickType.PICKUP, client.player);
        client.player.containerMenu.clicked(0, sentSlot, ClickType.PICKUP, client.player);
    }
}