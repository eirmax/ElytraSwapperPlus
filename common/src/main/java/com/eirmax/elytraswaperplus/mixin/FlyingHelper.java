package com.eirmax.elytraswaperplus.mixin;

import com.eirmax.elytraswaperplus.utils.ClientSideSwapUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class FlyingHelper {

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;", shift = At.Shift.AFTER))
    public void onTickMovementEnd(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (!ClientSideSwapUtil.auto_equip) {
            return;
        }

        if (!player.isFallFlying() &&
                !player.onGround() &&
                !player.isInLava() &&
                !player.isInWater() &&
                !player.hasEffect(MobEffects.LEVITATION)) {
            ClientSideSwapUtil.tryWearElytra();
        }
    }
}