package com.eirmax.elytraswaperplus.mixin;

import com.eirmax.elytraswaperplus.utils.ClientSideSwapUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LivingEntity.class)
public class FlyingHelper {

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isFallFlying()Z", shift = At.Shift.AFTER))
    public void swapToElytra(CallbackInfo ci) {
        LivingEntity player = (LivingEntity) (Object) (this);
        if(!ClientSideSwapUtil.auto_equip)
            return;
        if (player == null) {
            return;
        }
        if (!player.isFallFlying() && !player.onGround() && !player.isInLava() && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION) && player.getDeltaMovement().y < 0) {
            player.getEyeY();
        }
    }
}