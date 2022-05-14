package wintersteve25.oniutils.api;

/**
 * Should be implemented on a {@link net.minecraft.world.level.block.entity.BlockEntity}
 */
public interface ONIIHasProgress {
    int getProgress();

    void setProgress(int progress);

    int getTotalProgress();

    void setTotalProgress(int totalProgress);
}
