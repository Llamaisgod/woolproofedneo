package com.example.examplemod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Reduces the volume of server-broadcast sounds (block sounds, mob sounds,
 * player actions, explosions, etc.) by 75% if there is a wool block within
 * a few blocks of where the sound happens.
 *
 * IMPORTANT: this only affects sounds the server tells clients to play.
 * Purely client-local sounds (menu clicks, ambient cave noise picked by
 * each player's own client, weather loops, jukebox music) can't be touched
 * from server-side code -- there's no packet for those to intercept.
 *
 * If this fails to compile, the most likely cause is the exact method
 * signature string below not matching this Minecraft version's mappings.
 * Paste me the compile error and I'll fix the target string.
 */
@Mixin(ServerLevel.class)
public abstract class SoundVolumeMixin {

    private static final int SEARCH_RADIUS = 4;
    private static final float DAMPENED_MULTIPLIER = 0.25f; // 75% reduction

    @ModifyVariable(
        method = "playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FF)V",
        at = @At("HEAD"),
        argsOnly = true,
        ordinal = 0 // the first float parameter in the method (volume)
    )
    private float wooldampener$dampen(float volume, @Nullable Player except, double x, double y, double z) {
        ServerLevel self = (ServerLevel) (Object) this;

        if (!isWoolNearby(self, x, y, z)) {
            return volume;
        }
        return volume * DAMPENED_MULTIPLIER;
    }

    private static boolean isWoolNearby(ServerLevel level, double x, double y, double z) {
        BlockPos center = BlockPos.containing(x, y, z);
        BlockPos min = center.offset(-SEARCH_RADIUS, -SEARCH_RADIUS, -SEARCH_RADIUS);
        BlockPos max = center.offset(SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS);

        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            if (level.getBlockState(pos).is(BlockTags.WOOL)) {
                return true;
            }
        }
        return false;
    }
}
