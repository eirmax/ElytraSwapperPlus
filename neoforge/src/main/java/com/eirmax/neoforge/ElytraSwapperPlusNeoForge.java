package com.eirmax.neoforge;

import com.eirmax.elytraswapperplus.ElytraSwapperPlus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ElytraSwapperPlus.MODID)
public final class ElytraSwapperPlusNeoForge {
    public ElytraSwapperPlusNeoForge(IEventBus bus) {
       ElytraSwapperPlus.init();


    }
}
