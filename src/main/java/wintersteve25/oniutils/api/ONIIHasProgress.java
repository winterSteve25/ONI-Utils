package wintersteve25.oniutils.api;

/**
 * Should be implemented on a {@link net.minecraft.tileentity.TileEntity}
 */
public interface ONIIHasProgress extends ONIIForceStoppable {
    int getProgress();

    void setProgress(int progress);

    int getTotalProgress();

    void setTotalProgress(int totalProgress);
}
