package com.eirmax.elytraswaperplus.mixin;

import com.eirmax.elytraswaperplus.utils.ArmorHelperUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Player.class)
public class StopFlying {
    @Inject(method = "tick", at = @At("TAIL"))
    public void onTickMovementEnd(CallbackInfo ci) {
        Player player = (Player) (Object) this;

        if (player.onGround() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem().equals(Items.ELYTRA)) {

            ItemStack bestChestplate = ArmorHelperUtil.getBestChestplate(player);
            if (bestChestplate != null) {
                player.setItemSlot(EquipmentSlot.CHEST, bestChestplate);
            }
        }
    }
}