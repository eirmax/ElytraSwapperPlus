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
        int bestScore = -1;

        // Перебираем все 36 слотов инвентаря (0-35)
        for (int i = 0; i < 36; i++) {
            ItemStack stack = client.player.getInventory().getItem(i);
            if (!stack.isEmpty()) {
                if (isElytra(stack) && elytraSlot == -1) {
                    elytraSlot = i;
                } else if (isChestplate(stack)) {
                    int score = calculateChestplateScore(stack);
                    if (score > bestScore) {
                        bestScore = score;
                        bestChestplateSlot = i;
                    }
                }
            }
        }

        ItemStack wornItemStack = client.player.getInventory().getItem(38);

        // Логика свапа: если надета элитра — надеваем нагрудник, если надет нагрудник — надеваем элитру
        if (isElytra(wornItemStack) && bestChestplateSlot != -1) {
            sendSwapPackets(bestChestplateSlot, client);
        } else if (isChestplate(wornItemStack) && elytraSlot != -1) {
            sendSwapPackets(elytraSlot, client);
        } else if (wornItemStack.isEmpty() && elytraSlot != -1) {
            sendSwapPackets(elytraSlot, client);
        } else {
            System.out.println("Нет подходящего предмета для свапа!");
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

    private static void sendSwapPackets(int slotInInventory, Minecraft client) {
        int slotInMenu = slotInInventory + 10; // 0-35 -> 10-45
        int chestplateSlotInMenu = 6; // слот нагрудника

        ItemStack worn = client.player.getInventory().getItem(38);

        if (worn.isEmpty()) {
            // Если слот нагрудника пустой — shift-клик по элитре
            client.gameMode.handleInventoryMouseClick(
                    client.player.containerMenu.containerId,
                    slotInMenu,
                    0,
                    ClickType.QUICK_MOVE,
                    client.player
            );
        } else {
            // Обычный swap, если что-то надето
            client.gameMode.handleInventoryMouseClick(
                    client.player.containerMenu.containerId,
                    slotInMenu,
                    0,
                    ClickType.PICKUP,
                    client.player
            );
            client.gameMode.handleInventoryMouseClick(
                    client.player.containerMenu.containerId,
                    chestplateSlotInMenu,
                    0,
                    ClickType.PICKUP,
                    client.player
            );
            client.gameMode.handleInventoryMouseClick(
                    client.player.containerMenu.containerId,
                    slotInMenu,
                    0,
                    ClickType.PICKUP,
                    client.player
            );
        }
    }
}