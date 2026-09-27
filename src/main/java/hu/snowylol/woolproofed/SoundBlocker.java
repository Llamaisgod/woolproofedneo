package hu.snowylol.woolproofed;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Direct port of SoundBlocker.class.
 *
 * Marches a ray from the sound source to the listener in CHECK_INTERVAL-sized
 * steps and checks the block at each sampled position; if any sampled block
 * (other than the listener's own block) is wool, the sound is blocked.
 *
 * The original enumerated all 16 individual wool Blocks (Blocks.WHITE_WOOL,
 * Blocks.ORANGE_WOOL, ...) by hand in isWoolBlock(). Vanilla's own
 * BlockTags.WOOL tag covers exactly that same set of 16 blocks, so it is used
 * here instead - functionally identical, just less repetitive.
 */
public class SoundBlocker {

    public static boolean shouldBlockSound(Level world, Vec3 soundPos, Vec3 listenerPos) {
        if (!ModConfig.ENABLED) {
            return false;
        }

        double distance = soundPos.distanceTo(listenerPos);
        if (distance < ModConfig.MIN_DISTANCE) {
            return false;
        }

        Vec3 direction = listenerPos.subtract(soundPos).normalize();

        // NOTE: the original truncates toward zero ((int) cast) rather than
        // flooring (BlockPos.containing/Mth.floor), so plain int casts are
        // used here too to match the original's behavior exactly, including
        // its off-by-one quirk on negative coordinates.
        BlockPos listenerBlockPos = new BlockPos((int) listenerPos.x, (int) listenerPos.y, (int) listenerPos.z);

        for (double t = 0.0; t < distance; t += ModConfig.CHECK_INTERVAL) {
            Vec3 point = soundPos.add(direction.scale(t));
            BlockPos samplePos = new BlockPos((int) point.x, (int) point.y, (int) point.z);

            if (samplePos.equals(listenerBlockPos)) {
                continue;
            }

            BlockState state = world.getBlockState(samplePos);
            if (isWoolBlock(state)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isWoolBlock(BlockState state) {
        return state.is(BlockTags.WOOL);
    }
}
