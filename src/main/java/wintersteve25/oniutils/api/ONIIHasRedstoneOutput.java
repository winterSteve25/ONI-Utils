package wintersteve25.oniutils.api;

/**
 * Should be implemented on a {@link net.minecraft.tileentity.TileEntity}
 */
public interface ONIIHasRedstoneOutput {

    int lowThreshold();

    int highThreshold();

    void setLowThreshold(int in);

    void setHighThreshold(int in);
}
