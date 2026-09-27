package hu.snowylol.woolproofed;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

/**
 * Replaces the original SoundManagerMixin, which injected into
 * SoundManager.play(SoundInstance) at HEAD and cancelled the CallbackInfo.
 *
 * NeoForge exposes exactly this interception point as a proper event -
 * PlaySoundEvent, fired right before a sound is played on the client - so no
 * mixin is needed at all. Setting the event's sound to null prevents it from
 * playing, equivalent to CallbackInfo#cancel() in the mixin.
 */
@EventBusSubscriber(modid = Woolproofed.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        ClientLevel level = client.level;
        if (player == null || level == null) {
            return;
        }

        SoundInstance sound = event.getSound();
        if (sound == null) {
            return;
        }

        if (!ModConfig.BLOCK_UI_SOUNDS
                && sound.getX() == 0
                && sound.getY() == 0
                && sound.getZ() == 0) {
            return;
        }

        Vec3 soundPos = new Vec3(sound.getX(), sound.getY(), sound.getZ());
        Vec3 listenerPos = player.getEyePosition();

        if (SoundBlocker.shouldBlockSound(level, soundPos, listenerPos)) {
            event.setSound(null);
        }
    }
}
