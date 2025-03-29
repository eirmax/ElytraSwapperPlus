package com.eirmax.elytraswaperplus.mixin;

import com.eirmax.elytraswaperplus.config.ElytraSwapperConfig;
import com.eirmax.elytraswaperplus.utils.SwapUtil;
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
public class FlyingManager {

    @Inject(method = "tick", at = @At("TAIL"))
    public void onTickMovementEnd(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);

        if (!player.isCreative() && !player.hasEffect(MobEffects.SLOW_FALLING)) {
            if (!player.isInWater() && !player.isInLava() &&
                    player.getDeltaMovement().y < ElytraSwapperConfig.CONFIG.heightFalling &&
                    !player.isFallFlying()) {

                if (!chestItem.is(Items.ELYTRA)) {
                    SwapUtil.tryWearElytra(player);
                }

                if (player.isFallFlying() && !chestItem.is(Items.ELYTRA)) {
                    SwapUtil.tryWearElytra(player);
                }

                if (player.getDeltaMovement().y < ElytraSwapperConfig.CONFIG.heightFalling && chestItem.is(Items.ELYTRA) && !player.isFallFlying()) {
                    player.startFallFlying();
                }
            }

            if (player.onGround() && player.isFallFlying()) {
                player.stopFallFlying();
            }
            if (chestItem.is(Items.ELYTRA) && player.getInventory().countItem(Items.ELYTRA) > 1) {
                player.getInventory().removeItem(Items.ELYTRA.getDefaultInstance());
            }
        }
    }
}