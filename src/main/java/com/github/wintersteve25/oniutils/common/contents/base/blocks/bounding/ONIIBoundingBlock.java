package com.github.wintersteve25.oniutils.common.contents.base.blocks.bounding;

import net.minecraft.world.level.block.state.BlockState;

/**
 * Should be implemented on a {@link net.minecraft.world.level.block.entity.BlockEntity}
 */
public interface ONIIBoundingBlock {
    void onPlace();

    void onBreak(BlockState oldState);
}

