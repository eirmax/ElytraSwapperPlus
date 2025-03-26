package com.eirmax.neoforge;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import com.eirmax.elytraswaperplus.gui.ElytraSwaperPlusGuiHelper;
import com.eirmax.neoforge.neoforgeplatformhelper.NeoForgePlatformHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ElytraSwaperPlus.MODID)
public final class ElytraSwaperPlusNeoForge {
    public ElytraSwaperPlusNeoForge(IEventBus bus) {
       ElytraSwaperPlus.init();
        ElytraSwaperPlusGuiHelper.initialize(new NeoForgePlatformHelper());


    }
}
