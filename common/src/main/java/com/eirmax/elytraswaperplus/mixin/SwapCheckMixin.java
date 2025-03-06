package com.eirmax.elytraswaperplus.mixin;
import com.eirmax.elytraswaperplus.Utils.SwapUtil;
import com.eirmax.elytraswaperplus.network.KeyBind;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class SwapCheckMixin {

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isFallFlying()Z", shift = At.Shift.AFTER))
    public void swapToElytra(CallbackInfo callbackInfo) {
        if (!KeyBind.enabled) {
            return;
        }
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (!player.isOnGround() &&
                !player.isFallFlying() &&
                !player.hasStatusEffect(StatusEffects.LEVITATION) &&
                player.getVelocity().y < 0) {
            SwapUtil.tryWearElytra(player);
        }
    }
}