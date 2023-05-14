package com.github.wintersteve25.oniutils.common.contents.base.interfaces;

/**
 * Should be implemented on a {@link net.minecraft.world.level.block.entity.BlockEntity}
 */
public interface ONIIHasProgress {
    int getProgress();

    void setProgress(int progress);

    int getTotalProgress();

    void setTotalProgress(int totalProgress);
}
