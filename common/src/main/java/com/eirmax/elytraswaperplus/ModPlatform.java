package com.eirmax.elytraswaperplus;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import java.util.function.Consumer;

public abstract class ModPlatform {


    KeyBinding registerKeyBinding(String translationKeyName, int defaultKeyId, String Category) {
        return null;
    }

    public abstract String getModloader();

    public abstract boolean isModLoaded(String modloader);

    public abstract void registerClientTickEvent(Consumer<MinecraftClient> o);

    public abstract KeyBinding registerKeyBind(String translationKeyName, int defaultKeyId, String category);
}
