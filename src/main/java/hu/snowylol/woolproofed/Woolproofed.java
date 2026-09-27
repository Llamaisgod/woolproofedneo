package hu.snowylol.woolproofed;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common entrypoint. Equivalent to the Fabric {@code ModInitializer} (Woolproofed.class).
 * Runs on both sides; NeoForge lets the client-only logic live in its own
 * {@code @Mod(dist = Dist.CLIENT)} class instead ({@link WoolproofedClient}),
 * mirroring the original main/client split.
 */
@Mod(Woolproofed.MOD_ID)
public class Woolproofed {

    public static final String MOD_ID = "woolproofed";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public Woolproofed(IEventBus modEventBus) {
        LOGGER.info("Woolproofed mod initialized - sounds will be blocked by wool!");
    }
}
