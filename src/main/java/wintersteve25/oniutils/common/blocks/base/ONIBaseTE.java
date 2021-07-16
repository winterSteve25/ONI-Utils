package wintersteve25.oniutils.common.blocks.base;

import mekanism.common.tile.base.TileEntityUpdateable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public abstract class ONIBaseTE extends TileEntityUpdateable {

    protected int totalProgress = totalProgress();
    protected int progress;

    protected boolean isWorking = false;
    protected boolean isForceStopped = false;
    protected boolean isRedstoneSupported = true;

    public ONIBaseTE(TileEntityType<?> te) {
        super(te);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getTotalProgress() {
        return totalProgress;
    }

    public void setTotalProgress(int progress) {
        this.totalProgress = progress;
    }

    protected abstract int totalProgress();

    public boolean getWorking() {
        return isWorking;
    }

    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public boolean getForceStopped() {
        return isForceStopped;
    }

    public void setForceStopped(boolean forceStopped) {
        isForceStopped = forceStopped;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {

        isWorking = tag.getBoolean("isWorking");
        progress = tag.getInt("progress");

        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {

        isWorking = tag.getBoolean("isWorking");
        progress = tag.getInt("progress");

        return super.write(tag);
    }
}
