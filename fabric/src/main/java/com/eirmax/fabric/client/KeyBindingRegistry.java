package com.eirmax.fabric.client;

import com.eirmax.elytraswaperplus.ElytraSwapperPlus;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public class KeyBindingRegistry {
    public static final KeyMapping.Category ELYTRA_SWAP_PLUS = new KeyMapping.Category(ResourceLocation.fromNamespaceAndPath(ElytraSwapperPlus.MODID, "category.elytraswapplus"));

    public static void init() {
        registerKeyBind("key.elytraswapplus.swap", GLFW.GLFW_KEY_R, ELYTRA_SWAP_PLUS);
        registerKeyBind("key.elytraswapplus.auto_swap", GLFW.GLFW_KEY_B, ELYTRA_SWAP_PLUS);
    }

    public static KeyMapping registerKeyBind(String translationKeyName, int defaultKeyId, KeyMapping.Category category) {
        KeyMapping bind = new KeyMapping(translationKeyName, defaultKeyId, category);
        KeyBindingHelper.registerKeyBinding(bind);
        ClientFabricKeybindEvent.keyMappings.add(bind);
        return bind;
    }
}
