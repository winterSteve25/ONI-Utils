package wintersteve25.oniutils.common.contents.base.bounding;

import net.minecraft.block.BlockState;

public interface ONIIBoundingBlock {
    void onPlace();

    void onBreak(BlockState oldState);
}

