package wintersteve25.oniutils.api;

/**
 * Should be implemented on a {@link net.minecraft.tileentity.TileEntity}
 */
public interface ONIIWorkable {
    boolean getWorking();

    void setWorking(boolean isWorking);
}
