package wintersteve25.oniutils.common.blocks.base.interfaces;

/**
 * Should be implemented in a {@link net.minecraft.tileentity.TileEntity}
 */
public interface ONIIForceStoppable extends ONIIWorkable {
    boolean getForceStopped();

    void setForceStopped(boolean forceStopped);

    boolean isInverted();

    void toggleInverted();
}
