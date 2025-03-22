package com.eirmax.neoforge;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(ElytraSwaperPlus.MODID)
public final class ElytraSwaperPlusNeoForge {
    public ElytraSwaperPlusNeoForge(IEventBus bus) {
       ElytraSwaperPlus.init();


    }
}
