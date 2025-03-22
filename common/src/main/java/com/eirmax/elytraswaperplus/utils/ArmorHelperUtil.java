package com.eirmax.elytraswaperplus.utils;


import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.concurrent.atomic.AtomicInteger;

public class ArmorHelperUtil {
    public static ItemStack getBestChestplate(Player player) {
        ItemStack best = null;
        int highestScore = -1;

        for (ItemStack stack : player.getInventory().items) {
            if (isChestplate(stack) && !stack.is(Items.ELYTRA)) {
                int score = calculateScore(stack);
                if (score > highestScore) {
                    highestScore = score;
                    best = stack;
                }
            }
        }
        return best;
    }

    private static boolean isChestplate(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem &&
                ((ArmorItem) stack.getItem()).getEquipmentSlot() == EquipmentSlot.CHEST;
    }

    private static int calculateScore(ItemStack stack) {
        AtomicInteger score = new AtomicInteger();

        if (stack.getItem() == Items.NETHERITE_CHESTPLATE) score.addAndGet(6);
        else if (stack.getItem() == Items.DIAMOND_CHESTPLATE) score.addAndGet(5);
        else if (stack.getItem() == Items.IRON_CHESTPLATE) score.addAndGet(4);
        else if (stack.getItem() == Items.CHAINMAIL_CHESTPLATE) score.addAndGet(3);
        else if (stack.getItem() == Items.GOLDEN_CHESTPLATE) score.addAndGet(2);
        else if (stack.getItem() == Items.LEATHER_CHESTPLATE) score.addAndGet(1);

        stack.getEnchantments().entrySet().forEach(e -> score.addAndGet(e.getIntValue()));
        return score.get();
    }
    public static ItemStack findElytra(Player player) {
        return SwapUtil.findElytra(player);
    }
}