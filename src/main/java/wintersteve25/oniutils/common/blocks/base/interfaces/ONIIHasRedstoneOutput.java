package wintersteve25.oniutils.common.blocks.base.interfaces;

/**
 * Should be implemented in a {@link net.minecraft.tileentity.TileEntity}
 */
public interface ONIIHasRedstoneOutput {

    int lowThreshold();

    int highThreshold();

    void setLowThreshold(int in);

    void setHighThreshold(int in);
}
