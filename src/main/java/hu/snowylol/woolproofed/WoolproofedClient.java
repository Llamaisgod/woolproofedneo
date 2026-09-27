package hu.snowylol.woolproofed;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * Client-only entrypoint. Equivalent to the Fabric {@code ClientModInitializer}
 * (WoolproofedClient.class). The original registered the keybinding and hooked
 * the end-of-tick event here; on NeoForge that registration is instead done by
 * the {@code @EventBusSubscriber}-annotated {@link KeyBindings} class, which is
 * discovered automatically. This class just declares the client-only mod
 * entrypoint so nothing client-related is ever touched on a dedicated server.
 */
@Mod(value = Woolproofed.MOD_ID, dist = Dist.CLIENT)
public class WoolproofedClient {

    public WoolproofedClient(IEventBus modEventBus) {
        // No extra setup needed here - KeyBindings and ClientEvents register
        // themselves via @EventBusSubscriber.
    }
}
