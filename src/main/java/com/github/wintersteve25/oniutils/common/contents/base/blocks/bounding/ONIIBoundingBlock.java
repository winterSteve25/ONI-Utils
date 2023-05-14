package com.github.wintersteve25.oniutils.common.contents.base.blocks.bounding;

import net.minecraft.world.level.block.state.BlockState;

public interface ONIIBoundingBlock {
    void onPlace();

    void onBreak(BlockState oldState);
}

