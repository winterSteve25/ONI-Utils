package wintersteve25.oniutils.common.blocks.base.interfaces;

public interface ONIIForceStoppable extends ONIIWorkable {
    boolean getForceStopped();

    void setForceStopped(boolean forceStopped);

    boolean isInverted();

    void toggleInverted();
}
