package wintersteve25.oniutils.common.blocks.base.interfaces;

public interface ONIIHasRedstoneOutput {

    int lowThreshold();

    int highThreshold();

    void setLowThreshold(int in);

    void setHighThreshold(int in);
}
