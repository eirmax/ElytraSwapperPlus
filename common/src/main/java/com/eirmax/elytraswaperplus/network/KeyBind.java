package com.eirmax.elytraswaperplus.network;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.rmi.registry.Registry;


public class KeyBind implements ModInitializer {
    public static KeyBinding swapKey;
    public static boolean enabled = true; // Mod enabled state

    @Override
    public void onInitialize() {
        swapKey = Registry.registerKeyBinding(new KeyBinding(
                "key.elytraswapplus.swap",
                GLFW.GLFW_KEY_R,
                "category.elytraswapplus"
        ));
    }
}

