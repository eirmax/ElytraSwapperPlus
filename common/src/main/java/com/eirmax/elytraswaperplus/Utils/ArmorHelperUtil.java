package com.eirmax.elytraswaperplus.Utils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;

public class ArmorHelperUtil {
    public static ItemStack getBestChestplate(PlayerEntity player) {
        ItemStack best = null;
        int highestScore = -1;

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (isChestplate(stack)) {
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
                ((ArmorItem) stack.getItem()).getSlotType() == EquipmentSlot.CHEST;
    }

    private static int calculateScore(ItemStack stack) {
        int score = 0;
        if (stack.getItem() == Items.NETHERITE_CHESTPLATE) score += 3;
        else if (stack.getItem() == Items.DIAMOND_CHESTPLATE) score += 2;
        else if (stack.getItem() == Items.IRON_CHESTPLATE) score += 1;
        Map<Enchantment, Integer> enchants = (Map<Enchantment, Integer>) EnchantmentHelper.getEnchantments(stack);
        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
            score += entry.getValue();
        }

        return score;
    }
}