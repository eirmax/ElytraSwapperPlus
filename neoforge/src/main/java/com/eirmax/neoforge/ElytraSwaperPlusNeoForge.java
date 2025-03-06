package com.eirmax.neoforge;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import net.neoforged.fml.common.Mod;

@Mod(ElytraSwaperPlus.MOD_ID)
public final class ElytraSwaperPlusNeoForge {
    public ElytraSwaperPlusNeoForge() {
       ElytraSwaperPlus.init();
    }
}
