package com.eirmax.fabric;


import com.eirmax.elytraswaperplus.ElytraSwapperPlus;
import net.fabricmc.api.ModInitializer;

public final class ElytraSwaperPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ElytraSwapperPlus.init();
    }
}