package wintersteve25.oniutils.api;

/**
 * Should be implemented on a {@link net.minecraft.world.level.block.entity.BlockEntity}
 */
public interface ONIIWorkable {
    boolean getWorking();

    void setWorking(boolean isWorking);
}
