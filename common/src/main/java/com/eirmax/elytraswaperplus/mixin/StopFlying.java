package com.eirmax.elytraswaperplus.mixin;

import com.eirmax.elytraswaperplus.Utils.ArmorHelperUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPlayerEntity.class)
public class StopFlying {
    @Inject(method = "tickMovement", at = @At("TAIL"))
    public void onTickMovementEnd(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        if (player.isOnGround() &&
                player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {

            ItemStack bestChestplate = ArmorHelperUtil.getBestChestplate(player);
            if (bestChestplate != null) {
                player.equipStack(EquipmentSlot.CHEST, bestChestplate);
            }
        }


    }
}