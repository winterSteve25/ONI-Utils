package wintersteve25.oniutils.common.blocks.base.interfaces;

/**
 * Should be implemented in a {@link net.minecraft.tileentity.TileEntity}
 */
public interface ONIIWorkable {
    boolean getWorking();

    void setWorking(boolean isWorking);
}
