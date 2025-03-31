package com.eirmax.fabric.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindingRegistry {
    public static void init() {
        registerKeyBind("key.elytraswaperplus.swap", GLFW.GLFW_KEY_R, "category.elytraswaperplus");
        registerKeyBind("key.elytraswaperplus.auto_equip", GLFW.GLFW_KEY_B, "category.elytraswaperplus");
    }

    public static void registerKeyBind(String translationKeyName, int defaultKeyId, String category) {
        KeyMapping keyBinding = new KeyMapping(translationKeyName, defaultKeyId, category);
        KeyBindingHelper.registerKeyBinding(keyBinding);
        ClientFabricKeybindEvent.keyMappings.add(keyBinding);
    }
}