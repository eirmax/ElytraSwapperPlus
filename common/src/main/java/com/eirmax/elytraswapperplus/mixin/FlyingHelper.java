package com.eirmax.elytraswapperplus.mixin;

import com.eirmax.elytraswapperplus.utils.SwapUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class FlyingHelper {

    @Inject(method = "tick", at = @At("TAIL"))
    public void onTickMovementEnd(CallbackInfo ci) {
        if (!SwapUtil.auto_equip) {
            return;
        }

        Player player = (Player) (Object) this;
        ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);

        if (!player.hasEffect(MobEffects.SLOW_FALLING) && !player.isInPowderSnow) {
            if (player.onGround()) {
                if (player.isFallFlying()) {
                    player.stopFallFlying();
                }
            } else if (!player.isInWater() && !player.isInLava() &&
                    player.getDeltaMovement().y < 0 &&
                    !player.isFallFlying()) {

                if (!chestItem.is(Items.ELYTRA)) {
                    SwapUtil.tryWearElytra(player);
                    chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
                }
                if (player.getDeltaMovement().y < -0.5 && chestItem.is(Items.ELYTRA) && !player.isFallFlying()) {
                    player.startFallFlying();
                }
            }
            if (chestItem.is(Items.ELYTRA) && player.getInventory().countItem(Items.ELYTRA) > 1) {
                SwapUtil.elytraRemoveFirstElytra(player);
            }
        }
    }
}