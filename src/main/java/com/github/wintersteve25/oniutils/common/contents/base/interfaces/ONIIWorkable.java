package com.github.wintersteve25.oniutils.common.contents.base.interfaces;

/**
 * Should be implemented on a {@link net.minecraft.world.level.block.entity.BlockEntity}
 */
public interface ONIIWorkable {
    boolean getWorking();

    void setWorking(boolean isWorking);
}
