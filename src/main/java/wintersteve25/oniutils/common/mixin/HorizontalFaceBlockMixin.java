package wintersteve25.oniutils.common.mixin;

import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(HorizontalFaceBlock.class)
public class HorizontalFaceBlockMixin {

    /**
     * @author og by mojang mixin by wintersteve25
     */
    @Overwrite
    public static boolean isSideSolidForDirection(IWorldReader reader, BlockPos pos, Direction direction) {
        return true;
    }
}
