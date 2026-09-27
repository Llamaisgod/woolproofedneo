package hu.snowylol.woolproofed;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

/**
 * Direct port of ModConfig.class. These were plain static fields in the
 * original (no config file), so they are kept exactly the same way here:
 *
 *  - ENABLED:          whether sound blocking is currently active (toggle-able)
 *  - MIN_DISTANCE:     sounds closer than this are never blocked
 *  - CHECK_INTERVAL:   step size (in blocks) used when marching the ray
 *                       between the sound source and the listener
 *  - BLOCK_UI_SOUNDS:  whether to also evaluate sounds with no real world
 *                       position (reported as 0,0,0)
 */
public class ModConfig {

    public static boolean ENABLED = true;
    public static double MIN_DISTANCE = 1.0;
    public static double CHECK_INTERVAL = 0.5;
    public static boolean BLOCK_UI_SOUNDS = false;

    public static void toggle() {
        ENABLED = !ENABLED;

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player != null) {
            String state = ENABLED ? "\u00a7aenabled" : "\u00a7cdisabled";
            player.displayClientMessage(Component.literal("\u00a76[Woolproofed] \u00a7fSound blocking " + state), true);
        }
    }
}
