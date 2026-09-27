package hu.snowylol.woolproofed;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

/**
 * Direct port of KeyBindings.class.
 *
 * Fabric registered the keybinding via KeyBindingHelper on client init and
 * polled KeyBinding#wasPressed() on Fabric's END_CLIENT_TICK event.
 *
 * On NeoForge:
 *  - Registration happens via RegisterKeyMappingsEvent (mod event bus).
 *  - Polling happens via ClientTickEvent.Post (game event bus), using
 *    KeyMapping#consumeClick() in a while loop, which is the NeoForge
 *    equivalent of wasPressed().
 *
 * @EventBusSubscriber auto-registers this class's static @SubscribeEvent
 * methods on both buses, so no manual registration call is needed.
 */
@EventBusSubscriber(modid = Woolproofed.MOD_ID, value = Dist.CLIENT)
public class KeyBindings {

    public static final KeyMapping TOGGLE_SOUND_BLOCKING = new KeyMapping(
            "key.woolproofed.toggle",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "category.woolproofed.general"
    );

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_SOUND_BLOCKING);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (TOGGLE_SOUND_BLOCKING.consumeClick()) {
            ModConfig.toggle();
        }
    }
}
