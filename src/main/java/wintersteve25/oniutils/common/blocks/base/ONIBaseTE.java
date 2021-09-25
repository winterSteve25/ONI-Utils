package wintersteve25.oniutils.common.blocks.base;

import mekanism.common.tile.base.TileEntityUpdateable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIForceStoppable;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasProgress;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasRedstoneOutput;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIWorkable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ONIBaseTE extends TileEntityUpdateable implements ITickableTileEntity {

    public ONIBaseTE(TileEntityType<?> te) {
        super(te);
    }

    @Override
    public void tick() {
        if (world.isRemote()) return;
        if (this instanceof ONIIForceStoppable) {
            ONIIForceStoppable forceStoppable = (ONIIForceStoppable) this;
            if (forceStoppable.isInverted()) {
                forceStoppable.setForceStopped(!this.getWorldNN().isBlockPowered(pos));
            } else {
                forceStoppable.setForceStopped(this.getWorldNN().isBlockPowered(pos));
            }

            if (forceStoppable.getForceStopped()) {
                forceStoppable.setWorking(false);
                if (this instanceof ONIIHasProgress) {
                    ONIIHasProgress hasProgress = (ONIIHasProgress) this;
                    hasProgress.setProgress(0);
                }
            }
        }
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), 3, this.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, @Nonnull CompoundNBT tag) {
        this.read(state, tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(super.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        if (world == null) return;
        handleUpdateTag(world.getBlockState(pos), pkt.getNbtCompound());
        super.onDataPacket(net, pkt);
    }

    public void updateBlock(){
        if (world == null) return;
        BlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 2);
        markDirty();
        if (world.isRemote()) return;
        sendUpdatePacket();
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        if (this instanceof ONIIWorkable) {
            ONIIWorkable workable = (ONIIWorkable) this;
            workable.setWorking(tag.getBoolean("isWorking"));
            if (this instanceof ONIIHasProgress) {
                ONIIHasProgress hasProgress = (ONIIHasProgress) this;
                hasProgress.setProgress(tag.getInt("progress"));
            }
        }
        if (this instanceof ONIIHasRedstoneOutput) {
            ONIIHasRedstoneOutput redstoneOutput = (ONIIHasRedstoneOutput) this;
            redstoneOutput.setLowThreshold(tag.getInt("low_threshold"));
            redstoneOutput.setHighThreshold(tag.getInt("high_threshold"));
        }
        ONIUtils.LOGGER.info("Read TE at {} nbt to {}", getPos(), tag);
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        if (this instanceof ONIIWorkable) {
            ONIIWorkable workable = (ONIIWorkable) this;
            tag.putBoolean("isWorking", workable.getWorking());
            if (this instanceof ONIIHasProgress) {
                ONIIHasProgress hasProgress = (ONIIHasProgress) this;
                tag.putInt("progress", hasProgress.getProgress());
            }
        }
        if (this instanceof ONIIHasRedstoneOutput) {
            ONIIHasRedstoneOutput redstoneOutput = (ONIIHasRedstoneOutput) this;
            tag.putInt("low_threshold", redstoneOutput.lowThreshold());
            tag.putInt("high_threshold", redstoneOutput.highThreshold());
        }
        ONIUtils.LOGGER.info("Write TE at {} nbt to {}", getPos(), tag);
        return super.write(tag);
    }

    public boolean isServer() {
        return world != null && !world.isRemote();
    }

    public String machineName() {
        if (world != null) {
            Block block = world.getBlockState(pos).getBlock();
            if (block instanceof ONIBaseMachine) {
                ONIBaseMachine machine = (ONIBaseMachine) block;
                return machine.machineName();
            }
        }

        return "";
    }
}
