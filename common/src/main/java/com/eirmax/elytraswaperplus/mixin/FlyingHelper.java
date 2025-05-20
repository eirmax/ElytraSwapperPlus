package com.eirmax.elytraswaperplus.mixin;

import com.eirmax.elytraswaperplus.utils.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class FlyingHelper {

    @Unique
    private boolean wasOnGround = true;

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isFallFlying()Z", shift = At.Shift.AFTER))
    public void onAiStep(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;

        if (!InventoryUtils.auto_equip) return;

        boolean onGround = player.onGround();
        boolean isFallFlying = player.isFallFlying();
        boolean isFalling = player.getDeltaMovement().y < -0.5
                && !onGround && !isFallFlying
                && !player.isInWater() && !player.isInLava()
                && !player.hasEffect(MobEffects.SLOW_FALLING) && !player.hasEffect(MobEffects.LEVITATION);

        if (isFalling) {
            ItemStack chest = player.getInventory().getItem(38);
            if (!InventoryUtils.isElytra(chest)) {
                InventoryUtils.tryWearElytra(Minecraft.getInstance());
                chest = player.getInventory().getItem(38);
            }
            if (InventoryUtils.isElytra(chest) && !player.isFallFlying()) {
                player.startFallFlying();
            }
        }

        if (!wasOnGround && onGround) {
            if (player.isFallFlying()) {
                player.stopFallFlying();
            }
            InventoryUtils.tryWearChestplate(Minecraft.getInstance());
        }

        wasOnGround = onGround;
    }
}