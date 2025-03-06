package com.eirmax.fabric;

import com.eirmax.elytraswaperplus.ElytraSwaperPlus;
import net.fabricmc.api.ModInitializer;
public final class ElytraSwaperPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ElytraSwaperPlus.init();
    }
}
