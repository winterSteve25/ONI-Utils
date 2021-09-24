package wintersteve25.oniutils.common.blocks.base.interfaces;

public interface ONIIHasProgress extends ONIIForceStoppable {
    int getProgress();

    void setProgress(int progress);

    int getTotalProgress();

    void setTotalProgress(int totalProgress);
}
