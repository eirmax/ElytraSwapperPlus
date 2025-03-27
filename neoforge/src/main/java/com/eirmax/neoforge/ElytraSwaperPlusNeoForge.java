package com.eirmax.neoforge;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.init.ElytraSwaperPlusInit;
import com.eirmax.neoforge.neoforgeplatformhelper.NeoForgePlatformHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ElytraSwaperPlus.MODID)
public final class ElytraSwaperPlusNeoForge {
    public ElytraSwaperPlusNeoForge(IEventBus bus) {
       ElytraSwaperPlus.init();
       ElytraSwaperPlusInit.init(new NeoForgePlatformHelper());
    }
}
