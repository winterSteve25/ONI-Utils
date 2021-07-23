package wintersteve25.oniutils.common.blocks.base;

import mekanism.common.tile.base.TileEntityUpdateable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public abstract class ONIBaseTE extends TileEntityUpdateable implements ITickableTileEntity {

    protected int totalProgress = totalProgress();
    protected int progress;

    protected boolean isWorking = false;
    protected boolean isForceStopped = false;
    protected boolean isInverted = false;

    public ONIBaseTE(TileEntityType<?> te) {
        super(te);
    }

    @Override
    public void tick() {
        if (this.isInverted()) {
            this.setForceStopped(!this.getWorldNN().isBlockPowered(pos));
        } else {
            this.setForceStopped(this.getWorldNN().isBlockPowered(pos));
        }

        if (getForceStopped()) {
            setWorking(false);
            setProgress(0);
        }
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

    public boolean isInverted() {
        return isInverted;
    }

    public void toggleInverted() {
        isInverted = !isInverted;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), 3, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(world.getBlockState(pos),pkt.getNbtCompound());
    }

    public void updateBlock(){
        BlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 2);
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
